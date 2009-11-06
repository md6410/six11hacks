package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;

/**
 * A 'basic' interpretation is simply raw, uninterpreted ink. (Lay
 * aside for the moment the paradox of 'this interpretation is not
 * interpreted').
 **/
public class BasicInterpretation extends SketchInterpretation {

  public BasicInterpretation() {
    super("basic");
    confidence = 1.0;
  }

  public void addInProgressSequence(Sequence seq) {
    
    // Get a reference to the newest point.
    Pt newest = seq.getLast();

    // In the context of 'this' interpretation, record a confidence
    // level of 0.0 (the lowest possible value) for this point.
    setPointConfidence(newest, 0.0);

    // Inform the point that it is associated with this
    // interpretation. This will be used for points to find out which
    // interpretations they are associated with and subsequently how
    // strongly associated they are with those interpretations.
    addInterpretation(newest);

    // Include the most recent point in the interpretation.
    SketchInterpretation si = new SketchInterpretation(seq); 
    Lists.setLast(parents, si);
  }

  public void finishSequence() {
    parents.add(new SketchInterpretation(new Sequence()));
  }
}
