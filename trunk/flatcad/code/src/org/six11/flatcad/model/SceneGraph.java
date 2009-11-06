package org.six11.flatcad.model;

import static org.lwjgl.opengl.GL11.*;
import org.apache.commons.math.linear.RealMatrix;
import org.six11.flatcad.gl.GLDrawable;
import org.six11.flatcad.gl.SelectionModel;
import org.six11.flatcad.geom.Box;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.MathUtils;
import org.six11.flatcad.geom.MatrixStack;
import org.six11.util.Debug;
import org.six11.util.adt.AdjacencyTable;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class SceneGraph implements GLDrawable {

  /**
   * List of Pieces that are top-level.
   */
  protected List<Piece> tops;

  /**
   * Adjacency table relating pieceA -> pieceB, transformMatrix
   */
  protected AdjacencyTable<Piece, RealMatrix> adjacency;

  public SceneGraph() {
    tops = new ArrayList<Piece>();
    adjacency = new AdjacencyTable<Piece, RealMatrix>();
  }

  public void drawGL() {
    // TODO
    Debug.out("SceneGraph", "Drawing scenegraph, really!");

//     RealMatrix translate = MathUtils.getTranslationMatrix(new Vertex(1.0, 0.0, 0.0));
//     MatrixStack ms = new MatrixStack();
//     //     ms.push(translate);
//     //     glMultMatrix(ms.getCurrentDoubleBuffer());
//     butthead.drawGL();
//     RealMatrix rm = translate.inverse();
//     ms.push(rm);
//     glMultMatrix(ms.getCurrentDoubleBuffer());
//     beevis.drawGL();
  }

  public AdjacencyTable<Piece, RealMatrix> getAdjacencyTable() {
    return adjacency;
  }

  /**
   * Return a complete copy of this scenegraph. The returned
   * SceneGraph has the same Piece objects, but the transformations
   * are copies.
   */
  public SceneGraph deepCopy() {
    new RuntimeException("where is this being called from?").printStackTrace();
//     SceneGraph copy = new SceneGraph();
//     copy.tops.addAll(tops);
//     copy.adjacency = adjacency.copy();
//     for (AdjacencyTable.Entry<Piece, RealMatrix> entry : copy.adjacency.getEntries()) {
//       entry.val = entry.val.copy();
//     }
    return null;
  }

}
