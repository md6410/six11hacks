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
public class CommentCommand extends Command {

  public CommentCommand(Output output, String... names) {
    super(output, names);
  }

  public void execute(List<String> args) {

  }

  public String getSimpleHelpString() {
    return "lets you write a comment that extends to the end of the line";
  }
  
  public String getFullHelpString() {
    return getSimpleHelpString() + "\n\nFor example the following are comments:\n" +
      "--- this is a comment\n" +
      "# this is a comment\n" +
      "; this is a comment";
  }

}
