package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.flatcad.gl.SelectionModel;
import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Namable;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import org.six11.util.Debug;
import java.util.Map;

/**
 * Name the point at the turtle.
 **/
public class NameCommand extends Command {
  
  protected SelectionModel sm;
  protected Map<String, Namable> nameMap;
  protected Component comp;

  public NameCommand(Output out, SelectionModel sm, Map<String, Namable> nameMap, 
		     Component comp, String... names) {
    super(out, names);
    this.sm = sm;
    this.nameMap = nameMap;
    this.comp = comp;
  }

  public String getSimpleHelpString() {
    return "Syntax: name myPoint";
  }

  public String getFullHelpString() {
    return getSimpleHelpString();
  }

  public void execute(List<String> args) {
    if (args.size() > 1 && args.get(0).equals("edge")) {
      nameEdge(args.get(1));
    } else if (args.size() > 1 && args.get(0).equals("face")) {
      nameFace(args.get(1));
    } else if (args.size() > 0) {
      nameVertex(args.get(0));
    } else {
      output.addOutput(getSimpleHelpString());
    }
    comp.repaint();
  }
  
  private void nameEdge(String nom) {
    HalfEdge e = sm.getSelection().getNext();    
    e.setVarName(nom);
    e.getPair().setVarName(nom);
  }

  private void nameFace(String nom) {
    Polygon face = sm.getSelection().getFace();
    face.setVarName(nom);
  }

  private void nameVertex(String nom) {
    Vertex v = sm.getSelection().getPoint();
    if (nameMap.containsKey(nom)) {
      output.addOutput("There is already a point with that name.");
    } else {
      // I really need a symbol lookup table.
      nameMap.put(nom, v);
      v.setVarName(nom);
      output.addOutput("OK, point " + v + " is now '" + nom + "'\n");
    }
  }
}
