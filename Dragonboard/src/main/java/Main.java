import java.util.List;
import java.util.stream.Collectors;

import db.DatabaseHandler;
import line.RowConfig;
import serial.SerialMessageHandler;
import serial.serial_messages.AbstractSerialMessage;
import utils.Constants;
import utils.RowMode;
import utils.Util;

public class Main {

   public static void main(String[] args) throws InterruptedException {
      if (args.length < 1) {
         System.err.println("Please specify serial tty path");
         System.exit(-1);
      }

      final SerialMessageHandler serialMessageHandler = SerialMessageHandler.getInstance();
      serialMessageHandler.setCommPort(args[0]);
      Thread.sleep(3000); // Wait for serial reset

      final DatabaseHandler instance = DatabaseHandler.getInstance();
      instance.setRowConfigChangeEventListener((value, error) -> {
         if (error != null) {
            error.printStackTrace();
            return;
         }

         final List<AbstractSerialMessage> serialMessages = value.getDocuments()
               .stream()
               .map(document -> new RowConfig(Integer.parseInt(document.getId()), RowMode.getRowMode(document.get(Constants.SERVICE_KEY).toString())))
               .map(RowConfig::buildMessage)
               .collect(Collectors.toList());

         serialMessageHandler.sendMessages(serialMessages);
      });

      Util.spin();
   }

}
