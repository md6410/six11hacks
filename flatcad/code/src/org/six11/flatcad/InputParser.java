package org.six11.flatcad;

import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import org.six11.flatcad.model.TurtleModel;
import org.six11.flatcad.command.BeginCommand;
import org.six11.flatcad.command.BranchCursor;

/**
 * 
 **/
public class InputParser implements TextEntry.UserInputListener {

  Map<String, Command> commands;
  Output output;
  TurtleModel model;

  public InputParser() {
    commands = new HashMap<String, Command>();
  }

  public void setErrorOutput(Output output) {
    this.output = output;
  }

  public void addCommand(Command command) {
    for (String alias : command.getNames()) {
      associateCommand(alias, command);
    }
  }

  public Command getCommand(String name) {
    return commands.get(name);
  }

  public void setTurtleModel(TurtleModel tm) {
    this.model = tm;
  }

  public void handleUserInput(String input, boolean limited) {
    List<String> toks = tokens(input);
    if (toks.size() == 0) return;
    String cmd = toks.get(0);

    // parse to determine if we should indent/unindent, execute/stash
    if (cmd.equals("begin") && toks.size() > 1) {
      // make a new branch named cmd, which automatically attaches it
      // to the current cursor. Indent to the new branch.
      BranchCursor babyBranch = new BranchCursor(model, model.getCodeCursor(), toks);
      model.setCodeCursor(babyBranch);
    } else if (cmd.equals("end")) {
      // unindent one level. If we are ending a repeat block, that
      // block is a candidate to be executed. Otherwise, stash the
      // named block in the lookup table.
      BranchCursor ended = model.getCodeCursor();
      BranchCursor above = ended.getParent();
      model.setCodeCursor(above);
      Command execMe = ended.getExecutorCommand(output);
      toks.remove(0);
      if (model.getCodeCursor().isTop() && ended.getCallableName().equals("repeat")) {
	exec(execMe, toks);
      } else {
	if (model.getCodeCursor().isTop()) {
	  associateCommand(ended.getCallableName(), execMe);
	}
	stash(execMe, toks);
      }
    } else {
      // This was just a normal line of input. If we are currently at
      // the top level of interactive input, then execute it
      // immediately. Otherwise, stash it in the current branch of
      // input.
      if (model.getCodeCursor().isTop()) {
	if (cmd.equals("help")) {
	  doHelp(toks);
	} else {
	  toks.remove(0);
	  exec(getCommand(cmd), toks);
	}
      } else {
	toks.remove(0);
	stash(getCommand(cmd), toks);
      }
    }
  }

  public void doHelp(List<String> toks) {
    if (toks.size() == 2) {
      // full help on toks.get(1)
      if (commands.containsKey(toks.get(1))) {
	commands.get(toks.get(1)).getFullHelp();
      } else {
	output.addOutput("No help for " + toks.get(1));
      }
    } else {
      for (int i=1; i < toks.size(); i++) {
	if (commands.containsKey(toks.get(i))) {
	  commands.get(toks.get(i)).getSimpleHelp();
	  output.addOutput("\n");
	} else {
	  output.addOutput("No help for " + toks.get(i) + "\n");
	}
      }
    }
  }

  public void exec(Command cmd, List<String> toks) {
    if (cmd != null && toks != null) {
      cmd.execute(toks);
    } else {
      output.addOutput("Huh?");
    }
  } 

  public void stash(Command cmd, List<String> toks) {
    model.getCodeCursor().addCommand(cmd, toks);
  }

  public List<String> tokens(String input) {
    StringTokenizer tok = new StringTokenizer(input);
    List<String> ret = new ArrayList<String>();
    while (tok.hasMoreTokens()) {
      ret.add(tok.nextToken());
    }
    return ret;
  }

  private void associateCommand(String name, Command command) {
    commands.put(name, command);
  }

}

