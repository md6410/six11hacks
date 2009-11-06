package org.six11.flatcad.command;

import java.awt.Component;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import java.util.List;
import org.six11.flatcad.model.TurtleModel;

/**
 * 
 **/
public class TurtleCommand extends Command {

  protected TurtleModel model;
  protected Component component;

  public TurtleCommand(Output output, TurtleModel model, Component comp, String... names) {
    super(output, names);
    this.model = model;
    this.component = comp;
  }

  public String getSimpleHelpString() {
    return "f n  OR  b n  OR  up  OR  down  OR  turn d";
  }
  
  public String getFullHelpString() {
    return 
      "Move forward or backward with f n or b n, where n is an integer.\n" +
      "Toggle the pen by using 'up' or 'down'.\n"+
      "Turn the turtle with 'turn d' where d is a number in degrees. Left is positive, right is negative.";
  }

  public void execute(List<String> args) {
    
  }


}
