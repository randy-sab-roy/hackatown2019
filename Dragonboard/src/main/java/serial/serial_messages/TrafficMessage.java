package serial.serial_messages;

import utils.RowMode;
import utils.TrafficIntensity;

public class TrafficMessage extends AbstractSerialMessage {

   private final TrafficIntensity trafficIntensity;

   public TrafficMessage(int row, TrafficIntensity trafficIntensity) {
      super(row, RowMode.TRAFFIC);
      this.trafficIntensity = trafficIntensity;
   }

   @Override
   protected byte[] encodeArgs() {
      final byte[] bytes = new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];

      final int rgb = trafficIntensity.color;
      bytes[0] = (byte) ((rgb >> 16) & 0xff);
      bytes[1] = (byte) ((rgb >> 8) & 0xff);
      bytes[2] = (byte) ((rgb >> 0) & 0xff);
      return bytes;
   }
}
