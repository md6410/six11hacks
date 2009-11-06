package org.six11.flatcad;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.six11.util.gui.Placeholder;
import java.awt.Dimension;
import org.six11.util.layout.FrontEnd;
import org.six11.util.layout.FrontEnd.Anchor;

/**
 * 
 **/
public class CreateCommand extends Command {

  Main main;

  static final List<String> absoluteTerms = new ArrayList<String>();
  static {
    absoluteTerms.add("top");
    absoluteTerms.add("bottom");
    absoluteTerms.add("left");
    absoluteTerms.add("right");
    absoluteTerms.add("topleft");
    absoluteTerms.add("topright");
    absoluteTerms.add("bottomleft");
    absoluteTerms.add("bottomright");
  }

  static final List<String> relativeTerms = new ArrayList<String>();
  static {
    relativeTerms.add("north");
    relativeTerms.add("northwest");
    relativeTerms.add("northeast");
    relativeTerms.add("south");
    relativeTerms.add("southwest");
    relativeTerms.add("southeast");
    relativeTerms.add("west");
    relativeTerms.add("east");
  }

  static final Map<String, String> opposites = new HashMap<String, String>();
  static {
    opposites.put("north", "south");
    opposites.put("south", "north");
    opposites.put("east", "west");
    opposites.put("west", "east");
    opposites.put("northeast", "southwest");
    opposites.put("northwest", "southeast");
    opposites.put("southeast", "northwest");
    opposites.put("southwest", "northeast");
  }

  /**
   * Convert a string that contains "north", "south", "top", "bottom"
   * to N, S, N, S, or null if it doesn't contain one of those.
   */
  static Anchor getVertical(String in) {
    Anchor ret = null;
    if (in.contains("north") || in.contains("top")) {
      ret = FrontEnd.N;
    } else if (in.contains("south") || in.contains("bottom")) {
      ret = FrontEnd.S;
    }
    return ret;
  }

  /**
   * Convert a string that contains "east", "west", "right", "left"
   * to E, W, E, W, or null if it doesn't contain one of those.
   */
  static Anchor getHorizontal(String in) {
    Anchor ret = null;
    if (in.contains("east") || in.contains("right")) {
      ret = FrontEnd.E;
    } else if (in.contains("west") || in.contains("left")) {
      ret = FrontEnd.W;
    }
    return ret;
  }

  public CreateCommand(Output out, Main main, String... names) {
    super(out, names);
    this.main = main;
  }

  public String getSimpleHelpString() {
    return "Creates new objects and places them somewhere.";
  }

  public String getFullHelpString() {
    return 
      "Creates new objects and places them somewhere. " +
      "Specifically you can place it above, below, " +
      "left, right of some other object(s). You can place " +
      "things absolutely or relatively. Here is the " +
      "syntax.\n\n" +
      "add thingA at topleft\n" +
      "add thingB at bottomright\n" +
      "add middleThing southeast of thingA and northwest of thingB";
  }

  public void execute(List<String> args) {
    try {
      String name = args.remove(0); // get name and shift arguments
      if (args.get(0).equals("at") && absoluteTerms.contains(args.get(1))) {
	args.remove(0);
	executeAt(name, args);
      } else {
	executeRelative(name, args);
      }
      
    } catch (Exception ex) {
      output.addOutput("Could not add object: " + ex.getClass().getName());
      ex.printStackTrace();
    }
  }

  private void executeAt(String name, List<String> args) {
//     Anchor vert = getVertical(args.get(0));
//     Anchor horz = getHorizontal(args.get(0));

//     Placeholder block = new Placeholder(new Dimension(100, 100), name);
//     main.getDisplay().add(block, name);
//     if (vert == null) {
//       // make it span the entire north/south
//       main.getDisplay().addRule(FrontEnd.ROOT, FrontEnd.N, name, FrontEnd.N);
//       main.getDisplay().addRule(FrontEnd.ROOT, FrontEnd.S, name, FrontEnd.S);
//     } else {
//       main.getDisplay().addRule(FrontEnd.ROOT, vert, name, vert);
//     }

//     if (horz == null) {
//       // make it span the entire east/west
//       main.getDisplay().addRule(FrontEnd.ROOT, FrontEnd.W, name, FrontEnd.W);
//       main.getDisplay().addRule(FrontEnd.ROOT, FrontEnd.E, name, FrontEnd.E);
//     } else {
//       main.getDisplay().addRule(FrontEnd.ROOT, horz, name, horz);
//     }
  }

  private void executeRelative(String name, List<String> args) {
    // args are in the format
    // <clause> [and <clause>]*
    // where <clause> is <relativeTerm> <parentName>
//     Placeholder block = new Placeholder(new Dimension(100, 100), name);
//     main.getDisplay().add(block, name);

//     if (args.size() >= 2) {
//       do {
// 	if (args.get(0).equals("and")) {
// 	  args.remove(0);
// 	}
// 	Linkage linkage = getRelative(name, args);
// 	if (linkage.parentHAnchor != null) {
// 	  main.getDisplay().addRule(linkage.parentName, linkage.parentHAnchor,
// 				    linkage.childName, linkage.childHAnchor);
// 	}
// 	if (linkage.parentVAnchor != null) {
// 	  main.getDisplay().addRule(linkage.parentName, linkage.parentVAnchor,
// 				    linkage.childName, linkage.childVAnchor);
// 	}
//       } while (args.size() > 2 && args.get(0).equals("and"));
//     }
  }

  private Linkage getRelative(String name, List<String> args) {
    // contract is that when this returns, args has been trimmed so
    // that args[0] is the next linkage
    Linkage ret = new Linkage();
    String rel = args.remove(0);
    String opp = opposites.get(rel);
    if (opp == null) {
      System.out.println("Oddly, there is no opposite for " + rel);
    }
    if (args.get(0).equals("of")) {
      args.remove(0);
    }
    ret.parentName = args.remove(0);
    ret.parentHAnchor = getHorizontal(rel);
    ret.childHAnchor = getHorizontal(opp);
    ret.parentVAnchor = getVertical(rel);
    ret.childVAnchor = getVertical(opp);
    ret.childName = name;
    System.out.println(ret.toString());
    return ret;
  }
  
  class Linkage {
    String parentName;
    Anchor parentHAnchor;
    Anchor parentVAnchor;
    String childName;
    Anchor childHAnchor;
    Anchor childVAnchor;

    public String toString() {
      return "[Linkage: " + 
	parentName + "." + parentHAnchor + " -> " + 
	childName + "." + childHAnchor + ", " +
	parentName + "." + parentVAnchor + " -> " + 
	childName + "." + childVAnchor + "]";	
    }
  }
}
