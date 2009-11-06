package org.six11.flatcad.command;

import java.awt.Component;
import java.util.List;
import org.six11.util.Debug;
import org.six11.flatcad.Output;
import org.six11.flatcad.Command;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.model.Turtle;
import org.six11.flatcad.hpgl.HPGL;

/**
 * 
 **/
public class PrintCommand extends Command {

  protected TurtleModel model;

  public PrintCommand(Output output, TurtleModel model, String... names) {
    super(output, names);
    this.model = model;
  }


  public void execute(List<String> args) {
    if (args.size() != 1) return;
    
    HPGL h = HPGL.getInstance();
    if (args.get(0).equals("stop")) {
      // stop the existing print job
      h.stop();
      output.addOutput("Wrote " + h.getFileName());
    } else {
      String base = args.get(0);
      if (!base.endsWith(".hpgl")) {
	base += ".hpgl";
      }
      h.setFileName(base);
      h.start();
      output.addOutput("Printing to " + h.getFileName());
    }
  }

  public String getSimpleHelpString() {
    return "print [stop | fileName]";
  }

  public String getFullHelpString() {
    return "turn printing on or off. Ex:\n\n" +
      "print triangle\n" +
      "f 1\n" +
      "r 120\n" +
      "f 1\n" +
      "r 120\n" +
      "f 1\n" +
      "print stop\n";
  }

}
