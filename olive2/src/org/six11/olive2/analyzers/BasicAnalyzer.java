package org.six11.olive2.analyzers;

import org.six11.util.Debug;
import org.six11.olive2.SketchAnalyzer;
import org.six11.olive2.StrokeEvent;
import org.six11.olive2.SketchBook;
import org.six11.olive2.interp.BasicInterpretation;

/**
 * The whole point of the basic analyzer is that everything that it
 * receives is interpreted as "basic". This guaranteed that each point
 * has at least one interpretation that will claim it.
 **/
public class BasicAnalyzer extends SketchAnalyzer {

  private BasicInterpretation interp;

  public BasicAnalyzer(SketchBook book) {
    super(book, true, true, true, false);
    interp = new BasicInterpretation();
  }
  
  public void handleBegin(StrokeEvent ev) {
    interp.addInProgressSequence(ev.getSequence());
  }

  public void handleProgress(StrokeEvent ev) {
    interp.addInProgressSequence(ev.getSequence());
  }

  public void handleEnd(StrokeEvent ev) {
    interp.finishSequence();
  }

}
