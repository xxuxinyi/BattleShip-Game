package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumuation.ShipType;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * the view for BattleShipView
 */
public class BattleShipView {
  private final Scanner input;
  private final Appendable output;


  public BattleShipView(Readable input, Appendable output) {
    this.input = new Scanner(input);
    this.output = output;
  }

  /**
   * return the list of size until there is two integers
   *
   * @param prompt to ask the size of the board
   * @return the array with the size of the board
   * @throws IOException if there is mistake with input and output
   */
  public int[] askSize(String prompt) throws IOException {
    int[] result = new int[2];
    output.append(prompt);
    while (true) {
      String line = this.input.nextLine();
      Scanner sc = new Scanner(line);
      try {
        result[0] = sc.nextInt();
        result[1] = sc.nextInt();
        return result;
      } catch (Exception e) {
        output.append("Please enter two integers : ");
      }
    }
  }


  /**
   * return until the map have all the information
   *
   * @param prompt to ask the fleet data
   * @return the map with all the information
   * @throws IOException if there is problem with input and output
   */
  public Map<ShipType, Integer> askFleet(String prompt) throws IOException {
    output.append(prompt);
    while (true) {
      String line = this.input.nextLine();
      Scanner sc = new Scanner(line);
      try {
        int c = sc.nextInt();
        int b = sc.nextInt();
        int d = sc.nextInt();
        int s = sc.nextInt();
        Map<ShipType, Integer> map = new HashMap<>();
        map.put(ShipType.SUBMARINE, s);
        map.put(ShipType.CARRIER, c);
        map.put(ShipType.BATTLESHIP, b);
        map.put(ShipType.DESTROYER, d);
        return map;
      } catch (Exception e) {
        output.append("Please enter four integers : ");
      }
    }
  }

  /**
   * take the list of coords from user, return until there is enough pair of integer
   *
   * @param prompt to ask the shot
   * @param shipCount is used for how many line the scanner need
   * @return the list of coords from user input
   * @throws IOException if there is mistake when taking input and output
   */
  public List<Coord> takeShot(String prompt, int shipCount) throws IOException {
    output.append(prompt);
    List<Coord> result = new LinkedList<>();
    for (int i = 0; i < shipCount; i++) {
      String line = this.input.nextLine();
      Scanner sc = new Scanner(line);
      try {
        int x = sc.nextInt();
        int y = sc.nextInt();
        result.add(new Coord(x, y));
      } catch (Exception e) {
        output.append("Please enter 2 integers in one line, Please reenter: \n");
        return this.takeShot("", shipCount);
      }
    }
    return result;
  }

  /**
   * append the prompt to user
   *
   * @param prompt is the information, this program want to tell user
   */
  public void presentPrompt(String prompt)  {
    try {
      this.output.append(prompt);
    } catch (IOException e) {
      System.out.println("Unhandled output situation");
    }
  }

}
