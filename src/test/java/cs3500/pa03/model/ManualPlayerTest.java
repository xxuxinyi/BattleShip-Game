package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa03.view.BattleShipView;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for manual player
 */
class ManualPlayerTest {
  ManualPlayer p1;
  Map<ShipType, Integer> map1 = new HashMap<>();
  Readable read;
  Appendable write;
  BattleShipView view;
  Board board;
  List<Ship> ships;

  /**
   * the setup for test
   */
  @BeforeEach
  void setUp() {
    read = new StringReader("1 1\n2 2\n3 3\n4 4\n5 9\n1 1\n2 2\n3 3\n4 4\n5 5");
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    map1.put(ShipType.SUBMARINE, 1);
    map1.put(ShipType.CARRIER, 2);
    map1.put(ShipType.BATTLESHIP, 1);
    map1.put(ShipType.DESTROYER, 1);
    board = new Board();
    board.initBoard(6, 6);
    ships = new LinkedList<>();
    p1 = new ManualPlayer(view, new Random(1), ships, board);
    assertEquals(p1.setup(6, 6, map1).size(), 5);
  }

  /**
   * test for name
   */
  @Test
  void name() {
    assertEquals(p1.name(), "Player1");
  }

  /**
   * test for takeShots
   */
  @Test
  void takeShots() {
    List<Coord> shots = this.p1.takeShots();
    assertEquals(shots.size(), 5);
    assertEquals(shots.get(0), new Coord(1, 1));
    assertEquals(this.write.toString(), """
        Please enter 5 shorts :
        the Coordinates is not valid, Please enter again :
        """);
  }

  /**
   * test for successfulHits
   */
  @Test
  void successfulHits() {
    assertEquals(this.board.present().toString(), """
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
        0  S S S S S .\s
        1  . . . . . .\s
        2  S S S S . .\s
        3  S S S S S S\s
        4  S S S S S S\s
        5  . . S S S .\s
        """);
    List<Coord> l = new LinkedList<>();
    l.add(new Coord(2, 3));
    p1.successfulHits(l);
    assertEquals(this.board.present().toString(), """
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . . . . . .\s
        2  . . . . . .\s
        3  . . H . . .\s
        4  . . . . . .\s
        5  . . . . . .\s


        your board :\s
           0 1 2 3 4 5\s
        0  S S S S S .\s
        1  . . . . . .\s
        2  S S S S . .\s
        3  S S S S S S\s
        4  S S S S S S\s
        5  . . S S S .\s
        """);
  }
}