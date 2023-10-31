package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.enumuation.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test ShipType class
 */
public class ShipTypeTest {

  Coord[][] board;
  ShipType s1;
  ShipType s2;

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
    s1 = ShipType.CARRIER;
    s2 = ShipType.DESTROYER;
  }

  /**
   * the test for allPossibleV
   */
  @Test
  void allPossibleV() {
    assertEquals(s1.allPossibleV(this.board).size(), 6);
    assertEquals(s2.allPossibleV(this.board).size(), 18);
  }

  /**
   * the test for allPossible
   */
  @Test
  void allPossibleH() {
    assertEquals(s1.allPossibleH(this.board).size(), 6);
    assertEquals(s2.allPossibleH(this.board).size(), 18);
  }

}
