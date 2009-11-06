package org.six11.flatcad.model;

/**
 * A FlatCAD operation such as (F)old, (L)ayer, (A)ttach, (T)rim.
 *
 * Each operation produces a Generation, and probably involves a
 * parent Generation as well, unless this is an InitOperation.
 **/
public abstract class Operation {
  
  /**
   * The Generation that is a result of performing this
   * operation. Subclasses must set this value in their constructor.
   */
  protected Generation result;

  /**
   * Returns the generation that results from performing this
   * operation.
   */
  public Generation getResultGeneration() {
    return result;
  }


}
