package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.BattleShipView;
import cs3500.pa04.BattleShipController;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * test for BattleShipController
 */
class ManualBattleShipControllerTest {
  Readable read;
  Appendable write;
  BattleShipView view;
  BattleShipController controller;

  /**
   * test the round method
   *
   * @throws IOException if encounter unexpected error
   */
  @Test
  void round() throws IOException {
    read = new StringReader("""
        17 6\s
        8 20
        20 20
        5 5
        5 7
        7 5
        6 6
        1 0 1 1
        1 2 2 2
        1 1 0 0
        1 1 1 1
        1 1
        2 2
        3 3
        4 4
        5 5
        2 1
        3 1
        4 1
        1 5
        3 3
        4 4
        5 5
        2 1
        3 1
        4 1
        1 5
        3 3
        4 4
        5 5
        2 1
        3 1
        4 1
        1 5
        3 1
        4 1
        1 5
        3 3
        4 6
        3 1
        0 0
        0 4
        
        """);
    write = new StringBuilder();
    view = new BattleShipView(read, write);
    this.controller = new ManualBattleShipController(this.view, new Random(1));
    controller.round();
    assertEquals(write.toString(), """
        Hello! Welcome to the OOD BattleSalvo Game!\s
        Please enter a valid height and width below:\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!\s
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . . . . . .\s
        2  . . . . . .\s
        3  . . . . . .\s
        4  . . . . . .\s
        5  . . . . . .\s


        your board :\s
           0 1 2 3 4 5\s
        0  . S . . . S\s
        1  . S . . . S\s
        2  . S . . . S\s
        3  S S S S S S\s
        4  S S S S S S\s
        5  . . . . . .\s
        Please enter 4 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M . . . .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . . . . . .\s


        your board :\s
           0 1 2 3 4 5\s
        0  . S . . . S\s
        1  . S . . . S\s
        2  . S M M . S\s
        3  S S H H S S\s
        4  S S S S S S\s
        5  . . . . . .\s
        Please enter 4 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . . . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . S . . . S\s
        1  . H . . M S\s
        2  . S M M . S\s
        3  S H H H H S\s
        4  S S S S S S\s
        5  . . . . . .\s
        Please enter 4 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . H . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . H . . . S\s
        1  M H . . M S\s
        2  . S M M . S\s
        3  H H H H H H\s
        4  S S S S S S\s
        5  . . . . . .\s
        Please enter 3 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . H . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . H . . . S\s
        1  M H . . M S\s
        2  . H M M . S\s
        3  H H H H H H\s
        4  S S H H S S\s
        5  . . M . . .\s
        Please enter 2 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . H . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . H . M . S\s
        1  M H . . M S\s
        2  M H M M . S\s
        3  H H H H H H\s
        4  S H H H H S\s
        5  . . M . . .\s
        Please enter 2 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . H . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . H . M . H\s
        1  M H . . M S\s
        2  M H M M . S\s
        3  H H H H H H\s
        4  H H H H H H\s
        5  . M M . . .\s
        Please enter 1 shorts :
        opponent board :\s
           0 1 2 3 4 5\s
        0  . . . . . .\s
        1  . M M H H .\s
        2  . . M . . .\s
        3  . . . H . .\s
        4  . . . . H .\s
        5  . H . . . M\s


        your board :\s
           0 1 2 3 4 5\s
        0  . H . M M H\s
        1  M H . . M S\s
        2  M H M M . S\s
        3  H H H H H H\s
        4  H H H H H H\s
        5  M M M M M .\s
        Please enter 1 shorts :
        You lose
        there is no ship left on your board""");
  }
}