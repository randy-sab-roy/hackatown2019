package serial.serial_messages;

import utils.RowMode;

public class BusMessage extends AbstractSerialMessage {

   private final double arrivalPercentage;

   public BusMessage(int row, double arrivalPercentage) {
      super(row, RowMode.BUS);
      this.arrivalPercentage = arrivalPercentage;
   }

   @Override
   protected byte[] encodeArgs() {
      final byte[] bytes = new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];

      bytes[0] = ((byte) (100.0 * Math.min(Math.max(arrivalPercentage, 0), 1.0)));
      return bytes;
   }
}
