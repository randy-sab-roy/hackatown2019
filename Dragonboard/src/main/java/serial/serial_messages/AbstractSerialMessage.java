package serial.serial_messages;

import utils.RowMode;

// 5 bytes
// 0: No row [0,1,2,3]
// 1: Mode [0,1,2,3]
// 0: Clear line
// 1: Metro (4 lumières pour le métro)
// 2: Traffic (3 bytes de couleur)
// 3: Autobus (Pourcentage)
// 2: Arg1
// 3: Arg2
// 4: Arg3
public abstract class AbstractSerialMessage {
   private static final int MESSAGE_BYTE_COUNT = 5;
   static final int MESSAGE_ARGS_BYTE_COUNT = 3;
   private int row;
   private RowMode mode;

   public AbstractSerialMessage(int row, RowMode mode) {
      this.row = row;
      this.mode = mode;
   }

   public byte[] getBytes() {
      final byte[] bytes = new byte[MESSAGE_BYTE_COUNT];
      bytes[0] = (byte) row;
      bytes[1] = mode.modeCode;
      System.arraycopy(encodeArgs(), 0, bytes, 2, MESSAGE_ARGS_BYTE_COUNT);
      return bytes;
   }

   protected abstract byte[] encodeArgs();
}




