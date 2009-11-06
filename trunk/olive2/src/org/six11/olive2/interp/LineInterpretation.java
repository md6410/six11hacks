package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;

/**
 *
 **/
public class LineInterpretation extends SketchInterpretation {

  private Line abstractLine;
  private Sequence sequence;

  public LineInterpretation(int startIdx, int endIdx, 
			    Sequence seq, double conf) {
    super("line");
    confidence = conf; // TODO: should be a function of the error
    for (Pt pt : seq.from(startIdx, endIdx)) {
      setPointConfidence(pt, conf); // TODO: should be customized per-point
      addInterpretation(pt);
    }
    sequence = seq.copy(startIdx, endIdx+1);
    //abstractLine = new Line(seq.get(startIdx), seq.get(endIdx));
  }

  /**
   * You may also make a line interpretation based on emergent
   * properties of the sketch by using this constructor.
   */
  public LineInterpretation(Line abstractLine) {
    super("line");
    this.abstractLine = abstractLine;
  }

  public Line getLine() {
    Line ret = null;
    if (abstractLine == null) {
      ret = new Line(sequence.get(0), sequence.getLast());
    } else {
      ret = abstractLine;
    }
    return ret;
  }
  
  public Sequence getPoints() {
    return sequence;
  }

}
