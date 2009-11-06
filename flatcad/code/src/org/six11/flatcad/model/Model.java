package org.six11.flatcad.model;

//import org.six11.flatcad.geom.Pyramid;
import org.six11.util.Debug;

/**
 * Holds all of the model data. You should be able to serialize this
 * object and give it to your friends and enemies and they should be
 * able to see the same thing you were working on, down to the camera
 * positions.
 **/
public class Model {
  
  /**
   * The name of the file this was last saved to or loaded from.
   */
  protected String fileName;
  
  // Pyramid tmpPyramid = new Pyramid();

  public Model() {
    InitOperation init = new InitOperation();
  }

  public void draw() {
    // tmpPyramid.drawGL();
    
  }
  
  public void saveAs(String fileName) {
    // TODO
  }

  public void save() {
    // TODO
  }

  public static Model load(String fileName) {
    // TODO - will need an alternate constructor
    return null;
  }

  
}
