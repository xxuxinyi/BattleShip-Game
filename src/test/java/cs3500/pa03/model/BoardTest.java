package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for Board
 */
class BoardTest {
  Board b1 = new Board();

  /**
   * the setup for test
   */
  @BeforeEach
  void setUp() {
    b1.initBoard(6, 6);
    assertEquals(b1.getBoard()[1][1], new Coord(1, 1));
  }

  /**
   * test for present
   */
  @Test
  void present() {
    assertEquals(b1.present().toString(), """
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . . . . . .\s
        2  . . . . . .\s
        3  . . . . . .\s
        4  . . . . . .\s
        5  . . . . . .\s


        your board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . . . . . .\s
        2  . . . . . .\s
        3  . . . . . .\s
        4  . . . . . .\s
        5  . . . . . .\s
        """);
  }

  /**
   * test for getBoard
   */
  @Test
  void getBoard() {
    assertEquals(b1.getBoard().length, 6);
  }

  /**
   * test for getOpponentBoard
   */
  @Test
  void getOpponentBoard() {
    assertEquals(b1.getOpponentBoard().length, 6);
  }


}