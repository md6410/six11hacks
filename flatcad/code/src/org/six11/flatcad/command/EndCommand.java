package org.six11.flatcad.command;

import java.awt.Component;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import java.util.List;
import java.util.ArrayList;
import org.six11.flatcad.model.TurtleModel;
import org.six11.util.Debug;

/**
 * 
 **/
public class EndCommand extends Command {

  protected TurtleModel model;

  public EndCommand(Output output, TurtleModel model, String... names) {
    super(output, names);
    this.model = model;
  }

  public String getSimpleHelpString() {
    return "end";
  }
  
  public String getFullHelpString() {
    return 
      "End the most recently established 'begin' block.";
  }

  public void execute(List<String> args) {
    model.setCodeCursor(model.getCodeCursor().getParent());
    Debug.out("BeginCommand", "Ended old branch. Now in branch: " + model.getCodeCursor());    
  }


}
