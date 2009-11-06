package org.six11.flatcad.model;

import org.six11.flatcad.gl.GLDrawable;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class Generation implements GLDrawable {

  /**
   * The operation that created this generation.
   */
  protected Operation op;

  /**
   * The generation that immediately preceeds this one. This will be
   * non-null for all generations except for the root.
   */
  protected Generation parentGeneration;

  /**
   * The SceneGraph for this generation.
   */
  protected SceneGraph sceneGraph;

  /**
   * Generations (if any) that were created abased on this one.
   */
  protected List<Generation> childGenerations;

  /**
   * The camera that is currently (or was last) in use in this
   * generation.
   */
  protected Camera camera; // NOTE: this is a
			   // org.six11.flatcad.model.Camera, not from
			   // the old six11/gl pacakge!

  /**
   * Make a new Generation. This establishes a parent/child link
   * between the parent and this new generation.
   */
  public Generation(Operation op, Generation parentGeneration) {
    this.op = op;
    this.parentGeneration = parentGeneration;
    this.childGenerations = new ArrayList<Generation>();
    this.sceneGraph = new SceneGraph();
    if (parentGeneration != null) {
      parentGeneration.childGenerations.add(this);
    }
  }

  public void drawGL() {
    // This should do fancy things like setting up the camera, making
    // sure any current selections or other UI drawing conditions are
    // covered.

    // TODO: this is just for debugging at the moment.
    sceneGraph.drawGL();
  }
}
