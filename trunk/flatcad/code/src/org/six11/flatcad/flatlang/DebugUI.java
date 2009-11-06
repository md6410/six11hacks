package org.six11.flatcad.flatlang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.tree.*;
import org.six11.util.gui.ApplicationFrame;
import org.six11.flatcad.TextEntry;
import java.awt.BorderLayout;
import org.six11.util.Debug;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * 
 **/
public class DebugUI implements ActionListener, DebugMonitor.DebugMonitorListener {

  ApplicationFrame debugFrame;
  FlatInterpreter interpreter;
  TextEntry textEntry;

  JTree tree;
  Map<String, JLabel> labels;

  public DebugUI(FlatInterpreter interpreter, TextEntry textEntry) {
    debugFrame = new ApplicationFrame("FlatLang Debugger", 600, 360);
    debugFrame.setNoQuitOnClose();
    this.interpreter = interpreter;
    this.textEntry = textEntry;
    interpreter.getDebugMonitor().addListener(this);
    tree = new JTree();
    JScrollPane scroller = new JScrollPane(tree);
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel buttons = makeButtons();
    JPanel messages = makeMessages();
    topPanel.add(buttons, BorderLayout.NORTH);
    topPanel.add(scroller, BorderLayout.CENTER);
    topPanel.add(messages, BorderLayout.SOUTH);
    debugFrame.add(topPanel);
    updateModel();
    debugFrame.setVisible(true);
  }

  public void setCurrentDebugInfo(String code, int lineNum, 
				  int charNum, Register retVal, boolean ended) {
    if (ended) {
      Debug.out("DebugUI", "Aborted debugging.");
      labels.get("Code").setText("Aborted debug process. Press 'Run' to do it again.");
      labels.get("Line").setText("");
      labels.get("Char").setText("");
      labels.get("RetVal").setText("");
    } else {
      Debug.out("DebugUI", code + " (line " + lineNum + ":" + charNum + ")");
      labels.get("Code").setText(code);
      labels.get("Line").setText("Line Number: " + lineNum);
      labels.get("Char").setText("Char Number: " + charNum);
      labels.get("RetVal").setText("Most recent expression value: " + retVal +
				   " (" + Register.typeStr(retVal) + ")");
    }
    updateModel();
  }

  private JPanel makeMessages() {
    JPanel ret = new JPanel(new GridLayout(0, 1));
    labels = new HashMap<String, JLabel>();
    labels.put("Code", new JLabel("Code: (none debugged yet)"));
    labels.put("Line", new JLabel("Line: (none debugged yet)"));
    labels.put("Char", new JLabel("Char: (none debugged yet)"));
    labels.put("RetVal", new JLabel("Most recent expression value: (none debugged yet)"));
    ret.add(labels.get("Code"));
    ret.add(labels.get("Line"));
    ret.add(labels.get("Char"));
    ret.add(labels.get("RetVal"));
    return ret;
  }

  private JButton makeButton(String name) {
    JButton ret = new JButton(name);
    ret.addActionListener(this);
    return ret;
  }

  public JPanel makeButtons() {
    JPanel ret = new JPanel();
    ret.add(makeButton("Run"));
    ret.add(makeButton("Set Stop Point"));
    ret.add(makeButton("Next Statement"));
    ret.add(makeButton("Next Expression"));
    ret.add(makeButton("Abort"));
    return ret;
  }

  public void actionPerformed(ActionEvent ev) {
    String s = ev.getActionCommand();
    if (s.equals("Run")) {
      final int lineNum = interpreter.getDebugMonitor().getLineNumStop();
      Runnable r = new Runnable() {
	  public void run() {
	    textEntry.interpret();
	  }
	};
      interpreter.getDebugMonitor().abort();
      interpreter.getDebugMonitor().setLineNumStop(lineNum);
      new Thread(r).start();
    }
    if (s.equals("Set Stop Point")) {
      Debug.out("DebugUI", "Stop point on line " + textEntry.getLineNumber());
      interpreter.getDebugMonitor().setLineNumStop(textEntry.getLineNumber());
    }
    if (s.equals("Next Statement")) {
      Debug.out("DebugUI", "next statement...");
      interpreter.getDebugMonitor().allowStatement();
    }
    if (s.equals("Next Expression")) {
      Debug.out("DebugUI", "next expression...");
      interpreter.getDebugMonitor().allowExpression();
    }
    if (s.equals("Abort")) {
      Debug.out("DebugUI", "Abort signalled.");
      interpreter.getDebugMonitor().abort();
    }
  }

  public void updateModel() {
    tree.setModel(new DefaultTreeModel(makeModel()));
  }

  MachineState m() {
    return interpreter.getMachine();
  }

  public void go() {
    updateModel();
    debugFrame.setVisible(true);
    debugFrame.toFront();
  }

  class N extends DefaultMutableTreeNode {
    N(String s) { super(s); }
    N(String s, Register reggie) { super(reggie); }
  }

  DefaultMutableTreeNode makeModel() {
    DefaultMutableTreeNode root = new DebugTreeModel(interpreter);

    Stack<Map<Object, Register>> frames = m().getVariableStack();
    Debug.out("DebugUI", "adding " + frames.size() + " frames to the list.");
    for(Map<Object, Register> frame : frames) {
      root.add(makeFrameNode(frame));
    }

    return root;
  }

  class NamedReg {
    Object name;
    Register reg;
    NamedReg(Object name, Register reg) {
      this.name = name;
      this.reg = reg;
    }
    public String toString() {
      return name + " = " + reg;
    }
  }
  
  DefaultMutableTreeNode makeFrameNode(Map<Object, Register> frame) {
    DefaultMutableTreeNode ret = new DefaultMutableTreeNode("Context");
    for (Object key : frame.keySet()) {
      ret.add(new DefaultMutableTreeNode(new NamedReg(key, frame.get(key))));
    }
    return ret;
  }
}
