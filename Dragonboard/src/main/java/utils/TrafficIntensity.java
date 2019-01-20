package utils;

import java.awt.Color;

public enum TrafficIntensity {
   LOW(Color.GREEN),
   MED(Color.ORANGE),
   HIGH(Color.RED);
   public int color;

   TrafficIntensity(Color color) {
      this.color = color.getRGB();
   }
}
