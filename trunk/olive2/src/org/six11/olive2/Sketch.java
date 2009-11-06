package org.six11.olive2;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import org.six11.util.pen.Sequence;

/**
 * A sketch is a list of sequences that don't necessarily have a
 * relationship to any Glyph or Form.
 **/
public class Sketch {
  List<Sequence> sequences;

  public Sketch() {
    sequences = new ArrayList<Sequence>();
  }

  public void addSequence(Sequence seq) {
    sequences.add(seq);
  }

  public void clearSequences() {
    sequences.clear();
  }

  public List<Sequence> getSequences() {
    return sequences;
  }

  public int size() {
    return sequences.size();
  }

  public Rectangle2D getBoundingBox() {
    Rectangle2D allSequences = null;
    for (Sequence seq : getSequences()) {
      if (allSequences == null) {
	allSequences = seq.getBounds2D();
      } else {
	allSequences.add(seq.getBounds2D());
      }
    }
    
    return allSequences;
  }

}
