package metro;

public class MetroStatus {
   public final boolean isGreenStatusOk;
   public final boolean isOrangeStatusOk;
   public final boolean isYellowStatusOk;
   public final boolean isBlueStatusOk;

   public MetroStatus(boolean isGreenStatusOk, boolean isOrangeStatusOk, boolean isYellowStatusOk, boolean isBlueStatusOk) {
      this.isGreenStatusOk = isGreenStatusOk;
      this.isOrangeStatusOk = isOrangeStatusOk;
      this.isYellowStatusOk = isYellowStatusOk;
      this.isBlueStatusOk = isBlueStatusOk;
   }
}
