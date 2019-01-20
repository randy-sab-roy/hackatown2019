package serial.serial_messages;

public class ClearMessage extends AbstractSerialMessage {

   public ClearMessage(int row) {
      super(row, RowMode.CLEAR);
   }

   @Override
   protected byte[] encodeArgs() {
      return new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];
   }
}
