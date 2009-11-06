package org.six11.flatcad.command;

import java.awt.Component;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import java.util.List;
import java.util.ArrayList;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.InputParser;
import org.six11.util.Debug;

/**
 * 
 **/
public class BeginCommand extends Command {

  protected TurtleModel model;
  protected Component component;
  protected InputParser inputParser;

  public BeginCommand(Output output, TurtleModel model, 
		      InputParser inputParser, String... names) {
    super(output, names);
    this.model = model;
    this.inputParser = inputParser;
  }

  public String getSimpleHelpString() {
    return "begin (name) (params...)";
  }
  
  public String getFullHelpString() {
    return 
      "Begin a named block, possibly with a name or parameters. " +
      "This can be used to create logical groups of statements. " +
      "For example if you want to make a bunch of parts within the " +
      "same file you can separate them by putting each between " +
      "begin/end statements.\n\n" +

      "Example:\n\n" +

      "begin square :size\n" +
      "f :size\n" +
      "r 90\n" +
      "f :size\n" +
      "r 90\n" +
      "f :size\n" +
      "r 90\n" +
      "f :size\n" +
      "r 90\n" +
      "end square\n\n" +

      "This example shows special block definitions:\n\n" +

      "begin part triangle :size\n" +
      "f :size\n" +
      "r 120\n" +
      "f :size\n" +
      "r 120\n" +
      "f :size\n" +
      "r 120\n" +
      "end part\n\n" +
      
      "A 'part' is a special block that defines a single cutout. " +
      "In this case FlatCAD understands the part to be named " +
      "'triangle'.\n";
  }

  public void execute(List<String> args) {
    BranchCursor babyBranch = new BranchCursor(model, model.getCodeCursor(), args);
  }

  public static boolean isReserved(List<String> args) {
    boolean ret = false;
    if (args.size() == 0) ret = true;
    else if (args.get(0).equals("part") && args.size() > 1) ret = false; // name is args.get(1)
    else if (args.get(0).equals("repeat")) ret = true;
    return ret;
  }

  public static String getUserSuppliedName(List<String> args) {
    String ret = null;
    if (args.size() > 0) {
      if (args.get(0).equals("part") && args.size() > 1) ret = args.get(1);
      else ret = args.get(0);
    }
    return ret;
  }


}
