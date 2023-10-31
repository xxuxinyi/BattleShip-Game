package cs3500.pa04;

import cs3500.pa03.controller.ManualBattleShipController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Driver
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   *
   */

  public static void main(String[] args)  {
    driver(args);
  }

  /**
   * for test
   *
   * @param args is the arguments
   */
  public static void driver(String[] args) {
    if (!(args.length == 0 || args.length == 2)) {
      throw new IllegalArgumentException("Invalid number of Program Arguments");
    }
    try {
      Readable read = new InputStreamReader(System.in);
      Appendable write = new PrintStream(System.out);
      cs3500.pa03.view.BattleShipView v = new cs3500.pa03.view.BattleShipView(read, write);
      BattleShipController controller;
      if (args.length == 2) {
        String host = args[0];
        int post = Integer.parseInt(args[1]);
        Socket server = new Socket(host, post);
        Player player = new AiPlayer("pa04-xinyi", v, new Random(),
            new ArrayList<>(), new Board());
        controller = new ProxyController(server, GameType.SINGLE, player);
      } else  {
        controller = new ManualBattleShipController(v);
      }
      controller.round();
    } catch (Exception e) {
      System.out.println("We encountered an unexpected error " + e.getMessage());
    }
  }
}
