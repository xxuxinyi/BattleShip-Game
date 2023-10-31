package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test for Driver
 */
class DriverTest {

  /**
   * a driver test
   */
  @Test
  void driver() {
    String[] argument1 = new String[1];
    argument1[0] = "";
    assertThrows(IllegalArgumentException.class, () -> Driver.driver(argument1));
  }
}