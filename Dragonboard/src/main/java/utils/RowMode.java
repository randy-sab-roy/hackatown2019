package utils;


// Mode [0,1,2,3]
// 0: Off
// 1: Metro (4 lumières pour le métro)
// 2: Traffic (3 bytes de couleur)
// 3: Autobus (Pourcentage)
public enum RowMode {
   OFF((byte) 0x0, Constants.OFF_MODE_NAME),
   METRO((byte) 0x1, Constants.METRO_MODE_NAME),
   TRAFFIC((byte) 0x2, Constants.TRAFFIC_MODE_NAME),
   BUS((byte) 0x3, Constants.BUS_MODE_NAME);

   public final byte modeCode;
   public final String name;

   RowMode(byte modeCode, String name) {
      this.modeCode = modeCode;
      this.name = name;
   }

   public static RowMode getRowMode(String mode) {
      switch (mode.toLowerCase()) {
         case Constants.OFF_MODE_NAME:
            return RowMode.OFF;
         case Constants.METRO_MODE_NAME:
            return RowMode.METRO;
         case Constants.TRAFFIC_MODE_NAME:
            return RowMode.TRAFFIC;
         case Constants.BUS_MODE_NAME:
            return RowMode.BUS;
         default:
            return RowMode.OFF;
      }
   }
}
