package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.util.Debug;
import org.six11.flatcad.Output;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.model.Turtle;

/**
 * 
 **/
public class TurtlePitchCommand extends TurtleCommand {

  public TurtlePitchCommand(Output output, TurtleModel model, Component comp, String... names) {
    super(output, model, comp, names);
  }

  public void execute(List<String> args) {
    if (args.size() == 2) {
      try {
	float num = Float.parseFloat(args.get(1));
	if (args.get(0).equals("left")) {
	  num = -1f * num;
	} else if (args.get(0).equals("right")) {
	  // ok
	} else {
	  output.addOutput("dude, you must say left or right. I'll use 'right'");
	}
	model.addAction(new Turtle(0f, 0f, 0f, num, Turtle.PEN_USE_CURRENT));
	component.repaint();
      } catch (Exception ex) {
	output.addOutput("Got exception of type " + ex.getClass().getName());
      }
    }
  }

}
