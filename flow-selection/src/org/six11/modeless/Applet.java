package org.six11.modeless;

import java.awt.BorderLayout;

public class Applet extends java.applet.Applet {
  public void init() {
    setBackground(java.awt.Color.red);
    ScratchPad scratch = Test.makeFlowSelectionComponent();
    scratch.setBackground(java.awt.Color.blue);
    setLayout(new BorderLayout());
    add(scratch, BorderLayout.CENTER);
    System.out.println("Added Flow Selection applet...");
  }
}
