package org.six11.modeless;

import org.six11.util.pen.Stroke;
import org.six11.util.pen.Pt;

/**
 * An interface for any class that is interested in stroke events.
 **/
public interface DrawingListener {
  
  /**
   * This is called when the user has added more data to a stroke but
   * has not finished it. The provided stroke object should be used
   * for drawing but not stored in any way, because it is going to
   * change some more before it is done.
   */
  public void strokeInProgress(Stroke stroke);

  /**
   * This is called when the user has performed a non-ink operation,
   * and the stroke that had been drawn so far (using
   * strokeInProgress) is kaput. This can be used to provide a
   * pleasent fading effect, or as simple notification that the
   * drawing operation has been cancelled.
   *
   * NOTE that if this method is called, it is implied that any
   * in-progress strokes are cancelled, replaced with this.
   */
  public void strokeFade(Stroke stroke);

  /**
   * This is called when the user has performed a complete ink
   * operation. The provided stroke contains the raw data (in "RAW")
   * as well as normalized data in "normalized".
   *
   * NOTE that if there are any in-progress strokes, this method call
   * signifies the end of that progress, so the in-progress stroke can
   * be ignored.
   */
  public void strokeComplete(Stroke stroke);

  /**
   * A blot-selection has began! Interested listeners should take this
   * as a cue to establish distances on which ever stroke is
   * appropriate.
   */
  public void selectionBegan(Pt epicenter);

  /**
   * The current blot-selection progresses!
   */
  public void selectionExpand();

  /**
   * The blot-selection that has been on is now off.
   */
  public void selectionComplete();

  /**
   * Indicates that any selection must be done away with (turn
   * selection strength back to zero).
   */
  public void selectionRemoved();

  /**
   * Indicates that an operation is beginning, using the given point
   * as the position of the mouse to start with.
   */
  public void operateBegin(Pt epicenter, String operateFunction);

  /**
   * Indicates that an operation has progressed, and the mouse is in a
   * new location.
   */
  public void operateProgress(Pt newEpicenter);

  /**
   * Indicates that an operation has completed.
   */
  public void operateComplete();
}
