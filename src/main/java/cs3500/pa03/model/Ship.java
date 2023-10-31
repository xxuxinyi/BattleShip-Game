package cs3500.pa03.model;

import cs3500.pa03.model.enumuation.ShipType;
import cs3500.pa04.jsonmessage.ShipJson;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * represent a ship, ship will place itself
 */
public class Ship {
  private final ShipType type;
  private final List<Coord> location = new ArrayList<>();
  private final Coord[][] currentBoard;
  private final Random random;
  private boolean horizontal;

  /**
   * @param type is the type of this ship
   * @param currentBoard is the board that ship need to be place on
   * @param random is the random to place the ship
   */
  public Ship(ShipType type, Coord[][] currentBoard, Random random) {
    this.type = type;
    this.currentBoard = currentBoard;
    this.random = random;
    this.horizontal = random.nextBoolean();
    this.placeShip();
  }

  /**
   * @return a json for ship
   */
  public ShipJson getShipJson() {
    String direction;
    if (this.horizontal) {
      direction = "HORIZONTAL";
    } else {
      direction = "VERTICAL";
    }
    return new ShipJson(this.location.get(0).recordCoord(), this.type.size, direction);
  }


  /**
   * random choose the location based on all the
   * possible location for this certain ship type
   */
  private void placeShip() {
    LinkedList<LinkedList<Coord>> allPossibleH = this.type.allPossibleH(this.currentBoard);
    int possibleH = allPossibleH.size();
    LinkedList<LinkedList<Coord>> allPossibleV = this.type.allPossibleV(this.currentBoard);
    int possibleV = allPossibleV.size();
    if (possibleH == 0) {
      this.horizontal = false;
    }
    if (possibleV == 0) {
      this.horizontal = true;
    }
    if (this.horizontal) {
      int random = this.random.nextInt(possibleH);
      this.location.addAll(allPossibleH.get(random));
    } else {
      int random = this.random.nextInt(possibleV);
      this.location.addAll(allPossibleV.get(random));
    }
    for (Coord c : location) {
      c.markOccupied();
    }
  }

  /**
   * determine if this ship is sunk or not
   *
   * @return if this ship is sunk
   */
  public boolean sunk() {
    boolean result = true;
    for (Coord c : this.location) {
      result = result && c.isSunk();
    }
    return result;
  }
}
