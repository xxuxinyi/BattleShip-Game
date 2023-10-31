package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.BattleShipView;
import cs3500.pa04.jsonmessage.JsonUtil;
import cs3500.pa04.jsonmessage.MessageJson;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test ProxyController
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController proxyController;
  private List<String> input;
  private Player player;
  Readable read;
  Appendable write;
  BattleShipView view;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    read = new StringReader("");
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    this.input = new ArrayList<>();
    this.player = new AiPlayer("Megan238", this.view,
                               new Random(1), new ArrayList<>(), new Board());

  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }


  /**
   * test round method in ProxyController
   */
  @Test
  void round() {
    JsonNode joinMessage =
        JsonUtil.serializeRecord(new MessageJson("join",
                                                 new ObjectMapper().createObjectNode()));
    input.add(joinMessage.toString());
    input.add("""
                  {
                  \t"method-name": "setup",
                  \t"arguments": {
                  \t\t"width": 6,
                  \t\t"height": 6,
                  \t\t"fleet-spec": {
                  \t\t\t"CARRIER": 1,
                  \t\t\t"BATTLESHIP": 1,
                  \t\t  "DESTROYER": 1,
                  \t\t\t"SUBMARINE": 1
                  \t\t}
                  \t}
                  }""");
    input.add("""
                  {
                  \t"method-name": "take-shots",
                  \t"arguments": {}
                  }""");
    input.add("""
                  {
                  \t"method-name": "report-damage",
                  \t"arguments": {
                  \t\t"coordinates": [
                  \t\t\t{"x": 0, "y": 1},
                  \t\t\t{"x": 3, "y": 2}
                  \t\t]
                  \t}
                  }""");
    input.add("""
                  {
                  \t"method-name": "successful-hits",
                  \t"arguments": {
                  \t\t"coordinates": [
                  \t\t\t{"x": 0, "y": 1},
                  \t\t\t{"x": 3, "y": 2}
                  \t\t]
                  \t}
                  }""");
    input.add("""
                  {
                  \t"method-name": "end-game",
                  \t"arguments": {
                  \t\t"result": "WIN",
                  \t\t"reason": "Player 1 sank all of Player 2's ships"
                  \t}
                  }""");

    Socket mocket = new Mocket(this.testLog, this.input);
    try {
      this.proxyController = new ProxyController(mocket, GameType.SINGLE, player);
    } catch (Exception e) {
      fail();
    }
    // run the dealer and verify the response
    this.proxyController.round();

    assertEquals("{\"method-name\":\"join\",\"arguments\":"
        + "{\"name\":\"Megan238\",\"game-type\":\"SINGLE\"}}"
        + System.lineSeparator()
        + "{\"method-name\":\"setup\",\"arguments\":"
        + "{\"fleet\":[{\"coord\":{\"x\":0,\"y\":4},\"length\":6,\"direction\":"
        + "\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":3},\"length\":5,\"direction\":"
        + "\"HORIZONTAL\"}"
        + ",{\"coord\":{\"x\":5,\"y\":0},\"length\":4,\"direction\":\"VERTICAL\"},"
        + "{\"coord\":{\"x\":1,\"y\":0},"
        + "\"length\":3,\"direction\":\"VERTICAL\"}]}}"
        + System.lineSeparator()
        + "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":2,\"y\":2},"
        + "{\"x\":3,\"y\":2},{\"x\":2,\"y\":3},{\"x\":3,\"y\":3}]}}"
        + System.lineSeparator()
        + "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[]}}"
        + System.lineSeparator()
        + "{\"method-name\":\"successful-hits\",\"arguments\":{}}"
        + System.lineSeparator()
        + "MessageJson[name=end-game, arguments={}]"
        + System.lineSeparator(), logToString());
  }

  /**
   * test the inputError in ProxyController
   */
  @Test
  void testError() {
    input.add("""
                  {
                  \t"method-name": "random",
                  \t"arguments": {
                  \t}
                  }""");

    Socket mocket = new Mocket(this.testLog, this.input);
    try {
      this.proxyController = new ProxyController(mocket, GameType.SINGLE, player);
    } catch (Exception e) {
      fail();
    }
    // run the dealer and verify the response
    assertThrows(IllegalStateException.class, () -> this.proxyController.round());
  }
}