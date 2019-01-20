package serial.serial_messages;

import metro.MetroStatus;

public class MetroMessage extends AbstractSerialMessage {

   private final MetroStatus metroStatus;

   public MetroMessage(int row, MetroStatus metroStatus) {
      super(row, RowMode.METRO);
      this.metroStatus = metroStatus;
   }

   @Override
   protected byte[] encodeArgs() {
      final byte[] bytes = new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];
      int encodedMetroStatus = 0;
      encodedMetroStatus += metroStatus.isGreenStatusOk ? (1 << 0x3) : 0;
      encodedMetroStatus += metroStatus.isOrangeStatusOk ? (1 << 0x2) : 0;
      encodedMetroStatus += metroStatus.isYellowStatusOk ? (1 << 0x1) : 0;
      encodedMetroStatus += metroStatus.isBlueStatusOk ? (1 << 0x0) : 0;

      bytes[0] = ((byte) encodedMetroStatus);
      return bytes;
   }

}
