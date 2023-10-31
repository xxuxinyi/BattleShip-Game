package cs3500.pa03.model;

import cs3500.pa03.model.enumuation.CoordState;
import cs3500.pa04.jsonmessage.CoordJson;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * represent the coordinate with the state
 */
public class Coord {

  private CoordState state;
  private int weight;
  private final int coordX;
  private final int coordY;

  /**
   * Constructor for coord class
   *
   * @param x int x
   * @param y int y
   */
  public Coord(int x, int y) {
    this.coordX = x;
    this.coordY = y;
    this.state = CoordState.DEFAULT;
    this.weight = 0;
  }

  /**
   * convert a coord to coordJson
   *
   * @return a coordJson
   */
  public CoordJson recordCoord() {
    return new CoordJson(coordX, coordY);
  }

  /**
   * @return if the coordinate is hit
   */
  public boolean isSunk() {
    return this.state.equals(CoordState.HIT);
  }

  private boolean isEmpty() {
    return this.state.equals(CoordState.DEFAULT);
  }

  /**
   * if this point is default, add to the possible combination and
   * return true for able to place a ship
   *
   * @param possibleCom the possible combination of coordinate as a ship
   * @return if there is nothing here return true
   */
  public boolean ableToPlace(LinkedList<Coord> possibleCom) {
    if (this.state.equals(CoordState.DEFAULT)) {
      possibleCom.add(this);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param that is the Coord need to be compared
   * @return if this weight is larger than that weight
   */
  public int compareWeight(Coord that) {
    return Integer.compare(that.weight, this.weight);
  }

  /**
   * Find the coordinate that has same location as this one on the board
   *
   * @param board is the board that we need to find coord
   * @return the coordinate with same x and y on the board
   */
  public Coord findCoord(Coord[][] board) {
    return board[this.coordY][this.coordX];
  }

  /**
   * check if this coordinate is on the edge, and check if the adjacent is sunk or not
   * if the adjacent coordinate is not sunk add to the possibleCoord
   *
   * @param board is the board we need to find adjacent point on
   * @param possibleCoord is all the possible coordinates that have ship
   */
  private void placeRight(Coord[][] board, List<Coord> possibleCoord) {
    int width = board[1].length;
    if (this.coordX < width - 1) {
      Coord right = board[coordY][coordX + 1];
      if (right.isEmpty() && !possibleCoord.contains(right)) {
        possibleCoord.add(right);
      }
    }
  }

  /**
   * check if this coordinate is on the edge, and check if the adjacent is sunk or not
   * if the adjacent coordinate is not sunk add to the possibleCoord
   *
   * @param board is the board we need to find adjacent point on
   * @param possibleCoord is all the possible coordinates that have ship
   */
  private void placeTop(Coord[][] board, List<Coord> possibleCoord) {
    if (this.coordY > 0) {
      Coord top = board[coordY - 1][coordX];
      if (top.isEmpty() && !possibleCoord.contains(top)) {
        possibleCoord.add(top);
      }
    }
  }

  /**
   * check if this coordinate is on the edge, and check if the adjacent is sunk or not
   * if the adjacent coordinate is not sunk add to the possibleCoord
   *
   * @param board is the board we need to find adjacent point on
   * @param possibleCoord is all the possible coordinates that have ship
   */
  private void placeBottom(Coord[][] board, List<Coord> possibleCoord) {
    int height = board.length;
    if (this.coordY < height - 1) {
      Coord bottom = board[coordY + 1][coordX];
      if (bottom.isEmpty() && !possibleCoord.contains(bottom)) {
        possibleCoord.add(bottom);
      }
    }
  }

  /**
   * check if this coordinate is on the edge, and check if the adjacent is sunk or not
   * if the adjacent coordinate is not sunk add to the possibleCoord
   *
   * @param board is the board we need to find adjacent point on
   * @param possibleCoord is all the possible coordinates that have ship
   */
  private void placeLeft(Coord[][] board, List<Coord> possibleCoord) {
    if (this.coordX > 0) {
      Coord left = board[coordY][coordX - 1];
      if (left.isEmpty() && !possibleCoord.contains(left)) {
        possibleCoord.add(left);
      }
    }
  }

  /**
   * return a coordinate to a coordinate adjacent
   *
   * @param board is the board where coord is
   * @param possibleCoord  is all the possible coordinates that have ship
   * @param preHitCoord coords hit before and surronding don't have sunk coord
   */
  public void placeAdjacent(Coord[][] board, List<Coord> possibleCoord, 
                            List<Coord> preHitCoord) {
    final int height = board.length;
    final int width = board[1].length;
    boolean surroundingSunk = false;
    if (this.coordX > 0) {
      surroundingSunk = leftSunk(board, possibleCoord);
    }
    if (this.coordY < height - 1) {
      surroundingSunk = surroundingSunk || bottomSunk(board, possibleCoord);
    }
    if (this.coordY > 0) {
      surroundingSunk = surroundingSunk || topSunk(board, possibleCoord);
    }
    if (this.coordX < width - 1) {
      surroundingSunk = surroundingSunk || rightSunk(board, possibleCoord);
    }
    if (surroundingSunk) {
      preHitCoord.remove(this);
    }
    if (preHitCoord.contains(this)) {
      this.placeRight(board, possibleCoord);
      this.placeBottom(board, possibleCoord);
    } else if (!surroundingSunk) {
      this.placeLeft(board, possibleCoord);
      this.placeTop(board, possibleCoord);
      preHitCoord.add(this);
    }
  }

  /**
   * check if the right of this coordinate is sunk or not,
   * if the right is sunk, place the left coord and right of right coord.
   * also check the top right to seek for other possibility.
   *
   * @param board is the board where coord is
   * @param possibleCoord  is all the possible coordinates that have ship
   * @return if the right of this coord is sunk or not
   */
  private boolean rightSunk(Coord[][] board, List<Coord> possibleCoord) {
    Coord right = board[coordY][coordX + 1];
    if (right.isSunk()) {
      this.placeLeft(board, possibleCoord);
      right.placeRight(board, possibleCoord);
      if (this.checkTopRightSunk(board)) {
        List<Coord> lessPriority = new LinkedList<>();
        this.placeTop(board, lessPriority);
        this.placeBottom(board, lessPriority);
        for (Coord c : lessPriority) {
          c.weight += 100;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * check if the top of this coordinate is sunk or not,
   * if the right is sunk, place the bottom coord and top of top coord.
   *
   * @param board is the board where coord is
   * @param possibleCoord  is all the possible coordinates that have ship
   * @return if the right of this coord is sunk or not
   */
  private boolean topSunk(Coord[][] board, List<Coord> possibleCoord) {
    Coord top = board[coordY - 1][coordX];
    if (top.isSunk()) {
      this.placeBottom(board, possibleCoord);
      top.placeTop(board, possibleCoord);
      if (this.checkTopRightSunk(board)) {
        List<Coord> lessPriority = new LinkedList<>();
        this.placeRight(board, lessPriority);
        this.placeLeft(board, lessPriority);
        for (Coord c : lessPriority) {
          c.weight += 100;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * check if the right of this coordinate is sunk or not,
   * if the right is sunk, place the left coord and right of right coord.
   * also check the bottom left to seek for other possibility.
   *
   * @param board is the board where coord is
   * @param possibleCoord  is all the possible coordinates that have ship
   * @return if the right of this coord is sunk or not
   */
  private boolean bottomSunk(Coord[][] board, List<Coord> possibleCoord) {
    Coord bottom = board[coordY + 1][coordX];
    if (bottom.isSunk()) {
      this.placeTop(board, possibleCoord);
      bottom.placeBottom(board, possibleCoord);
      if (this.checkBottomLeftSunk(board)) {
        List<Coord> lessPriority = new LinkedList<>();
        this.placeRight(board, lessPriority);
        this.placeLeft(board, lessPriority);
        for (Coord c : lessPriority) {
          c.weight += 100;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * check if the right of this coordinate is sunk or not,
   * if the right is sunk, place the left coord and right of right coord.
   * also check the bottom left to seek for other possibility.
   *
   * @param board is the board where coord is
   * @param possibleCoord is all the possible coordinates that have ship
   * @return if the right of this coord is sunk or not
   */
  private boolean leftSunk(Coord[][] board, List<Coord> possibleCoord) {
    Coord left = board[coordY][coordX - 1];
    if (left.isSunk()) {
      this.placeRight(board, possibleCoord);
      left.placeLeft(board, possibleCoord);
      if (this.checkBottomLeftSunk(board)) {
        List<Coord> lessPriority = new LinkedList<>();
        this.placeTop(board, lessPriority);
        this.placeBottom(board, lessPriority);
        for (Coord c : lessPriority) {
          c.weight += 100;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * @param board is the board where coord is
   * @return if the top right is sunk
   */
  private boolean checkTopRightSunk(Coord[][] board) {
    try {
      Coord topRight = board[this.coordY - 1][this.coordX + 1];
      return topRight.isSunk();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @param board is the board where coord is
   * @return if the bottom left is sunk
   */
  private boolean checkBottomLeftSunk(Coord[][] board) {
    try {
      Coord bottomLeft = board[this.coordY + 1][this.coordX - 1];
      return bottomLeft.isSunk();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * change the state to hit if there is a ship
   *
   * @return true if this coordinate has ship
   */
  public boolean handleHit() {
    CoordState original = this.state;
    switch (original) {
      case SHIP -> {
        this.state = CoordState.HIT;
        return true;
      }
      case HIT -> {
        return true;
      }
      default -> {
        this.state = CoordState.MISSED;
        return false;
      }
    }
  }

  /**
   *  Find the coordinate that has same location as this one on the board, and mark as hit
   *
   * @param board is the board that we need to find
   */
  public void findAndHit(Coord[][] board) {
    Coord onboard = board[this.coordY][this.coordX];
    onboard.state = CoordState.HIT;
  }

  /**
   * reset the weight to 0
   */
  public void reWeight() {
    this.weight = 0;
  }

  /**
   * @return the presentation of a Coord as a char
   */
  public char drawCoord() {
    return state.present;
  }

  /**
   * mark this coordinate as has part of ship on it
   */
  public void markOccupied() {
    this.state = CoordState.SHIP;
  }

  /**
   * add one to the weight for this coordinate
   */
  public void addWeight() {
    this.weight++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return coordX == coord.coordX && coordY == coord.coordY;
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordX, coordY);
  }

}
