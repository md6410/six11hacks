package org.six11.olive2.analyzers;

import org.six11.util.Debug;
import org.six11.util.data.Statistics;
import org.six11.util.pen.IntersectionData;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Line;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;
import org.six11.util.pen.Functions;
import org.six11.olive2.SketchAnalyzer;
import org.six11.olive2.StrokeEvent;
import org.six11.olive2.SketchBook;
import org.six11.olive2.interp.LineInterpretation;
import org.six11.olive2.interp.AdjacentLineInterpretation;
import java.util.Stack;
import java.util.List;

/**
 *
 **/
public class GreedyLineAnalyzer extends SketchAnalyzer {

  public GreedyLineAnalyzer(SketchBook book) {
    super(book, false, false, true, true);
  }
  
  public void handleBegin(StrokeEvent ev) {
  }

  public void handleProgress(StrokeEvent ev) {
  }

  public void handleEnd(StrokeEvent ev) {
  }

  public void handleIntegral() {
    // TODO: look at adjacent lines and determine if we can interpret
    // them as a single continuous line.
  }

}
