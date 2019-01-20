package line;

import serial.serial_messages.AbstractSerialMessage;
import serial.serial_messages.BusMessage;
import serial.serial_messages.ClearMessage;
import serial.serial_messages.MetroMessage;
import serial.serial_messages.TrafficMessage;
import utils.Constants;
import utils.RowMode;

public class RowConfig {
   public final int rowIndex;
   public final RowMode rowMode;

   public RowConfig(int rowIndex, RowMode rowMode) {
      this.rowIndex = rowIndex;
      this.rowMode = rowMode;
   }

   public AbstractSerialMessage buildMessage() {
      switch (rowMode) {
         case OFF:
            return new ClearMessage(rowIndex);
         case METRO:
            return new MetroMessage(rowIndex, Constants.DEFAULT_METRO_STATUS);
         case TRAFFIC:
            return new TrafficMessage(rowIndex, Constants.trafficIntensityForRow(rowIndex));
         case BUS:
            return new BusMessage(rowIndex, Constants.DEFAULT_BUS_ARRIVAL_PERCENTAGE);
         default:
            return new ClearMessage(rowIndex);
      }
   }
}
