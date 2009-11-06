package org.six11.olive2;

/**
 * 
 **/
public abstract class SketchAnalyzer implements StrokeListener {
  
  protected SketchBook book;
  protected boolean observeBegin;
  protected boolean observeProgress;
  protected boolean observeEnd;
  protected boolean observeIntegral;

  public SketchAnalyzer(SketchBook book,
			boolean observeBegin,
			boolean observeProgress,
			boolean observeEnd,
			boolean observeIntegral) {
    this.book = book;
    this.observeBegin = observeBegin;
    this.observeProgress = observeProgress;
    this.observeEnd = observeEnd;
    this.observeIntegral = observeIntegral;
  }

  public void handleBegin(StrokeEvent ev) { }
  public void handleProgress(StrokeEvent ev) { }
  public void handleEnd(StrokeEvent ev) { }
  public void handleIntegral() { }

  public final void handleStrokeAction(StrokeEvent ev) {
    if (observeBegin && ev.isBegin()) {
      handleBegin(ev);
    }

    if (observeProgress && ev.isProgress()) {
      handleProgress(ev);
    }

    if (observeEnd && ev.isEnd()) {
      handleEnd(ev);
    }

  }

  public boolean isIntegral() {
    return observeIntegral;
  }

}
