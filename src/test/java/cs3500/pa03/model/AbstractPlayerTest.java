package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.enumuation.GameResult;
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
 * test for AbstractPlayer
 */
class AbstractPlayerTest {
  AbstractPlayer p1;
  AbstractPlayer p2;
  Map<ShipType, Integer> map1 = new HashMap<>();
  Readable read;
  Appendable write;
  BattleShipView view;
  Board board1;
  Board board2;
  List<Ship> ships1;
  List<Ship> ships2;

  /**
   * the setup
   */
  @BeforeEach
  void setUp() {
    read = new StringReader("");
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    map1.put(ShipType.SUBMARINE, 1);
    map1.put(ShipType.CARRIER, 1);
    map1.put(ShipType.BATTLESHIP, 1);
    map1.put(ShipType.DESTROYER, 1);
    board1 = new Board();
    board2 = new Board();
    this.ships1 = new LinkedList<>();
    this.ships2 = new LinkedList<>();
    p1 = new AiPlayer("Player",
        view, new Random(1), ships1, board1);
    p1.setup(6, 6, map1);
    p2 = new ManualPlayer(view, new Random(2), ships2, board2);
    p2.setup(6, 6, map1);
  }



  /**
   * test for reportDamage
   */
  @Test
  void reportDamage() {
    List<Coord> l = new LinkedList<>();
    l.add(new Coord(0, 0));
    assertEquals(p1.reportDamage(l).size(), 0);
    l.add(new Coord(0, 3));
    assertEquals(p1.reportDamage(l).size(), 1);
    List<Coord> l2 = new LinkedList<>();
    assertEquals(p2.reportDamage(l2).size(), 0);
    l2.add(new Coord(3, 5));
    assertEquals(p2.reportDamage(l2).size(), 1);
    l2.add(new Coord(4, 5));
    l2.add(new Coord(5, 5));
    p2.reportDamage(l2);
    assertEquals(ships2.size(), 3);
  }


  /**
   * test for endGame
   */
  @Test
  void endGame() {
    p1.endGame(GameResult.WIN, "no reason");
    assertEquals(write.toString(), "You win\n"
        + "no reason");
    p1.endGame(GameResult.LOSE, "no reason");
    assertEquals(write.toString(), """
        You win
        no reasonYou lose
        no reason""");
    p1.endGame(GameResult.DRAW, "no reason");
    assertEquals(write.toString(), """
        You win
        no reasonYou lose
        no reasonGame tile
        no reason""");
  }
}