package org.six11.olive2.interp;

import org.six11.olive2.SketchInterpretation;
import org.six11.util.Debug;
import org.six11.util.data.Lists;
import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Line;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Graham;
import org.six11.util.pen.Antipodal;
import org.six11.util.pen.ConvexHull;
import java.util.List;

/**
 *
 **/
public class DenseInterpretation extends SketchInterpretation {

  private Sequence sequence;
  private Sequence hullSeq;
  private Sequence rectSeq;

  private ConvexHull h;

  public DenseInterpretation(Sequence seq) {
    super("dense");
    sequence = seq.copy();
    addInterps();

    h = new ConvexHull(sequence.copy().getPoints());
  }
  
  private final void addInterps() {
    for (Pt pt : sequence) {
      addInterpretation(pt);
    }
  }

  public Pt getCentroid() {
    return h.getConvexCentroid();
  }

  public ConvexHull getHull() {
    return h;
  }

  public Sequence getRectAsSequence() {
    if (rectSeq == null) {
      List<Pt> r = h.getRotatedRect();
      rectSeq = new Sequence(r, true);
      rectSeq.add(r.get(0));
    }
    return rectSeq;
  }

//   public List<Pt> getHull() {
//     return h.getHull();
//   }

  public Sequence getHullAsSequence() {
    if (hullSeq == null) {
      hullSeq = new Sequence(h.getHull(), true);
      hullSeq.add(h.getHull().get(0));
    }
    return hullSeq;
  }
  
  public Sequence getPoints() {
    return sequence;
  }
  
}
