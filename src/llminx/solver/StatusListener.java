package llminx.solver;

import java.util.EventListener;

/**
 *
 */
public interface StatusListener extends EventListener {

  public void statusEvent(StatusEvent aEvent);

}
