package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumuation.ShipType;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * the test for BattleShipView
 */
class BattleShipViewTest {
  Readable read;
  Appendable write;
  BattleShipView view;
  Readable read2;
  Appendable write2;
  BattleShipView v2;

  /**
   * the setup for test
   */
  @BeforeEach
  void setUp() {
    read = new StringReader("6 7 \n1 2 1 1\n1 2\n3 4\n5 6");
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    read2 = new StringReader("g s \n 9 8\n dj\n 1 2 3 1\n3 4\n2 4ls\n2 5\n3 5\n70 3");
    write2 = new StringBuilder();
    v2 = new BattleShipView(read2, write2);
  }

  /**
   * the test for askSize
   */
  @Test
  void askSize() {
    try {
      int[] size = this.view.askSize("what is the size");
      assertEquals(size.length, 2);
      assertEquals(write.toString(), "what is the size");
      assertEquals(size[0], 6);
      assertEquals(size[1], 7);
      int[] size2 = this.v2.askSize("what is the size");
      assertEquals(size2.length, 2);
      assertEquals(write2.toString(), "what is the sizePlease enter two integers : ");
      assertEquals(size2[0], 9);
      assertEquals(size2[1], 8);
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * test for askFleet
   */
  @Test
  void askFleet() {
    try {
      Map<ShipType, Integer> map = this.view.askFleet("sd");
      assertEquals(map.size(), 4);
      assertEquals(this.write.toString(), "sdPlease enter four integers : ");
      assertEquals(map.get(ShipType.BATTLESHIP), 2);
      assertEquals(map.get(ShipType.CARRIER), 1);
      Map<ShipType, Integer> map2 = this.v2.askFleet("sd");
      assertEquals(map2.size(), 4);
      assertEquals(write2.toString(), "sdPlease enter four integers :"
          + " Please enter four integers : Please enter four integers : ");
      assertEquals(map.get(ShipType.BATTLESHIP), 2);
      assertEquals(map.get(ShipType.CARRIER), 1);
      assertEquals(this.write2.toString(), "sdPlease enter four integers : "
          + "Please enter four integers : Please enter four integers : ");
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * the test for take shot
   */
  @Test
  void takeShot() {
    try {
      List<Coord> l1 = this.view.takeShot("dj", 2);
      assertEquals(l1.size(), 2);
      assertEquals(write.toString(), "dj");
      List<Coord> l2 = this.v2.takeShot("adj", 3);
      assertEquals(l2.size(), 3);
      assertEquals(write2.toString(), """
          adjPlease enter 2 integers in one line, Please reenter:\s
          Please enter 2 integers in one line, Please reenter:\s
          Please enter 2 integers in one line, Please reenter:\s
          """);
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * the test for present prompt
   */
  @Test
  void presentPrompt() {
    this.view.presentPrompt("aaaa");
    assertEquals(write.toString(), "aaaa");
  }
}