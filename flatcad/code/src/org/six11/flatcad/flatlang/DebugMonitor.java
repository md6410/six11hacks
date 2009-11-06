package org.six11.flatcad.flatlang;

import org.six11.util.Debug;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 **/
public class DebugMonitor {

  final int GO_ONCE = 1;
  final int STOP = 0;
  final int GO_FOREVER = -1;

  boolean aborted;
  int statements;
  int expressions;
  int stopLineNum;

  List<DebugMonitorListener> listeners = new ArrayList<DebugMonitorListener>();
  
  public DebugMonitor() {
    reset();
  }

  public boolean isLineNumStopSet() {
    return stopLineNum >= 0;
  }

  public int getLineNumStop() {
    return stopLineNum;
  }

  public void setLineNumStop(int where) {
    stopLineNum = where;
    statements = GO_FOREVER;
    expressions = GO_FOREVER;
  }

  public synchronized void reset() {
    aborted = false;
    stopLineNum = GO_FOREVER;
    statements = GO_FOREVER;
    expressions = GO_ONCE;
  }

  public synchronized void abort() {
    // stop debugging!
    aborted = true;
    statements = GO_FOREVER;
    expressions = GO_FOREVER;
    Debug.out("DebugMonitor", "Telling everybody that we're aborting.");
    notifyAll();
  }

  public synchronized boolean isAborted() {
    return aborted;
  }

  public synchronized void allowAllStatements() {
    statements = GO_FOREVER;
    Debug.out("DebugMonitor", "ok, flagging all statments allowed");
    notifyAll();
  }

  public synchronized void allowStatement() {
    statements = GO_ONCE;
    Debug.out("DebugMonitor", "ok, flagging one statment allowed");
    notifyAll();
  }

  public synchronized boolean consumeStatement() {
    boolean ret = false;
    if (statements == GO_ONCE) {
      statements = STOP;
      ret = true;
    } else if (statements == GO_FOREVER) {
      ret = true;
    }
    return ret || consumeExpression();
  }

  public synchronized void allowAllExpressions() {
    expressions = GO_FOREVER;
    Debug.out("DebugMonitor", "ok, flagging all statments allowed");
    notifyAll();
  }

  public synchronized void allowExpression() {
    expressions = GO_ONCE;
    Debug.out("DebugMonitor", "ok, flagging one expression allowed");
    notifyAll();
  }

  public synchronized boolean consumeExpression() {
    boolean ret = false;
    if (expressions == GO_ONCE) {
      expressions = STOP;
      ret = true;
    } else if (expressions == GO_FOREVER) {
      ret = true;
    }
    return ret;
  }

  public static interface DebugMonitorListener {
    public void setCurrentDebugInfo(String code, int lineNum, int charNum, Register retVal, boolean ended);
  }

  public void addListener(DebugMonitorListener lis) {
    if (!listeners.contains(lis)) {
      listeners.add(lis);
    }
  }

  public void setCurrentDebugInfo(String code, int lineNum, int charNum, Register retVal, boolean ended) {
    for (DebugMonitorListener lis : listeners) {
      lis.setCurrentDebugInfo(code, lineNum, charNum, retVal, ended);
    }
  }

}
