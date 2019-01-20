package serial.serial_messages;

import java.awt.Color;

import static utils.Util.constrain;

public class TrafficMessage extends AbstractSerialMessage {

   private static final int NO_TRAFFIC_HUE = 113;
   private static final int HEAVY_TRAFFIC_HUE = 13;
   private static final float TRAFFIC_SATURATION = 1f;
   private static final float TRAFFIC_BRIGHTNESS = 1f;

   private final double trafficPercentage;

   public TrafficMessage(int row, double trafficPercentage) {
      super(row, RowMode.TRAFFIC);
      this.trafficPercentage = trafficPercentage;
   }

   @Override
   protected byte[] encodeArgs() {
      final byte[] bytes = new byte[AbstractSerialMessage.MESSAGE_ARGS_BYTE_COUNT];

      final double hue = 255 / (NO_TRAFFIC_HUE + ((HEAVY_TRAFFIC_HUE - NO_TRAFFIC_HUE) * constrain(trafficPercentage, 0, 1)));

      final int rgb = Color.HSBtoRGB((float) hue, TRAFFIC_SATURATION, TRAFFIC_BRIGHTNESS);
      bytes[0] = (byte) ((rgb >> 0x16) & 0xff);
      bytes[1] = (byte) ((rgb >> 0xf) & 0xff);
      bytes[2] = (byte) ((rgb >> 0x8) & 0xff);
      return bytes;
   }
}
