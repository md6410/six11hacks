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
public class AdjacentLineAnalyzer extends SketchAnalyzer {

  public AdjacentLineAnalyzer(SketchBook book) {
    super(book, false, false, true, true);
  }
  
  public void handleBegin(StrokeEvent ev) {
  }

  public void handleProgress(StrokeEvent ev) {
  }

  public void handleEnd(StrokeEvent ev) {
  }

  public void handleIntegral() {
    List lines = book.getInterpretations("line");
    List knownAdjacentLines = book.getInterpretations("adjacent_line");
    LineInterpretation a, b;
    for (int i=0; i < lines.size() - 1; i++) {
      a = (LineInterpretation) lines.get(i);
      for (int j=i+1; j < lines.size(); j++) {
	b = (LineInterpretation) lines.get(j);
	if (doesListContainExistingAdjacentLine(knownAdjacentLines, a, b)) {
	  // Debug.out("AdjacentAnalyzer", "Avoiding duplicate interpretation for " + a + " and " + b);
	} else {
	  AdjacencyInfo info = getAdjacencyInfo(a.getLine(), b.getLine());
	  if (info.adjacent) {
	    AdjacentLineInterpretation interp = 
	      new AdjacentLineInterpretation(a, b);
	    book.flagRecache();
	  }
	}
      }
    }
  }

  private boolean doesListContainExistingAdjacentLine
    (List known, LineInterpretation a, LineInterpretation b) {
    boolean ret = false;
    for (int i=0; i < known.size(); i++) {
      AdjacentLineInterpretation adj = (AdjacentLineInterpretation) known.get(i);
      if ((adj.getLineA().equals(a) && adj.getLineB().equals(b)) ||
	  (adj.getLineA().equals(b) && adj.getLineB().equals(a))) {
	ret = true;
	break;
      }
    }
    return ret;
  }

  private AdjacencyInfo getAdjacencyInfo(Line a, Line b) {
    AdjacencyInfo ret = new AdjacencyInfo();
    IntersectionData id = Functions.getIntersectionData(a, b);
    // Debug.out("AdjacentAnalyzer", "Line one param: " + Debug.num(id.getLineOneParam()));
    // Debug.out("AdjacentAnalyzer", "Line two param: " + Debug.num(id.getLineTwoParam()));
    if (id.intersectsInSegments()) {
      ret.adjacent = true;
      ret.intersectionPt = id.getIntersection();
      ret.error = 0d;
    } else {
      double min = Statistics.minimum
	( Functions.getDistanceBetween(a.getStart(), b.getStart()),
	  Functions.getDistanceBetween(a.getStart(), b.getEnd()),
	  Functions.getDistanceBetween(a.getEnd(), b.getStart()),
	  Functions.getDistanceBetween(a.getEnd(), b.getEnd()) );
      if (min < 15.0) {
	ret.adjacent = true;
	ret.intersectionPt = id.getIntersection();
	ret.error = min;
      } else {
	// TODO: look for 'T' junctures
      }
    }
    return ret;
  }

  private static class AdjacencyInfo {
    boolean adjacent;
    Pt intersectionPt;
    Line a_1, a_2, b_1, b_2;
    double error;
  }

}
