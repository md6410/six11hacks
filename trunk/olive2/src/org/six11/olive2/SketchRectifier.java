package org.six11.olive2;

import java.awt.Graphics2D;
import java.util.Map;

/**
 * Instances of this class are responsible for turning an abstract
 * interpretation of a sketch into a format that can be unambiguously
 * encoded, such as for printing or displaying to the screen.
 **/
public abstract class SketchRectifier {
  public abstract void rectify(SketchInterpretation interp, Map params);
}
