package org.six11.olive2.analyzers;

import org.six11.util.Debug;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Functions;
import org.six11.olive2.SketchAnalyzer;
import org.six11.olive2.StrokeEvent;
import org.six11.olive2.SketchBook;
import org.six11.olive2.interp.DenseInterpretation;
import java.util.Stack;

/**
 *
 **/
public class DenseAnalyzer extends SketchAnalyzer {

  public DenseAnalyzer(SketchBook book) {
    super(book, true, true, true, false);
  }
  
  public void handleBegin(StrokeEvent ev) {
  }

  public void handleProgress(StrokeEvent ev) {
  }

  /**
   * At the end of this, the most recent stroke has been turned into a
   * DenseInterpretation. Remember that the interpretation object
   * is responsible for telling it's constituents (points) that they
   * are associated with this interpretation.
   **/
  public void handleEnd(StrokeEvent ev) {
    // TODO: need to look at the input stroke and ensure it makes
    // sense as a dense region.
    Sequence seq = ev.getSequence();
    new DenseInterpretation(seq);
    book.flagRecache();
  }

}
