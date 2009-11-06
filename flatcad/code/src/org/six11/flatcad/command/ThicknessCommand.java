package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.util.Debug;
import org.six11.flatcad.Output;
import org.six11.flatcad.Command;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.model.Turtle;

/**
 * 
 **/
public class ThicknessCommand extends Command {

  protected TurtleModel model;

  public ThicknessCommand(Output output, TurtleModel model, String... names) {
    super(output, names);
    this.model = model;
  }

  public void execute(List<String> args) {
    if (args.size() == 1) {
      try {
	float thick = Float.parseFloat(args.get(0));
	model.setMatThickness(thick);
	output.addOutput("OK, material thickness is now " + thick);
      } catch (Exception ex) {
	output.addOutput("Got exception of type " + ex.getClass().getName() + " while trying to parse " + args.get(0));
      }
    }

  }

  public String getSimpleHelpString() {
    return "notch [left|right] [depth]";
  }
  
  public String getFullHelpString() {
    return 
      "For example, \"notch right .3\" will make a notch to the right that is .3 units deep.";
  }

}
