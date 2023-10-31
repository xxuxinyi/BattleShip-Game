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
 * test for AI Player
 */
class AiPlayerTest {
  AiPlayer p1;
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
    read = new StringReader("");
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    map1.put(ShipType.SUBMARINE, 1);
    map1.put(ShipType.CARRIER, 2);
    map1.put(ShipType.BATTLESHIP, 1);
    map1.put(ShipType.DESTROYER, 1);
    board = new Board();
    ships = new LinkedList<>();
    p1 = new AiPlayer("Player", view, new Random(1), ships, board);
    p1.setup(6, 6, map1);
  }

  /**
   * test for name
   */
  @Test
  void name() {
    assertEquals(p1.name(), "Player");
  }

  /**
   * test for takeShots
   */
  @Test
  void takeShots() {
    List<Coord> l = p1.takeShots();
    assertEquals(l.size(), 5);
    assertEquals(l.get(0).compareWeight(l.get(1)), 0);
    assertEquals(l.get(1).compareWeight(l.get(2)), 0);
    assertEquals(l.get(2).compareWeight(l.get(3)), 0);
    assertEquals(l.get(3).compareWeight(l.get(0)), 0);
    List<Coord> l2 = p1.takeShots();
    assertEquals(l2.size(), 5);
    assertEquals(l2.get(0).compareWeight(l.get(1)), 1);
    assertEquals(l2.get(1).compareWeight(l.get(2)), 1);
    assertEquals(l2.get(2).compareWeight(l.get(3)), 1);
    assertEquals(l2.get(3).compareWeight(l.get(0)), 1);
    List<Coord> l3 = new LinkedList<>();
    l3.add(new Coord(2, 3));
    p1.successfulHits(l3);
    List<Coord> l4 = p1.takeShots();
    assertEquals(l4.size(), 5);
    List<Coord> l5 = new LinkedList<>();
    l5.add(new Coord(2, 3));
    l5.add(new Coord(4, 4));
    l5.add(new Coord(1, 3));
    p1.successfulHits(l5);
    List<Coord> l6 = p1.takeShots();
    assertEquals(l6.size(), 5);
    for (Coord[] col : board.getOpponentBoard()) {
      for (Coord c : col) {
        c.handleHit();
      }
    }
    p1.successfulHits(l5);
    List<Coord> l7 = p1.takeShots();
    assertEquals(l7.size(), 0);
  }

  /**
   * test for successfulHits
   */
  @Test
  void successfulHits() {
    assertEquals(board.present().toString(), """
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
    assertEquals(board.present().toString(), """
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