package utils;

public class MetroStatus {
   public final boolean isGreenFaulty;
   public final boolean isOrangeFaulty;
   public final boolean isYellowFaulty;
   public final boolean isBlueFaulty;

   public MetroStatus(boolean isGreenFaulty, boolean isOrangeFaulty, boolean isYellowFaulty, boolean isBlueFaulty) {
      this.isGreenFaulty = isGreenFaulty;
      this.isOrangeFaulty = isOrangeFaulty;
      this.isYellowFaulty = isYellowFaulty;
      this.isBlueFaulty = isBlueFaulty;
   }
}
