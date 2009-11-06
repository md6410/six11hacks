package org.six11.olive2;

import org.six11.util.pen.Sequence;

/**
 * A Stroke has completed. Success!
 **/
public class StrokeEvent {

  public static final int STROKE_BEGIN = 0;
  public static final int STROKE_PROGRESS = 1;
  public static final int STROKE_END = 2;

  Sequence sequence;
  Object source;
  long when;
  int type;

  /**
   * Create a new StrokeEvent. The source object should always be
   * "this" in the calling context.
   */
  public StrokeEvent(Object source, Sequence sequence, long when, int type) {
    this.source = source;
    this.sequence = sequence;
    this.when = when;
    this.type = type;
  }

  public StrokeEvent(Object source, Sequence sequence, int type) {
    this(source, sequence, System.currentTimeMillis(), type);
  }

  public String toString() {
    String kind = "";
    if (isBegin()) { kind = "begun"; }
    if (isProgress()) { kind = "progress"; }
    if (isEnd()) { kind = "end"; }

    return "Stroke " + kind + " (" + sequence.size() + " points)";
  }

  public Object getSource() {
    return source;
  }

  public Sequence getSequence() {
    return sequence;
  }

  public long getWhen() {
    return when;
  }

  public int getType() {
    return type;
  }

  public boolean isBegin() {
    return type == STROKE_BEGIN;
  }

  public boolean isProgress() {
    return type == STROKE_PROGRESS;
  }

  public boolean isEnd() {
    return type == STROKE_END;
  }


}
