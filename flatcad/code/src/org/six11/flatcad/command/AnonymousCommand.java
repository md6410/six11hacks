package org.six11.flatcad.command;

import org.six11.flatcad.Command;
import org.six11.flatcad.Output;

/**
 * 
 **/
public abstract class AnonymousCommand extends Command {

  public AnonymousCommand(Output output) {
    super(output, "");
  }

  public String getSimpleHelpString() { return ""; }

  public String getFullHelpString() { return ""; }

}
