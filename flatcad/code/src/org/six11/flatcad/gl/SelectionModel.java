package org.six11.flatcad.gl;

import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.geom.Line;
import org.six11.flatcad.geom.Box;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * A Selection model for determining what is selected in a 3D
 * model. Everything is stored in half-edges, but you can ask it
 * questions like 'is this face selected' or 'is this point' selected
 * and it will look at the half-edges to give you the answer.
 **/
public class SelectionModel {
  
  HalfEdge selection;
  List<ChangeListener> changeListeners;
  List<Vertex> miscPoints;
  
  public SelectionModel() {
    changeListeners = new ArrayList<ChangeListener>();
    miscPoints = new ArrayList<Vertex>();
  }

  public void addChangeListener(ChangeListener lis) {
    if (!changeListeners.contains(lis)) {
      changeListeners.add(lis);
    }
  }

  protected void fireChangeEvent() {
    ChangeEvent ev = new ChangeEvent(this);
    for (ChangeListener lis : changeListeners) {
      lis.stateChanged(ev);
    }
  }

  public void clearSelection() {
    setSelection(null);
  }

  public void setSelection(HalfEdge sel) {
    selection = sel;
    if (sel != null) {
      sel.getPoint().addVisit(sel);
      sel.getFace().addVisit(sel);
    }
    fireChangeEvent();
  }

  public List<Vertex> getMiscPoints() {
    return miscPoints;
  }

  public HalfEdge getSelection() {
    return selection;
  }

  public boolean hasSelection() {
    return selection != null;
  }

  public boolean isSelected(Vertex v) {
    return hasSelection() && v.equals(selection.getPoint());
  }

  public boolean isSelected(Polygon p) {
    return hasSelection() && (selection.getFace() == p);
  }

  public boolean isSelected(Line line) {
    return hasSelection() && 
      isSelected(line.getStart()) &&
      selection.getNext().equals(line.getEnd());
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testSelections() {
    SelectionModel sm = new SelectionModel();
    Box b = new Box();
    HalfEdge he = b.getFaces().get(0).getEdge();
    assertFalse(sm.hasSelection());
    sm.setSelection(he);
    assertTrue(sm.hasSelection());
    assertTrue(sm.isSelected(he.getFace()));
    assertTrue(sm.isSelected(he.getPoint()));
    sm.setSelection(he.getNext());
    assertTrue(sm.isSelected(he.getFace()));
    assertFalse(sm.isSelected(he.getPoint()));
    sm.clearSelection();
    assertFalse(sm.hasSelection());
  }

}
