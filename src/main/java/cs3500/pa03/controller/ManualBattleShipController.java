package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumuation.GameResult;
import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa03.view.BattleShipView;
import cs3500.pa04.BattleShipController;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * the controller for BattleShip game
 */
public class ManualBattleShipController implements BattleShipController {
  private final Player mainPlayer;
  private final Player opponent;
  private final BattleShipView view;
  private final Board mainBoard;
  private final List<Ship> mainShips = new LinkedList<>();
  private final List<Ship> opponentShips = new LinkedList<>();

  /**
   * for test
   *
   * @param view          is the view of this program
   * @param random        the random to place the ship
   * @throws IOException if there is mistake in output and input
   */
  public ManualBattleShipController(BattleShipView view, Random random) throws IOException {
    this.view = view;
    this.mainBoard = new Board();
    this.mainPlayer = new ManualPlayer(this.view, random,
        this.mainShips, this.mainBoard);
    this.opponent = new AiPlayer("PLAYER2", this.view, random,
        this.opponentShips, new Board());
    this.iniPlayers();
  }

  public ManualBattleShipController(BattleShipView view) throws IOException {
    this(view, new Random());
  }

  /**
   * according to the information return from the view, initialize the two players
   *
   * @throws IOException if there is wrong with output and input
   */
  private void iniPlayers() throws IOException {
    int[] size = this.view.askSize("""
        Hello! Welcome to the OOD BattleSalvo Game!\s
        Please enter a valid height and width below:\s
        """);
    int height = size[0];
    int width = size[1];
    while (height > 15 || height < 6 || width > 15 || width < 6) {
      size = this.view.askSize("""
          Uh Oh! You've entered invalid dimensions. Please remember that the height and width
          of the game must be in the range (6, 10), inclusive. Try again!\s
          """);
      height = size[0];
      width = size[1];
    }
    Map<ShipType, Integer> specifications =
        this.view.askFleet("Please enter your fleet in the order [Carrier, Battleship,"
            + " Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + Math.min(height, width) + "\n");
    int c = specifications.get(ShipType.CARRIER);
    int b = specifications.get(ShipType.BATTLESHIP);
    int d = specifications.get(ShipType.DESTROYER);
    int s = specifications.get(ShipType.SUBMARINE);
    while ((c + b + d + s) > Math.min(height, width)
        || (c <= 0) || (b <= 0) || (d <= 0) || (s <= 0)) {
      specifications =
          this.view.askFleet("Uh Oh! You've entered invalid fleet sizes.\n"
              + "Please enter your fleet in the order [Carrier, Battleship,"
              + " Destroyer, Submarine].\n"
              + "Remember, your fleet may not exceed size " + Math.min(height, width) + "\n");
      c = specifications.get(ShipType.CARRIER);
      b = specifications.get(ShipType.BATTLESHIP);
      d = specifications.get(ShipType.DESTROYER);
      s = specifications.get(ShipType.SUBMARINE);
    }
    this.mainPlayer.setup(height, width, specifications);
    this.opponent.setup(height, width, specifications);
  }

  /**
   * represent a round in the game
   *
   */
  public void round() {
    while (!this.gameResult()) {
      StringBuilder view = this.mainBoard.present();
      this.view.presentPrompt(view.toString());
      List<Coord> mainShots = this.mainPlayer.takeShots();
      List<Coord> opponentShots = this.opponent.takeShots();
      List<Coord> shotsThatHitMainShips = this.mainPlayer.reportDamage(opponentShots);
      List<Coord> shotsThatHitOpponentShips = this.opponent.reportDamage(mainShots);
      this.mainPlayer.successfulHits(shotsThatHitOpponentShips);
      this.opponent.successfulHits(shotsThatHitMainShips);
    }
  }



  /**
   * check the ship count for each player, and determine if this game is end or not
   *
   * @return if this game is end or not
   */
  private boolean gameResult() {
    int main = this.mainShips.size();
    int opp = this.opponentShips.size();
    boolean gameEnd = false;
    if (main == 0 && opp > 0) {
      this.mainPlayer.endGame(GameResult.LOSE, "there is no ship left on your board");
      gameEnd = true;
    }
    if (opp == 0 && main > 0) {
      this.mainPlayer.endGame(GameResult.WIN, "there is no ship left on opponent board");
      gameEnd = true;
    }
    if (opp == 0 && main == 0) {
      this.mainPlayer.endGame(GameResult.DRAW, "there is no ship left on both boards");
      gameEnd = true;
    }
    return gameEnd;
  }


}
