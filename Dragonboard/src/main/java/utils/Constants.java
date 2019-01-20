package utils;

public class Constants {
   public static final String OFF_MODE_NAME = "off";
   public static final String METRO_MODE_NAME = "metro";
   public static final String TRAFFIC_MODE_NAME = "trafic";
   public static final String BUS_MODE_NAME = "bus";
   public static final String FIRESTORE_URL = "https://ulate-6625b.firebaseio.com/";
   public static final String AUTH_CONFIG = "./ulate-6625b-ad396ad2785a.json";
   public static final String BOARD_ID = "7qOUSBuRUMwagevZd8ch";
   public static final String BOARDS_KEY = "boards";
   public static final String LINES_KEY = "lines";
   public static final String SERVICE_KEY = "service";
   public static final MetroStatus DEFAULT_METRO_STATUS = new MetroStatus(false, true, false, false);
   public static final double DEFAULT_BUS_ARRIVAL_PERCENTAGE = 0.6;

   public static TrafficIntensity trafficIntensityForRow(int rowIndex) {
      switch (rowIndex) {
         case 0:
         case 1:
            return TrafficIntensity.HIGH;
         case 2:
         case 3:
            return TrafficIntensity.LOW;
         default:
            return TrafficIntensity.HIGH;
      }
   }
}
