package org.six11.flatcad.command;

import java.util.List;
import java.util.ArrayList;
import org.six11.flatcad.command.BranchCursor;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.Command;
import org.six11.flatcad.Output;
import org.six11.util.Debug;

/**
 * A branch or block of execution delimited by begin/end pairs (or in
 * the case of the top level program, the beginning and ending of the
 * file).
 **/
public class BranchCursor {
  protected List<CommandInstance> commands = new ArrayList<CommandInstance>();
  protected BranchCursor parent = null; // isTop == true
  protected TurtleModel turtleModel;
  protected List<String> beginStatementText;
  protected String callableName; // the name of this block, which is
				 // probably but not necessarily the
				 // first string after 'begin'

  public BranchCursor(TurtleModel turtleModel, BranchCursor parent, List<String> beginStatementText) {
    this.turtleModel = turtleModel;
    this.parent = parent;
    this.beginStatementText = beginStatementText;
    if (beginStatementText.size() == 0) {
      callableName = "[[top-level execution]]";
    } else if (beginStatementText.get(1).equals("part") && beginStatementText.size() > 2) {
      callableName = beginStatementText.get(2);
    } else {
      callableName = beginStatementText.get(1);
    }
  }

  public Command getExecutorCommand(Output output) {
    return new AnonymousCommand(output) {
	public void execute(List<String> args) {
	  int repeats = getNumRepeats();
	  for (int i=0; i < repeats; i++) {
	    for (CommandInstance ci : commands) {
	      ci.cmd.execute(ci.args);
	    }
	  }
	}
      };
  }

  public String getCallableName() {
    return callableName;
  }

  public BranchCursor getParent() {
    return parent;
  }

  public boolean isTop() {
    return parent == null;
  }

  public void addCommand(Command cmd, List<String> arguments) {
    commands.add(new CommandInstance(cmd, arguments));
  }

  public int getNumRepeats() {
    int ret = 1;
    if (beginStatementText.size() == 3 &&
	callableName.equals("repeat")) {
      try {
	String val = beginStatementText.get(2);
	if (TurtleModel.isVariable(val)) {
	  val = turtleModel.getVariableValue(val);
	}
	ret = Integer.parseInt(val);
      } catch (Exception ignore) {
	Debug.out("BranchCursor", "Not a number literal or variable: " + 
		  beginStatementText.get(2));
      }
    }
    return ret;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    if (isTop()) {
      buf.append("[Top-level execution]");
    } else if (beginStatementText.size() == 0) {
      buf.append("[anonymous block]");
    } else {
      for (String s : beginStatementText) {
	buf.append(s + " ");
      }
    }
    return buf.toString();
  }

  private static class CommandInstance {
    Command cmd;
    List<String> args;
    
    CommandInstance(Command cmd, List<String> args) {
      this.cmd = cmd;
      this.args = args;
    }
  }
  
}
