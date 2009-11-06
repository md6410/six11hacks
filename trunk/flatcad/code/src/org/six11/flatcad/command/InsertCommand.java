package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.flatcad.gl.SelectionModel;
import org.six11.flatcad.geom.LineSegment;
import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Polygon;
import org.six11.flatcad.geom.Vertex;
import org.six11.flatcad.geom.Namable;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import org.six11.util.Debug;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Insert a point somewhere along the currently visually selected
 * edge. Remember that if 'he' is the current selection, then the
 * visually selected edge is he.getNext().
 **/
public class InsertCommand extends Command {
  
  protected SelectionModel sm;
  protected Map<String, Namable> nameMap;
  protected Component comp;
  protected Pattern percentPattern = Pattern.compile("(\\d\\d?)%?");

  public InsertCommand(Output out, SelectionModel sm, Map<String, Namable> nameMap, 
		     Component comp, String... names) {
    super(out, names);
    this.sm = sm;
    this.nameMap = nameMap;
    this.comp = comp;
  }

  public String getSimpleHelpString() {
    return "Syntax: insert myPoint -OR- insert myPoint at 15%";
  }

  public String getFullHelpString() {
    return getSimpleHelpString();
  }

  public void execute(List<String> args) {
    //    output.addOutput("You have " + args.size() + " args.\n");
    int perc;
    if ((args.size() > 1 && args.get(0).equals("at") && (perc = getPercent(args.get(1))) >= 0) ||
	((args.size() > 2 && args.get(1).equals("at") && (perc = getPercent(args.get(2))) >= 0))) { 
      output.addOutput("Adding point " + perc + "% of the way along this line.");
      HalfEdge he = sm.getSelection();
      Vertex a = he.getPoint();
      Vertex b = he.getNext().getPoint();
      LineSegment ls = new LineSegment(a,b);
      Vertex addme = ls.getParameterizedVertex(((float) perc) / 100f);
      // Rather than doing this:
      //   he.getNext().insert(addme);
      // I will tell my model that there is this extra point. If I did
      // the insert thing, it would screw up the polygon Euler
      // function.
      sm.getMiscPoints().add(addme);

      // also if there was a name (args.size() is 3) name it
      if (args.size() == 3) {
	String name = args.get(0);
	nameMap.put(name, addme);
	addme.setVarName(name);
      }
    } else {
      output.addOutput(getSimpleHelpString());
    }
    comp.repaint();
  }
  
  private int getPercent(String n) {
    int ret = -1;
    Matcher matcher = percentPattern.matcher(n);
    if (matcher.matches()) {
      try {
	ret = Integer.parseInt(matcher.group(1));
      } catch (Exception ignore) { }
    }
    return ret;
  }

}
