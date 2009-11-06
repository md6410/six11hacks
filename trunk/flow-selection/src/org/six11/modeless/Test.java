package org.six11.modeless;

import org.six11.util.gui.ApplicationFrame;

/**
 * 
 **/
public class Test {
  public static void main(String[] args) {
    go();
  }

  public static ScratchPad makeFlowSelectionComponent() {
    ScratchPad scratch = new ScratchPad();
    scratch.addSelectFunction("select: linear", new LinearSelect());
    scratch.addSelectFunction("select: poly squared", new PolynomialSelect(2.0));
    scratch.addSelectFunction("select: poly root", new PolynomialSelect(0.5));
    scratch.addSelectFunction("select: trig (slow curves)", new TrigSelect());
    scratch.addSelectFunction("select: trig (linear)", new TrigLinearSelect());
    scratch.addSelectFunction("select: corner", new CornerSelect());
    scratch.addSelectFunction("select: slow curves", new CurveSelect());
    scratch.setSelectFunction("select: trig (slow curves)");
    scratch.addOperateFunction(ScratchPad.OP_MOVE, new MoveOperation());
    scratch.addOperateFunction(ScratchPad.OP_HINGE, new HingeOperation());

    // +------------------------- Smooth operations
    // 'SmoothOperation' is the spline thing
    scratch.addOperateFunction(ScratchPad.OP_SMOOTH, new SmoothOperation(scratch));
    // 'LinearSmooth' tweens to a line, but it doesn't work very nicely
    //    scratch.addOperateFunction(ScratchPad.OP_SMOOTH, new LinearSmooth());

    return scratch;
  }

  public static void go() {
    ApplicationFrame frame = new ApplicationFrame("Flow Selection Prototype");
    ScratchPad scratch = makeFlowSelectionComponent();
    frame.add(scratch);
    frame.setVisible(true);
  }
}
