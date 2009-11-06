package org.six11.olive2.rect;

import org.six11.olive2.SketchRectifier;
import org.six11.olive2.SketchInterpretation;
import org.six11.olive2.interp.BasicInterpretation;
import org.six11.util.Debug;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;

/**
 * 
 **/
public class BasicRectifier extends SketchRectifier {

  Stroke drawingStroke;

  public BasicRectifier() {
    drawingStroke = new BasicStroke(3f);    
  }

  public void rectify(SketchInterpretation si, Map params) {
    
  }

}
