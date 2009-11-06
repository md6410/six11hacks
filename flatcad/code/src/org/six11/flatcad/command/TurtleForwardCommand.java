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
public class TurtleForwardCommand extends TurtleCommand {

  public TurtleForwardCommand(Output output, TurtleModel model, Component comp, String... names) {
    super(output, model, comp, names);
  }

  public void execute(List<String> args) {
    if (args.size() == 1) {
      try {
	float num = Float.parseFloat(args.get(0));
	model.addAction(new Turtle(num, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));
	component.repaint();
      } catch (Exception ex) {
	output.addOutput("Got exception of type " + ex.getClass().getName());
      }
    }
  }

}
