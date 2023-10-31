package cs3500.pa03.model;

import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa03.view.BattleShipView;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * represent an AI player has basic skill for playing a battle-salvo game
 */
public class AiPlayer extends AbstractPlayer {
  private final List<Coord> possibleCoord = new Stack<>();
  private final List<Coord> weightPossible;
  private final String name;
  private final List<Coord> coordinatorUnKnownSurrounding = new Stack<>();


  /**
   * Constructor for aiPlayer
   *
   * @param name is the name of player
   * @param view is the view for this program
   * @param r is the random number to place ship.
   * @param remainShip is the remain ship this player have
   * @param board is the board this player can see
   */
  public AiPlayer(String name, BattleShipView view,
                  Random r, List<Ship> remainShip, Board board) {
    super(view, r, remainShip, board);
    this.name = name;
    this.weightPossible = new LinkedList<>();
  }

  @Override
  public String name() {
    return this.name;
  }

  /**
   * if the possibleCoord is empty return the top coordinates
   * from the weight all possible coordinate list
   *
   * @return the shot the AI choose
   */
  @Override
  public List<Coord> takeShots() {
    if (weightPossible.isEmpty()) {
      for (Coord[] shipCoord : this.board.getOpponentBoard()) {
        this.weightPossible.addAll(Arrays.stream(shipCoord).toList());
      }
      this.weightPossible();
    }
    int size = this.remainShip.size();
    List<Coord> result = new LinkedList<>();
    while (!(result.size() == size)) {
      int count = size - result.size();
      if (this.possibleCoord.isEmpty()) {
        for (int i = 0; i < count; i++) {
          if (this.weightPossible.size() > 0) {
            result.add(this.weightPossible.remove(0));
          } else {
            for (Coord c : result) {
              Coord onboard = c.findCoord(this.board.getOpponentBoard());
              onboard.handleHit();
            }
            return result;
          }
        }
      } else {
        for (int i = 0; i < count; i++) {
          if (this.possibleCoord.size() > 0) {
            result.add(possibleCoord.remove(0));
          }
        }
      }
      this.possibleCoord.removeAll(result);
      this.weightPossible.removeAll(result);
    }
    for (Coord c : result) {
      Coord onboard = c.findCoord(this.board.getOpponentBoard());
      onboard.handleHit();
    }
    return result;
  }

  /**
   * mark the coordinates on opponent's board as hit
   * according the list return from another player
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : weightPossible) {
      c.reWeight();
    }
    for (Coord c : shotsThatHitOpponentShips) {
      c.findAndHit(this.board.getOpponentBoard());
    }
    shotsThatHitOpponentShips.addAll(coordinatorUnKnownSurrounding);
    for (Coord c : shotsThatHitOpponentShips) {
      c.placeAdjacent(this.board.getOpponentBoard(), this.possibleCoord,
          coordinatorUnKnownSurrounding);
    }
    this.weightPossible();
  }

  /**
   * weight all the coords in the board
   *
   */
  private void weightPossible() {
    LinkedList<Coord> ableToPlace = new LinkedList<>();
    this.weightPossible.removeIf(c -> !c.ableToPlace(ableToPlace));
    for (ShipType s : this.guessOpponentShip) {
      s.allPossibleH(this.board.getOpponentBoard());
      s.allPossibleV(this.board.getOpponentBoard());
    }
    this.weightPossible.sort(Coord::compareWeight);
  }

}