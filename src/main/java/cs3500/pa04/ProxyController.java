package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumuation.GameResult;
import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa04.jsonmessage.CoordJson;
import cs3500.pa04.jsonmessage.FleetJson;
import cs3500.pa04.jsonmessage.GameTypeJson;
import cs3500.pa04.jsonmessage.JsonUtil;
import cs3500.pa04.jsonmessage.MessageJson;
import cs3500.pa04.jsonmessage.ShipJson;
import cs3500.pa04.jsonmessage.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class ProxyController implements BattleShipController
 */
public class ProxyController implements BattleShipController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final GameType gameType;

  /**
   * Constructor for ProxyController
   *
   * @param server the socket
   * @param gameType the GameType enum
   * @param player the player
   * @throws IOException the exception
   */
  public ProxyController(Socket server, GameType gameType, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.gameType = gameType;
    this.player = player;
  }

  /**
   * loop the game
   */
  @Override
  public void round()  {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Determines the type of request the server has sent and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) throws IOException {
    String name = message.name();
    JsonNode arguments = message.arguments();
    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      this.handleSetUp(arguments);
    } else if ("take-shots".equals(name)) {
      this.handleTakeShot();
    } else if ("report-damage".equals(name)) {
      this.handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      this.handleSuccessfulHit(arguments);
    } else if ("end-game".equals(name)) {
      this.handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * handle join, give the username and game type to server
   */
  private void handleJoin() {
    GameTypeJson name = new GameTypeJson(this.player.name(), this.gameType.toString());
    MessageJson joinMessage = new MessageJson("join", JsonUtil.serializeRecord(name));
    JsonNode response = JsonUtil.serializeRecord(joinMessage);
    this.out.println(response);
  }

  /**
   * handle set-up, give the information of the ships to server
   *
   * @param arguments the server requested message
   */
  private void handleSetUp(JsonNode arguments) {
    int width = this.mapper.convertValue(arguments.get("width"), Integer.class);
    int height = this.mapper.convertValue(arguments.get("height"), Integer.class);
    JsonNode spec = this.mapper.convertValue(arguments.get("fleet-spec"), JsonNode.class);
    int carrier = this.mapper.convertValue(spec.get("CARRIER"), Integer.class);
    int battleship = this.mapper.convertValue(spec.get("BATTLESHIP"), Integer.class);
    int destroyer = this.mapper.convertValue(spec.get("DESTROYER"), Integer.class);
    int submarine = this.mapper.convertValue(spec.get("SUBMARINE"), Integer.class);
    HashMap<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, carrier);
    map.put(ShipType.BATTLESHIP, battleship);
    map.put(ShipType.DESTROYER, destroyer);
    map.put(ShipType.SUBMARINE, submarine);
    List<Ship> ships = this.player.setup(height, width, map);
    List<ShipJson> shipsJsonList = new ArrayList<>();
    for (Ship s : ships) {
      shipsJsonList.add(s.getShipJson());
    }
    FleetJson fleetJson = new FleetJson(shipsJsonList);
    MessageJson setUpMessage = new MessageJson("setup", JsonUtil.serializeRecord(fleetJson));
    JsonNode setUpResponse = JsonUtil.serializeRecord(setUpMessage);
    this.out.println(setUpResponse);
  }

  /**
   * handle take-shots, return the take shots form AI player
   */
  private void handleTakeShot() {
    List<Coord> shots = this.player.takeShots();
    List<CoordJson> jsonList = new ArrayList<>();
    for (Coord c : shots) {
      jsonList.add(c.recordCoord());
    }
    VolleyJson volleyJson = new VolleyJson(jsonList);
    MessageJson takeShotsMessage =
        new MessageJson("take-shots", JsonUtil.serializeRecord(volleyJson));
    JsonNode takeShotsResponse = JsonUtil.serializeRecord(takeShotsMessage);
    this.out.println(takeShotsResponse);
  }

  /**
   * handle successfully-hit, get information from Player
   *
   * @param arguments the server requested message
   */
  private void handleSuccessfulHit(JsonNode arguments) {
    this.player.successfulHits(volleyJson(arguments));
    MessageJson hitMessage =
        new MessageJson("successful-hits", this.mapper.createObjectNode());
    JsonNode response = JsonUtil.serializeRecord(hitMessage);
    this.out.println(response);
  }

  /**
   * handle report-damage, get information from PLayer and pass to server
   *
   * @param arguments the server requested message
   */
  private void handleReportDamage(JsonNode arguments) {
    List<Coord> coords = volleyJson(arguments);
    List<Coord> hit = this.player.reportDamage(coords);
    List<CoordJson> responseHit = new LinkedList<>();
    for (Coord c : hit) {
      responseHit.add(c.recordCoord());
    }
    VolleyJson volley = new VolleyJson(responseHit);
    MessageJson reportMessage = new MessageJson("report-damage",
                                                JsonUtil.serializeRecord(volley));
    JsonNode reportResponse = JsonUtil.serializeRecord(reportMessage);
    this.out.println(reportResponse);
  }

  /**
   * covert the coordinate to volley json
   *
   * @param arguments the server requested message
   * @return a list of coord correspond with the coordinate
   */
  private List<Coord> volleyJson(JsonNode arguments) {
    ArrayNode
        volleyJson = this.mapper.convertValue(arguments.get("coordinates"),
        ArrayNode.class);
    List<Coord> coords = new LinkedList<>();
    for (JsonNode j : volleyJson) {
      int x = this.mapper.convertValue(j.get("x"), Integer.class);
      int y = this.mapper.convertValue(j.get("y"), Integer.class);
      coords.add(new Coord(x, y));
    }
    return coords;
  }

  /**
   * handle end-game
   *
   * @param arguments the server requested message
   * @throws IOException if there is problem when closing the server
   */
  private void handleEndGame(JsonNode arguments) throws IOException {
    String resultAsString = this.mapper.convertValue(arguments.get("result"), String.class);
    GameResult result = GameResult.valueOf(resultAsString);
    this.player.endGame(result, this.mapper.convertValue(arguments.get("reason"), String.class));
    this.out.println(new MessageJson("end-game", this.mapper.createObjectNode()));
    this.server.close();
  }
}
