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
public class NotchCommand extends Command {

  protected TurtleModel model;
  protected Component comp;

  public NotchCommand(Output output, TurtleModel model, Component comp, String... names) {
    super(output, names);
    this.comp = comp;
    this.model = model;
  }

  public void execute(List<String> args) {
    if (args.size() == 2) {
      
      // get the direction and set the 'in' and 'out' directions
      float in = 90f; // you should really be specific about notch dir
      float out = -90f;
      if (args.get(0).equalsIgnoreCase("right")) {
	in = -90f;
	out = 90f;
      } else if (args.get(0).equalsIgnoreCase("left")) {
	in = 90f;
	out = -90f;
      }

      // get the notch depth
      float depth = 0.5f;
      try {
	depth = Float.parseFloat(args.get(1));
      } catch (Exception ex) {
	output.addOutput("Got exception of type " + ex.getClass().getName() + " while trying to parse " + args.get(1));
      }

      // ok, to make a notch, back up 1/2 the notch width (the
      // material thickness), turn in, move forward the notch depth
      // distance, turn out, move forward the notch width, turn out
      // again, forward the notch depth, and in so that you're facing
      // the original direction, and draw a line backward 1/2 the
      // notch width so you are in the spot you started.
      //
      // Obviously this should just be a flatlang routine, but today I
      // want to have something printed on the laser cutter.
      float thick = model.getMatThickness();
      float halfThick = -0.5f * thick;
      
      model.addAction(new Turtle(halfThick, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(0f, in, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(depth, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(0f, out, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(thick, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(0f, out, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(depth, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(0f, in, 0f, 0f, Turtle.PEN_USE_CURRENT));
      model.addAction(new Turtle(halfThick, 0f, 0f, 0f, Turtle.PEN_USE_CURRENT));

      comp.repaint();
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
