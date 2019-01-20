import com.fazecast.jSerialComm.SerialPort;

public class Main {

   public static void main(String[] args) {
      final SerialPort commPort = SerialPort.getCommPort("/dev/cu.wchusbserial1460");
      commPort.openPort();

      String test = "Hello World";

      commPort.writeBytes(test.getBytes(), test.getBytes().length);

      commPort.closePort();
   }

}
