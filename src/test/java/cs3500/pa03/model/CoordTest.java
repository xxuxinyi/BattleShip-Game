package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.jsonmessage.JsonUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * the test for coord
 */
class CoordTest {
  Coord[][] board = new Coord[10][10];

  Coord c1 = new Coord(1, 2);
  Coord c2 = new Coord(0, 0);
  Coord c3 = new Coord(9, 0);
  Coord c4 = new Coord(0, 2);
  Coord c5 = new Coord(9, 9);
  Coord c6 = new Coord(9, 0);

  /**
   * the setup for test
   */
  @BeforeEach
   void setUp() {
    c1.reWeight();
    c2.reWeight();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        this.board[j][i] = new Coord(i, j);
      }
    }
  }

  /**
   * the test for isSunk
   */
  @Test
  void isSunk() {
    assertFalse(c1.isSunk());
    c1.markOccupied();
    c1.handleHit();
    assertTrue(c1.isSunk());
  }

  /**
   * the test for ableToPlace
   */
  @Test
  void ableToPlace() {
    LinkedList<Coord> l = new LinkedList<>();
    assertTrue(c1.ableToPlace(l));
    assertEquals(l.size(), 1);
  }

  /**
   * the test for compareWeight
   */
  @Test
  void compareWeight() {
    assertEquals(c1.compareWeight(c2), 0);
    c1.addWeight();
    assertEquals(c1.compareWeight(c2), -1);
    c2.addWeight();
    c2.addWeight();
    assertEquals(c1.compareWeight(c2), 1);
  }

  /**
   * the test for findCoord
   */
  @Test
  void findCoord() {
    assertEquals(c1.findCoord(board), board[2][1]);
  }

  /**
   * the test for placeAdjacent
   */
  @Test
  void placeAdjacent() {
    ArrayList<Coord> l = new ArrayList<>();
    ArrayList<Coord> previous = new ArrayList<>();
    c1.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 2);
    c2.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 2);
    c4.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 3);
    c3.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 4);
    c6.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 5);
    for (Coord c : l) {
      c.findAndHit(board);
    }
    c1.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 6);
    c2.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 6);
    c3.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 7);
    board[1][0].markOccupied();
    board[1][0].handleHit();
    new Coord(0, 1).placeAdjacent(board, l, previous);
    assertEquals(l.size(), 9);
    new Coord(0, 9).findAndHit(board);
    board[1][9].markOccupied();
    board[1][9].handleHit();
    board[0][9].markOccupied();
    board[0][9].handleHit();
    new Coord(8, 1).placeAdjacent(board, l, previous);
    assertEquals(l.size(), 10);
    previous.add(c2);
    new Coord(8, 1).placeAdjacent(board, l, previous);
    assertEquals(l.size(), 10);
    c5.placeAdjacent(board, l, previous);
    assertEquals(l.size(), 12);
    board[5][4].markOccupied();
    board[5][4].handleHit();
    board[6][4].markOccupied();
    board[6][4].handleHit();
    new Coord(5, 5).placeAdjacent(board, l, previous);
    assertEquals(l.size(), 14);
    board[5][6].markOccupied();
    board[5][6].handleHit();
    board[4][6].markOccupied();
    board[4][6].handleHit();
    new Coord(5, 5).placeAdjacent(board, l, previous);
    assertEquals(l.size(), 14);
  }

  /**
   * the test for handleHit
   */
  @Test
  void handleHit() {
    assertFalse(c1.handleHit());
    assertFalse(c2.handleHit());
  }

  /**
   * the test for findAndHit
   */
  @Test
  void findAndHit() {
    assertFalse(board[0][0].isSunk());
    c2.findAndHit(board);
    assertTrue(board[0][0].isSunk());
  }

  /**
   * the test for reWeight
   */
  @Test
  void reWeight() {
    assertEquals(c1.compareWeight(c2), 0);
    c2.reWeight();
    assertEquals(c1.compareWeight(c2), 0);
  }

  /**
   * the test for drawCoord
   */
  @Test
  void drawCoord() {
    assertEquals(c1.drawCoord(), '.');
  }

  /**
   * the test for markOccupied
   */
  @Test
  void markOccupied() {
    c5.markOccupied();
    assertFalse(c5.ableToPlace(new LinkedList<>()));
  }

  /**
   * the test for addWeight
   */
  @Test
  void addWeight() {
    assertEquals(c1.compareWeight(c2), 0);
    c1.addWeight();
    assertEquals(c1.compareWeight(c2), -1);
    c2.addWeight();
    c2.addWeight();
    assertEquals(c1.compareWeight(c2), 1);
  }

  /**
   * the test for recordCoord method
   */
  @Test
  void recordCoord() {
    assertEquals(JsonUtil.serializeRecord(new Coord(3, 4).recordCoord()).toString(),
                 "{\"x\":3,\"y\":4}");
  }

  /**
   * the test for equals
   */
  @Test
  void testEquals() {
    assertEquals(new Coord(3, 3), new Coord(3, 3));
    assertNotEquals(new Coord(3, 4), new Coord(3, 3));
  }

  /**
   * the test for HashCode
   */
  @Test
  void testHashCode() {
    assertEquals(new Coord(4, 4).hashCode(), 1089);
  }
}