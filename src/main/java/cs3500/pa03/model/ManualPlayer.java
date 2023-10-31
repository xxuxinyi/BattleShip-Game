package cs3500.pa03.model;

import cs3500.pa03.view.BattleShipView;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * represent a manual player, need a person to interact with
 */
public class ManualPlayer extends AbstractPlayer {


  /**
   * @param view is the view for this program
   * @param r is the random number to place ship.
   * @param remainShip is the remain ship this player have
   * @param board is the board this player can see
   */
  public ManualPlayer(BattleShipView view, Random r, List<Ship> remainShip, Board board) {
    super(view, r, remainShip, board);
  }


  /**
   * @return the name of this player
   */
  @Override
  public String name() {
    return "Player1";
  }


  /**
   * return the shots user input, and make the shots list empty
   *
   * @return the shot user input
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> userShorts;
    try {
      userShorts = this.view.takeShot("Please enter "
          + this.remainShip.size() + " shorts :\n", this.remainShip.size());
      while (!this.validInput(userShorts)) {
        userShorts = this.view.takeShot("the Coordinates is "
                + "not valid, Please enter again :\n",
            this.remainShip.size());
      }
    } catch (IOException e) {
      this.view.presentPrompt("Meet unhandled situation : " + e.getMessage());
      return this.takeShots();
    }
    for (Coord c : userShorts) {
      Coord onboard = c.findCoord(this.board.getOpponentBoard());
      onboard.handleHit();
    }

    return userShorts;
  }

  /**
   * check if the Coords is valid
   *
   * @param list is the user input coordinates
   * @return if the input in valid
   */
  private boolean validInput(List<Coord> list) {
    for (Coord c : list) {
      try {
        c.findCoord(board.getBoard());
      } catch (Exception e) {
        return false;
      }
    }
    return true;
  }


  /**
   * mark all the coordinate that is successful Hits as Hit on the opponentBoard
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      c.findAndHit(board.getOpponentBoard());
    }
  }

}
