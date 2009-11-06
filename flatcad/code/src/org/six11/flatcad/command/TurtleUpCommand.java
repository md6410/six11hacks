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
public class TurtleUpCommand extends TurtleCommand {

  public TurtleUpCommand(Output output, TurtleModel model, Component comp, String... names) {
    super(output, model, comp, names);
  }

  public void execute(List<String> args) {
    try {
      Turtle t = new Turtle(0f, 0f, 0f, 0f, Turtle.PEN_UP);
      model.addAction(t);
      component.repaint();
    } catch (Exception ex) {
      output.addOutput("Got exception of type " + ex.getClass().getName());
    }
  }

}
