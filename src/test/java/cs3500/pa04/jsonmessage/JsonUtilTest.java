package cs3500.pa04.jsonmessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for JsonUtilTest
 */
class JsonUtilTest {

  /**
   * Test for serializeRecord
   */
  @Test
  void serializeRecord() {

    assertEquals(JsonUtil.serializeRecord(new CoordJson(3, 3)).toString(),
        "{\"x\":3,\"y\":3}");
  }
}