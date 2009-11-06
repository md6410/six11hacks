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
import org.six11.olive2.interp.LineInterpretation;
import java.util.Stack;

/**
 *
 **/
public class LineAnalyzer extends SketchAnalyzer {

  private LineInterpretation interp;

  public LineAnalyzer(SketchBook book) {
    // TODO: shouldn't this be false, false, true?
    super(book, true, true, true, false);
  }
  
  public void handleBegin(StrokeEvent ev) {
    // only analyze finished strokes -- do nothing
  }

  public void handleProgress(StrokeEvent ev) {
    // only analyze finished strokes -- do nothing
  }

  public void handleEnd(StrokeEvent ev) {
    // The user has finished a stroke. There may be a number of
    // line-like segments to the stroke, or there may be no lines at
    // all. Some points may be involved in more than one
    // interpretation of lines.

    // For example, say I quickly draw a rectangular-like blob. I
    // didn't make perfect corners with right angles. One
    // interpretation of the rectangle's north border may be
    // long--giving more leeway in membership of the points that start
    // to drift away from the straight section. In this case the
    // rectangle will have sharper corners than if it were composed of
    // other interpretations of lines that do not admit points with as
    // much deviation.

    Sequence seq = ev.getSequence();
    int startIdx = 0;
    while ((startIdx + 1) < seq.size()) {
      startIdx = findLine(seq, startIdx);
      //      Debug.out("LineAnalyzer", "now start from " + startIdx);
    }
  }

  private int findLine(Sequence seq, int startIdx) {
    int endIdx = startIdx + 1;
    double maxAllowedDeviation;
    Stack<Search> bestBet = new Stack<Search>();
    boolean keepGoing = true;
    int ret = endIdx;

    // find the 'best bet' beginning at startIdx.
    while (seq.size() > 1 && keepGoing) {
      Search result = Search.find(startIdx, endIdx, seq, 10.0);
      if (result == null) {
	keepGoing = false;
      } else {
	maxAllowedDeviation = f(result.distance);
	if (result.maxError < maxAllowedDeviation) {
	  bestBet.push(result);
	  endIdx++;
	} else {
	  keepGoing = false;
	}
      }
    }
    
    if (bestBet.size() > 0) {
      Search bestest = extractBest(bestBet);
      LineInterpretation lineInterp = 
	new LineInterpretation(bestest.startIdx, bestest.endIdx, 
			       seq, bestest.maxError/f(bestest.distance));
      book.flagRecache();
      ret = bestest.endIdx;
      
    }
    return ret;
  }

  private Search extractBest(Stack<Search> bestBet) {
    Search best = bestBet.peek();
    Search current;
    Vec aveChange = best.getAve();
    //    Debug.out("LineAnalyzer", "average vector: " + aveChange.getX() + " , " + aveChange.getY());
    boolean keepRejecting = true;
    while (!bestBet.isEmpty() && keepRejecting) {
      current = bestBet.pop();
      if (current.numPoints > 3) {
	Vec lastPart = new Vec(current.seq.get(current.endIdx - 1), 
			       current.seq.get(current.endIdx)).getUnitVector();
	double dot = Functions.getDotProduct(aveChange, lastPart);
	if (dot > 0.9) {
	  keepRejecting = false;
	  best = current;
	}
      }
    }
    return best;
  }

  private double f(double x) {
    // max allowed deviation should be a function of the 'length' of
    // the line. short lines should have very narrow tolerances. long
    // lines should have more lenient tolerances.
    double ret = Math.max(10.0, 1.0d + Math.pow(x, 0.3));
    //    Debug.out("LineAnalyzer", "threshold at distance " + Debug.num(x) + " = " + Debug.num(ret));
    return ret;
  }

  private static class Search {
    int startIdx;
    int endIdx;
    double distance;
    double sumError;
    double maxError;
    int numPoints;
    Sequence seq;

    Vec getAve() {
      return Functions.getAverageChangeVector(seq, startIdx, endIdx).getUnitVector();
    }



    static Search find(int startIdx, int endIdx, 
		       Sequence seq, double dist) {
      // find the straight line that starts at 'startIdx' and ends on
      // or beyond 'endIdx' that is at least 'dist' units long.
      Search result = findNextSegment(startIdx, endIdx, seq, dist);
      return result;
    }

    static Search findNextSegment(int startIdx, int endIdxOrig, Sequence seq, double dist) {
      // Find the first point in the sequence that is more than 'dist'
      // units away from the starting point. The search is constrained
      // to points that are at or beyond the 'endIdx' index. If such a
      // point is found, return an instance of this private class with
      // the members set correctly. Return null otherwise.
      int endIdx = endIdxOrig;
      Line line = new Line();
      line.setStart(seq.get(startIdx));
      double d;
      Search ret = null;
      if (endIdx < seq.size()) {
	for (Pt pt : seq.forward(endIdx)) {
	  line.setEnd(pt);
	  d = line.getLength();
	  if (d >= dist) {
	    ret = new Search(startIdx, endIdx, d, seq);
	    break;
	  }
	  endIdx++;
	}
      }
      return ret;
    }

    /**
     * Create a search result about the given sequence between
     * (inclusively) the start/end points of the desired segment of
     * the sequence.
     */
    Search(int startIdx, int endIdx, double d, Sequence seq) {
      this.startIdx = startIdx;
      this.endIdx = endIdx;
      this.distance = d;
      this.seq = seq;

      // double sumError;
      // double maxError;
      // int numPoints;
      double runningMaxError = 0d;

      Line master = new Line(seq.get(startIdx), seq.get(endIdx));
      double thisDist;
      for (Pt pt : seq.from(startIdx, endIdx)) {
	thisDist = master.ptLineDist(pt);
	maxError = Math.max(maxError, thisDist);
	sumError += thisDist;
      }
      numPoints = endIdx - startIdx;
    }

  }

}
