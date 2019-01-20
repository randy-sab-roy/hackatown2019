package utils;

public class Constants {
   public static final String OFF_MODE_NAME = "off";
   public static final String METRO_MODE_NAME = "metro";
   public static final String TRAFFIC_MODE_NAME = "traffic";
   public static final String BUS_MODE_NAME = "bus";
   public static final String FIRESTORE_URL = "https://ulate-6625b.firebaseio.com/";
   public static final String AUTH_CONFIG = "ulate-6625b-ad396ad2785a.json";
   public static final String BOARD_ID = "7qOUSBuRUMwagevZd8ch";
   public static final String BOARDS_KEY = "boards";
   public static final String LINES_KEY = "lines";
   public static final String SERVICE_KEY = "service";
   public static final MetroStatus DEFAULT_METRO_STATUS = new MetroStatus(false, true, false, false);
   public static final double DEFAULT_TRAFFIC_PERCENTAGE = 1.0;
   public static final double DEFAULT_BUS_ARRIVAL_PERCENTAGE = 0.6;
}
