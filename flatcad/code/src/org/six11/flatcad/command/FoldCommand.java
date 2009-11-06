package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.flatcad.gl.SelectionModel;
import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Namable;
import org.six11.flatcad.geom.LineSegment;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import org.six11.flatcad.gl.OpenGLDisplay;
import org.six11.util.Debug;
import java.util.Map;

/**
 * Fold the structure between two named points.
 **/
public class FoldCommand extends Command {
  
  protected SelectionModel sm;
  protected Map<String, Namable> nameMap;
  protected OpenGLDisplay display;

  public FoldCommand(Output out, SelectionModel sm, Map<String, Namable> nameMap, 
		     OpenGLDisplay display, String... names) {
    super(out, names);
    this.sm = sm;
    this.nameMap = nameMap;
    this.display = display;
  }

  public String getSimpleHelpString() {
    return "Syntax: name myPoint";
  }

  public String getFullHelpString() {
    return getSimpleHelpString();
  }

  public void execute(List<String> args) {

    // TODO: I am explicitly casting Namable to Vertex, which works
    // only because I have only implemented Vertexes
    if (args.size() >= 3 && args.get(1).equals("to")) {
      if (!nameMap.containsKey(args.get(0))) {
	output.addOutput("Point '" + args.get(0) + "' not found. Use 'name' to name points.");
	return;
      }
      if (!nameMap.containsKey(args.get(2))) {
	output.addOutput("Point '" + args.get(2) + "' not found. Use 'name' to name points.");
	return;
      }
      
      double amt = 180d;
      if (args.size() > 3) {
	try { 
	  amt = Double.parseDouble(args.get(3));
	} catch (Exception ignore) { }
      }
      Vertex a = (Vertex) nameMap.get(args.get(0));
      Vertex b = (Vertex) nameMap.get(args.get(2));
      //      display.applyFold(new LineSegment(a,b), amt, OpenGLDisplay.FOLD_STYLE_MIDDLE);

    } else {
      output.addOutput(getSimpleHelpString());
    }
  }
}
