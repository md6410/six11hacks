package org.six11.flatcad.geom;

import org.apache.commons.math.linear.RealMatrix;
import java.util.Stack;
import java.nio.DoubleBuffer;
import org.six11.flatcad.gl.GLApp;
import org.six11.util.Debug;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A MatrixStack is simply a java.util.Stack that works with
 * RealMatrix objects. At any point you can ask for the 'current
 * matrix' which is just all the matrixes on the stack multiplied together in order of 
 **/
public class MatrixStack extends Stack<RealMatrix> {

  /**
   * The current result of multiplying the matrices together. It is
   * nulled out whenever a push/pop is exectued, so it must be
   * recalculated by the getCurrent() method.
   */
  protected RealMatrix current;

  /**
   * This is a pre-allocated DoubleBuffer for use with getting the
   * matrix stack value in a format that glMultMatrix can use.
   */
  protected DoubleBuffer doubleBuffer;

  public MatrixStack() {
    this(true);
  }

  public MatrixStack(boolean ident) {
    if (ident) {
      //      push(MathUtils.getIdentityMatrix());
      current = MathUtils.getIdentityMatrix();
    }
  }

  private void forget() {
    current = null;
    doubleBuffer = null;
  }

  /**
   * Pop the most recent entry of the stack, removing and returning
   * it.
   */
  public RealMatrix pop() {
    forget();
    return super.pop();
  }

  /**
   * Pushes a new entry onto the stack, returning argument (for some
   * reason... I'm just doing what the Java API does).
   */
  public RealMatrix push(RealMatrix rm) {

    //    forget();

    if (rm == null) {
      Debug.out("MatrixStack", "Warning: you are adding a null matrix to the stack. " +
		"Probably not what you want.");
      new RuntimeException("hi").printStackTrace();
    }

    RealMatrix ret = super.push(rm);
    current = current.multiply(rm);
    return ret;
  }

  /**
   * Return the matrix that is the result of multiplying together all
   * the matrices in the stack in reverse order. In other words, if
   * there are three matrices in the stack, the current matrix is the
   * result of performing: 3 * 2 * 1. 
   *
   * MatrixStack caches your result, so the 'current value' is only
   * calculated one time and used until you pop or push a value. NOTE:
   * if you use any of the List methods such as add/remove, you are
   * boned because this implementation only notices pushes and pops.
   */
  public RealMatrix getCurrent() {
    if (current == null) {
      current = MathUtils.getIdentityMatrix();
      for (RealMatrix rm : this) {
	current = current.multiply(rm); // current = current * rm
      }
    }
    return current;
  }

  /**
   * Returns the same as getCurrent() but in a format that can be used
   * with glMultMatrix(). Note that the return value is shared among
   * all callers of this MatrixStack, and is not thread-safe. For best
   * results, make a copy if you need to hang on to it for a while.
   */
  public DoubleBuffer getCurrentDoubleBuffer() {
    if (doubleBuffer == null) {
      doubleBuffer = GLApp.allocDoubles(16);
      
      RealMatrix rm = getCurrent();
      MathUtils.fillDoubleBuffer(rm, doubleBuffer);
    }
    return doubleBuffer;
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testBasic() {
//     MatrixStack mats = new MatrixStack();
//     Direction dir = new Direction(0,0,1); // z axis
//     double angle = MathUtils.degToRad(-90);

//     Vertex first = new Vertex(-3, 10, 7);
//     Vertex afterTrans, afterRot, afterBack;
//     RealMatrix trans = MathUtils.getTranslationMatrix(new Vertex(4, -10, -7));
//     mats.push(trans);
//     afterTrans = first.getTransformed(mats.getCurrent());
//     assertEquals(1d, afterTrans.x());
//     assertEquals(0d, afterTrans.y());
//     assertEquals(0d, afterTrans.z());

//     RealMatrix rot = MathUtils.getRotationMatrix(dir, angle);
//     mats.push(rot);
//     afterRot = first.getTransformed(mats.getCurrent());
//     assertEquals(0d, afterRot.x());
//     assertEquals(1d, afterRot.y());
//     assertEquals(0d, afterRot.z());

//     RealMatrix back  = MathUtils.getTranslationMatrix(new Vertex(-4, 10, 7));
//     mats.push(back);
//     afterBack = first.getTransformed(mats.getCurrent());
//     assertEquals(-4d, afterBack.x());
//     assertEquals(11d, afterBack.y());
//     assertEquals(7d, afterBack.z());

//     mats.pop(); // should remove 'back'
//     Vertex sameAsAfterRot = first.getTransformed(mats.getCurrent());
//     assertEquals(0d, sameAsAfterRot.x());
//     assertEquals(1d, sameAsAfterRot.y());
//     assertEquals(0d, sameAsAfterRot.z());
  }

  
}
