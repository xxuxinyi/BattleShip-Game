package cs3500.pa03.model;

import cs3500.pa03.model.enumuation.GameResult;
import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa03.view.BattleShipView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * the abstract class for player, abstract something
 * that AIPlayer and normal player has in common
 */
public abstract class AbstractPlayer implements Player {
  protected final List<Ship> remainShip;
  protected final BattleShipView view;
  protected final Random random;
  protected final Board board;
  protected List<ShipType> guessOpponentShip;

  /**
   * @param view           is the view of this program
   * @param remainShip     is the remian ship this player had
   * @param random         is the random to place the ship
   * @param board          is the two boards for this player
   */
  public AbstractPlayer(BattleShipView view, Random random, List<Ship> remainShip, Board board) {
    this.view = view;
    this.random = random;
    this.remainShip = remainShip;
    this.board = board;
    this.guessOpponentShip = new LinkedList<>();
  }




  /**
   * Given the specifications for a BattleSalvo board,
   * return a list of ships with their locations on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the list of ship with its location
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board.initBoard(height, width);
    final Integer carrier = specifications.get(ShipType.CARRIER);
    final Integer battleship = specifications.get(ShipType.BATTLESHIP);
    final Integer destroyer = specifications.get(ShipType.DESTROYER);
    final Integer submarine = specifications.get(ShipType.SUBMARINE);
    ArrayList<Ship> result = new ArrayList<>();
    for (int i = 0; i < carrier; i += 1) {
      Ship s = new Ship(ShipType.CARRIER, board.getBoard(), random);
      result.add(s);
    }
    for (int i = 0; i < battleship; i += 1) {
      result.add(new Ship(ShipType.BATTLESHIP, board.getBoard(), random));
    }
    for (int i = 0; i < destroyer; i += 1) {
      result.add(new Ship(ShipType.DESTROYER, board.getBoard(), random));
    }
    for (int i = 0; i < submarine; i += 1) {
      result.add(new Ship(ShipType.SUBMARINE, board.getBoard(), random));
    }
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType type = entry.getKey();
      Integer count = entry.getValue();
      for (int i = 0; i < count; i++) {
        this.guessOpponentShip.add(type);
      }
    }
    this.remainShip.addAll(result);
    return result;
  }

  /**
   * return the shot that hit the ship and also mark those ship as hit on player's board
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the shot that hit the ship
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> result = new LinkedList<>();
    for (Coord c : opponentShotsOnBoard) {
      Coord onBoard = c.findCoord(board.getBoard());
      if (onBoard.handleHit()) {
        result.add(c);
      }
    }
    List<Ship> copy = List.copyOf(this.remainShip);
    for (Ship s : copy) {
      if (s.sunk()) {
        this.remainShip.remove(s);
      }
    }
    return result;
  }



  /**
   * return the information to view
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    switch (result) {
      case WIN -> this.view.presentPrompt("You win\n" + reason);
      case LOSE -> this.view.presentPrompt("You lose\n" + reason);
      case DRAW -> this.view.presentPrompt("Game tile\n" + reason);
      default -> { }
    }
  }
}
