package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa04.jsonmessage.JsonUtil;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * the test for ship
 */
class ShipTest {
  Coord[][] board;
  Ship s1;
  Ship s2;

  /**
   * the setup for test
   */
  @BeforeEach
  void setUp() {
    board = new Coord[6][6];
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        this.board[j][i] = new Coord(i, j);
      }
    }
    s1 = new Ship(ShipType.CARRIER, board,  new Random(2));
    s2 = new Ship(ShipType.BATTLESHIP, board,  new Random(2));
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        Coord c = board[i][j];
        result.append(c.drawCoord());
      }
      result.append("\n");
    }
    assertEquals(result.toString(), """
        SSSSSS
        ......
        ......
        SSSSS.
        ......
        ......
        """);
  }

  /**
   * the test for sunk
   */
  @Test
  void sunk() {

    assertFalse(s1.sunk());
  }

  /**
   * the test for get shipJson
   */
  @Test
  void getShipJson() {
    assertEquals(JsonUtil.serializeRecord(s1.getShipJson()).toString(),
                 "{\"coord\":{\"x\":0,\"y\":0},\"length\":6,\"direction\":\"HORIZONTAL\"}");
  }
}