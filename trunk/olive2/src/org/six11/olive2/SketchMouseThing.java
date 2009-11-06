package org.six11.olive2;

import java.awt.event.MouseEvent;
import org.six11.util.pen.MouseThing;
import org.six11.util.pen.Pt;
import org.six11.util.Debug;
import org.six11.util.pen.Sequence;

/**
 * 
 **/
class SketchMouseThing extends MouseThing {

  Sequence currentSequence = null;
  SketchBook eventCannon;

  SketchMouseThing(SketchBook capture) {
    this.eventCannon = capture;
  }

  public void mousePressed(MouseEvent ev) {
    currentSequence = new Sequence();
    currentSequence.add(new Pt(ev));
    eventCannon.fire(new StrokeEvent(this, currentSequence, ev.getWhen(),
				     StrokeEvent.STROKE_BEGIN));
  }

  public void mouseDragged(MouseEvent ev) {
    currentSequence.add(new Pt(ev.getPoint(), ev.getWhen()));
    eventCannon.fire(new StrokeEvent(this, currentSequence, ev.getWhen(),
				     StrokeEvent.STROKE_PROGRESS));
  }

  public void mouseReleased(MouseEvent ev) {
    eventCannon.fire(new StrokeEvent(this, currentSequence, ev.getWhen(),
				     StrokeEvent.STROKE_END));
  }

  private void bug(String what) {
    Debug.out("SketchMouseThing", what);
  }

}
