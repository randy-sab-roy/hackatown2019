package serial;


import java.util.Arrays;
import java.util.List;

import serial.serial_messages.AbstractSerialMessage;

import com.fazecast.jSerialComm.SerialPort;

import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_BLOCKING;
import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_WRITE_BLOCKING;

public class SerialMessageHandler {


   private static final int BAUD_RATE = 9600;
   private static SerialMessageHandler ourInstance = new SerialMessageHandler();
   public SerialPort commPort;

   public static SerialMessageHandler getInstance() {
      return ourInstance;
   }

   private SerialMessageHandler() {
   }

   public void setCommPort(String ttyPath) {
      if (commPort != null && commPort.isOpen()) commPort.closePort();
      commPort = SerialPort.getCommPort(ttyPath);
      commPort.setComPortTimeouts(TIMEOUT_READ_BLOCKING | TIMEOUT_WRITE_BLOCKING, 500, 500);
      commPort.setBaudRate(BAUD_RATE);
      commPort.openPort();
   }

   public void sendMessage(AbstractSerialMessage message) {
      System.out.println("Sending " + message.getClass().getSimpleName());
      final byte[] messageBytes = message.getBytes();
      System.out.println(Arrays.toString(messageBytes));

      if (commPort.isOpen()) {
         commPort.writeBytes(messageBytes, messageBytes.length);
         System.out.println("Sent!");
      } else {
         System.out.println("Not sent");
      }
   }

   public void sendMessages(List<AbstractSerialMessage> serialMessages) {
      serialMessages.forEach(message -> {
         sendMessage(message);
         try {
            Thread.sleep(100);
         } catch (InterruptedException ignored) {
         }
      });
   }
}
