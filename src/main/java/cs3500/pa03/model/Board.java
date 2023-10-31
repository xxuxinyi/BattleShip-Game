package cs3500.pa03.model;

/**
 * represent two board in a battleship game
 */
public class Board {
  private int height;
  private int width;
  private Coord[][] board;
  private Coord[][] opponentBoard;


  /**
   * initialize the given board, place the right coordinate
   * on the right place in the 2-d array
   *
   */
  public void initBoard(int height, int width) {
    this.width = width;
    this.height = height;
    this.board = new Coord[this.height][this.width];
    this.opponentBoard = new Coord[this.height][this.width];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        board[j][i] = new Coord(i, j);

      }
    }
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        opponentBoard[j][i] = new Coord(i, j);
      }
    }

  }

  /**
   * present the board as a string builder
   *
   * @return the board situation as board
   */
  public StringBuilder present() {
    StringBuilder result = new StringBuilder();
    result.append("opponent board : \n");
    result.append("   ");
    for (int i = 0; i < width; i++) {
      result.append(i).append(" ");
    }
    result.append("\n");
    for (int i = 0; i < height; i++) {
      result.append(i).append("  ");
      for (int j = 0; j < width; j++) {
        Coord c = this.opponentBoard[i][j];
        result.append(c.drawCoord()).append(" ");
      }
      result.append("\n");
    }
    result.append("\n\n");
    result.append("your board : \n");
    result.append("   ");
    for (int i = 0; i < width; i++) {
      result.append(i).append(" ");
    }
    result.append("\n");
    for (int i = 0; i < height; i++) {
      result.append(i).append("  ");
      for (int j = 0; j < width; j++) {
        Coord c = this.board[i][j];
        result.append(c.drawCoord()).append(" ");
      }
      result.append("\n");
    }
    return result;
  }

  /**
   * @return the main board
   */
  public Coord[][] getBoard() {
    return board;
  }

  /**
   * @return the opponent board
   */
  public Coord[][] getOpponentBoard() {
    return opponentBoard;
  }
}
