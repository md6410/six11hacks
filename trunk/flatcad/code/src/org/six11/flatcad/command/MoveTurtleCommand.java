package org.six11.flatcad.command;

import java.util.List;
import java.util.Map;
import org.six11.flatcad.gl.SelectionModel;
import org.six11.flatcad.geom.HalfEdge;
import org.six11.flatcad.geom.Namable;
import org.six11.flatcad.geom.Visitable;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import org.six11.util.Debug;

/**
 * Move the turtle forward or backwards
 **/
public class MoveTurtleCommand extends Command {
  
  protected SelectionModel sm;
  protected Map<String, Namable> nameMap;  

  public MoveTurtleCommand(Output out, SelectionModel sm, Map<String, Namable> nameMap, String... names) {
    super(out, names);
    this.sm = sm;
    this.nameMap = nameMap;
  }

  public String getSimpleHelpString() {
    return "Move the turtle. Syntax: move [forward | backward | adjacent ]";
  }

  public String getFullHelpString() {
    return "Move the turtle. Syntax: move [forward | backward | adjacent ]\n" +
      "You may shorten those to [fwd | f | back | b ]. Also 'pair' works\n" +
      "but has specific meaning to the data structure and may confuse you.";
  }

  public void execute(List<String> args) {
    if (sm.hasSelection() && (args.size() > 0)) {
      if (args.get(0).equals("forward") ||
	  args.get(0).equals("fwd") ||
	  args.get(0).equals("f")) {
	moveForward();
      } else if (args.get(0).equals("backward") ||
		 args.get(0).equals("back") ||
		 args.get(0).equals("b")) {
	moveBackward();
      } else if (args.get(0).equals("adjacent")) {
	moveAdjacent();
      } else if (args.get(0).equals("to") && args.size() > 1) {
	moveTo(args.get(1));
      } else if (args.get(0).equals("pair")) {
	movePair();
      } else {
	output.addOutput("nicht verstehen! use 'help move' for commands");
      }
    }
  }

  private void moveTo(String where) {
    if (nameMap.containsKey(where)) {
      Namable named = nameMap.get(where);
      if (named instanceof Visitable) {
	Visitable vis = (Visitable) named;
	if (vis.getLastVisitor() == null) {
	  output.addOutput("It looks like I've never been there before.");
	} else {
	  sm.setSelection(vis.getLastVisitor());
	  output.addOutput("OK, set selection to the last visitor of '" + where + "'.");
	}
      } else {
	output.addOutput("Very odd. That location is not visitable!");
      }
    } else {
      output.addOutput("Can't find location '" + where + "'");
    }
  }

  private void moveForward() {
    sm.setSelection(sm.getSelection().getNext());
  }

  private void moveBackward() {
    sm.setSelection(sm.getSelection().getPreviousEdge());	
  }

  private void movePair() {
    sm.setSelection(sm.getSelection().getPair());
  }

  private void moveAdjacent() {
    HalfEdge he = sm.getSelection();
    he = he.getNext();
    he = he.getPair();
    he = he.getPreviousEdge();
    sm.setSelection(he);
  }
}
