package serial.serial_messages;

import utils.RowMode;

public class ClearMessage extends AbstractSerialMessage {

   public ClearMessage(int row) {
      super(row, RowMode.OFF);
   }

   @Override
   protected byte[] encodeArgs() {
      return new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];
   }
}
