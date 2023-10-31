package cs3500.pa03.model.enumuation;

import cs3500.pa03.model.Coord;
import java.util.LinkedList;

/**
 * represent the different type of ship
 */
public enum ShipType {
  SUBMARINE(3), DESTROYER(4), BATTLESHIP(5), CARRIER(6);

  public final int size;

  ShipType(int size) {
    this.size = size;
  }

  /**
   * generate all the possible way a ship will generate horizontal
   *
   * @param board the board we need to place the ship on
   * @return all the possible combination for placing the ship
   */
  public LinkedList<LinkedList<Coord>> allPossibleH(Coord[][] board) {
    int width = board[1].length;
    LinkedList<LinkedList<Coord>> result = new LinkedList<>();
    // Place Horizontally
    for (int i = 0; i < width - this.size + 1; i++) {
      for (Coord[] coords : board) {
        boolean possible = true;
        LinkedList<Coord> possibleCom = new LinkedList<>();
        for (int j = 0; j < this.size; j++) {
          Coord current = coords[i + j];
          possible = current.ableToPlace(possibleCom);
          if (!possible) {
            break;
          }
        }
        if (possible) {
          result.add(possibleCom);
          for (Coord c : possibleCom) {
            c.addWeight();
          }
        }
      }
    }
    return result;
  }

  /**
   * generate all the possible way a ship will generate vertical
   *
   * @param board the board we need to place the ship on
   * @return all the possible combination for placing the ship
   */
  public LinkedList<LinkedList<Coord>> allPossibleV(Coord[][] board) {
    int height = board.length;
    int width = board[1].length;
    LinkedList<LinkedList<Coord>> result = new LinkedList<>();
    //Place vertically
    for (int i = 0; i < height - this.size + 1; i++) {
      for (int k = 0; k < width; k++) {
        boolean possible = true;
        LinkedList<Coord> possibleCom = new LinkedList<>();
        for (int j = 0; j < this.size; j++) {
          Coord current = board[i + j][k];
          possible = current.ableToPlace(possibleCom);
          if (!possible) {
            break;
          }
        }
        if (possible) {
          result.add(possibleCom);
          for (Coord c : possibleCom) {
            c.addWeight();
          }
        }
      }
    }
    return result;
  }
}
