package llminx.solver;

/**
 *
 */
public class StatusEvent {

  private StatusEventType fType;
  private String fMessage;
  private double fProgress;

  public StatusEvent(StatusEventType type, String aMessage, double progress) {
    fType = type;
    fMessage = aMessage;
    fProgress = progress;
  }

  public StatusEventType getType() {
    return fType;
  }

  public String getMessage() {
    return fMessage;
  }

  public double getProgress() {
    return fProgress;
  }
}
