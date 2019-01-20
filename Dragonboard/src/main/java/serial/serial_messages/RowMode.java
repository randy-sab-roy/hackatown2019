package serial.serial_messages;


// Mode [0,1,2,3]
// 0: Clear line
// 1: Metro (4 lumières pour le métro)
// 2: Traffic (3 bytes de couleur)
// 3: Autobus (Pourcentage)
enum RowMode {
   CLEAR((byte) 0x0),
   METRO((byte) 0x1),
   TRAFFIC((byte) 0x2),
   AUTOBUS((byte) 0x3);

   public byte modeCode;

   RowMode(byte modeCode) {
      this.modeCode = modeCode;
   }
}
