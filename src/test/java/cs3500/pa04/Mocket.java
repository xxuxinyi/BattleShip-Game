package cs3500.pa04;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * Mock a Socket to simulate behaviors of ProxyControllers being connected to a server.
 */
public class Mocket extends Socket {


  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * Constructor for mocket class
   *
   * @param testLog what the server has received from the client
   * @param toSend what the server will send to the client
   */
  public Mocket(ByteArrayOutputStream testLog, List<String> toSend) {

    this.testLog = testLog;

    // Set up the list of inputs as separate messages of JSON for the ProxyDealer to handle
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String message : toSend) {
      printWriter.println(message);
    }
    this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  /**
   * get the test input for ProxyController test
   *
   * @return this input
   */
  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  /**
   * get the testlog for ProxyController test
   *
   * @return this testlog
   */
  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }

}