package llminx.solver;

/**
 *
 */
public class SystemOutStatusListener implements StatusListener {


  public void statusEvent(StatusEvent aEvent) {
    System.out.println(aEvent.getMessage());
  }
  
}
