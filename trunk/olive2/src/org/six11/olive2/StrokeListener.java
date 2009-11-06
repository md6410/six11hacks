package org.six11.olive2;

/**
 * Used by anything that is interested in tracking the progress of
 * user input.
 **/
public interface StrokeListener {

  public void handleStrokeAction(StrokeEvent ev);

}
