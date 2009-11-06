package org.six11.flatcad.geom;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.six11.flatcad.gl.GLApp;
import org.six11.util.Debug;

/**
 * A bunch of static methods for processing 3D data or converting from
 * one thing to another. This class is instantiable because I use
 * JUnit to test it but all of the tasty methods are static so you can
 * just statically import them.
 **/
public class MathUtils {

  /**
   * A small number. We can use this as a tolerance to determine if
   * two other numbers are the same.
   */
  public static final double FLOAT_TOLERANCE = 0.0001;

  /**
   * Calculate the area formed by the triangle abc and return it.
   */
  public static double calculateArea(Vertex a, Vertex b, Vertex c) {
    // the area of a triangle in 3D can be found by subtracting away
    // one of the vertices and calculating the cross product.
    Vertex minusA = a.getReverse();
    Vertex one = b.getTranslated(minusA);
    Vertex two = c.getTranslated(minusA);
    Vertex crossProd = one.cross(two);
    double magnitude = crossProd.mag();
    return magnitude / 2.0;
  }

  /**
   * Given the matrix equation a = x * b where x is unknown, calculate x.
   */
  public static RealMatrix calcUnknownTransformLeft(RealMatrix a, RealMatrix b) {
    RealMatrix bInv = b.inverse();
    RealMatrix ret = a.multiply(bInv);
    return ret;
  }

  /**
   * Given the matrix equation a = b * x where x is unknown, calculate x.
   */
  public static RealMatrix calcUnknownTransformRight(RealMatrix a, RealMatrix b) {
    RealMatrix bInv = b.inverse();
    RealMatrix ret = a.preMultiply(bInv);
//     debugMatrixString("a", a);
//     debugMatrixString("b", b);
//     debugMatrixString("x", ret);
    return ret;
  }

  /**
   * Converts radians to degrees.
   */
  public static double radToDeg(double rad) {
    return (rad * 180.0) / Math.PI;
  }

  /**
   * Converts degrees to radians.
   */
  public static double degToRad(double deg) {
    return (deg * Math.PI) / 180.0;
  }

  /**
   * Returns the 4x4 identity matrix.
   */
  public static RealMatrix getIdentityMatrix() {
    double[][] matdata = new double[][] {
      { 1,       0,       0,       0  },
      { 0,       1,       0,       0  },
      { 0,       0,       1,       0  },
      { 0,       0,       0,       1  }
    };
    
    return new RealMatrixImpl(matdata);
  }

  /**
   * Returns a matrix that can be used to scale a vertex. The
   * components of 'amt' specify the scale factors. For example, if
   * amt is {1, 0.5, 1}, then scaling by this matrix will keep the x
   * and z dimensions the same, but the y components will be halved.
   */
  public static RealMatrix getScaleMatrix(Vertex amt) {
    double[][] matdata = new double[][] {
      { amt.x(), 0,       0,       0  },
      { 0,       amt.y(), 0,       0  },
      { 0,       0,       amt.z(), 0  },
      { 0,       0,       0,       1d }
    };
    return new RealMatrixImpl(matdata);
  }

  /**
   * Return a matrix that can be used to translate a vertex. The
   * components of 'amt' specify the translation amounts. So 'amt' of
   * {0, 0, 0} will not move anything, but {1,2,3} will move a point
   * by +1 in the x direction, +2 in y, and +3 in z.
   */
  public static RealMatrix getTranslationMatrix(Vertex amt) {
    double[][] matdata = new double[][] {
      { 1,       0,       0,       amt.x()  },
      { 0,       1,       0,       amt.y()  },
      { 0,       0,       1,       amt.z()  },
      { 0,       0,       0,       1        }
    };
    
    return new RealMatrixImpl(matdata);
  }

  public static List<Vertex> getXYLine(Vertex a, Vertex b) {
    Direction u = new Direction(a, b);
    Vertex c = new Vertex(0d, 0d, 0d);
    Direction arb = new Direction(a, c);
    Direction w = u.cross(arb);
    Direction v = w.cross(u);

    RealMatrix rot = MathUtils.buildMatrixFromVectors(u,v,w);
    Vertex a_rotated = a.getTransformed(rot);
    Vertex b_rotated = b.getTransformed(rot);
    List ret = new ArrayList<Vertex>();
    ret.add(a_rotated);
    ret.add(b_rotated);
    return ret;
  }

  /**
   * Return a matrix that can be used to rotate about an axis as
   * specified by 'dir'. For example to rotate about the z axis, use
   * dir = {0, 0, 1}. It appears that positive radians rotates in the
   * counter-clockwise direction.
   */
  public static RealMatrix getRotationMatrix(Direction dir, double angleRadians) {
    double x = dir.x();
    double y = dir.y();
    double z = dir.z();
    double xx = x*x;
    double yy = y*y;
    double zz = z*z;
    double c = Math.cos(angleRadians);
    double s = Math.sin(angleRadians);
    double t = 1d - c;

    double[][] matdata = new double[][] {
	{ t * xx + c,          t * x * y + s * z,   t * x * z - s * y, 0  },
	{ t * x * y - s * z,   t * yy + c,          t * y * z + s * x, 0  },
	{ t * x * z + s * y,   t * y * z - s * x,   t * zz + c,        0  },
	{ 0,                   0,                   0,                 1d }
      };
    return new RealMatrixImpl(matdata);
  }

  /**
   * Given any matrix representing the result of some coordinate
   * system transformation, return a new matrix containing the three
   * unit vectors that describe it's local orthonormal basis.
   */
  public static RealMatrix getUVW(RealMatrix canonical) {
    Direction u = new Direction(canonical.operate(Direction.X.getData()));
    Direction v = new Direction(canonical.operate(Direction.Y.getData()));
    Direction w = new Direction(canonical.operate(Direction.Z.getData()));
    return buildMatrixFromVectors(u, v, w);
  }

  /**
   * Return the first column of a matrix as a direction -- goes well
   * with getUVW(..). You can think of this as the 'local x'
   * dimension.
   */
  public static Direction getU(RealMatrix uvw) {
    return new Direction(uvw.getColumn(0));
  }

  /**
   * Return the second column of a matrix as a direction -- goes well
   * with getUVW(..). You can think of this as the 'local y'
   * dimension.
   */
  public static Direction getV(RealMatrix uvw) {
    return new Direction(uvw.getColumn(1));
  }

  /**
   * Return the third column of a matrix as a direction -- goes well
   * with getUVW(..). You can think of this as the 'local z'
   * dimension.
   */
  public static Direction getW(RealMatrix uvw) {
    return new Direction(uvw.getColumn(2));
  }

  /**
   * Return the last column of a matrix as a direction -- goes well
   * with getUVW(..). You can think of this as the 'local
   * translation'.
   */
  public static Direction getT(RealMatrix uvw) {
    return new Direction(uvw.getColumn(3));
  }

  /**
   * Detect if a and b are within MathUtils.FLOAT_TOLERANCE of one
   * another.
   */
  public static boolean is(double a, double b) {
    return Math.abs(a - b) < FLOAT_TOLERANCE;
  }


  public static void debugMatrixString(String label, RealMatrix m) {
    double[] d;
    d = m.getRow(0);
    Debug.out("MathUtils", label + ": [" +  Debug.num(d[0], 2) + " " + Debug.num(d[1], 2) + 
	      " " + Debug.num(d[2], 2) + " " + Debug.num(d[3], 2) + " ]");
    d = m.getRow(1);
    Debug.out("MathUtils", label + ": [" +  Debug.num(d[0], 2) + " " + Debug.num(d[1], 2) + 
	      " " + Debug.num(d[2], 2) + " " + Debug.num(d[3], 2) + " ]");
    d = m.getRow(2);
    Debug.out("MathUtils", label + ": [" +  Debug.num(d[0], 2) + " " + Debug.num(d[1], 2) + 
	      " " + Debug.num(d[2], 2) + " " + Debug.num(d[3], 2) + " ]");
    d = m.getRow(3);
    Debug.out("MathUtils", label + ": [" +  Debug.num(d[0], 2) + " " + Debug.num(d[1], 2) + 
	      " " + Debug.num(d[2], 2) + " " + Debug.num(d[3], 2) + " ]");

  }

  /**
   * Given a local vertex, return the 3D world value that is the
   * result of multiplying:
   *
   * ret = modelview * local
   */
  public static Vertex multiplyWithModelview(Vertex local) {
    float[][] modelView = GLApp.getModelviewMatrixA();
    RealMatrix mv = get4x4RealMatrix(modelView);
    double[] localData = local.getData();
    Vertex ret = new Vertex(mv.preMultiply(localData));
    return ret;
  }

  public static Vertex calcVertex(RealMatrix ma, Vertex in) {
    return new Vertex(ma.operate(in.getData()));
  }

  public static RealMatrix buildMatrixFromVectors(Direction u, Direction v, Direction w) {
    double[][] doubles = new double[4][4];
    doubles[0][0] = u.x();
    doubles[0][1] = u.y();
    doubles[0][2] = u.z();
    doubles[0][3] = 0d;

    doubles[1][0] = v.x();
    doubles[1][1] = v.y();
    doubles[1][2] = v.z();
    doubles[1][3] = 0d;

    doubles[2][0] = w.x();
    doubles[2][1] = w.y();
    doubles[2][2] = w.z();
    doubles[2][3] = 0d;

    doubles[3][0] = 0d;
    doubles[3][1] = 0d;
    doubles[3][2] = 0d;
    doubles[3][3] = 1d;

    RealMatrix ret = new RealMatrixImpl(doubles);

    return ret;
  }

  /**
   * Given a FloatBuffer that is assumed to have 16 values, return a
   * 4x4 RealMatrix.
   */
  public static RealMatrix get4x4RealMatrix(FloatBuffer fourbyfour) {

    float[] floatValues = new float[16];
    fourbyfour.get(floatValues);
    double[][] values = new double[4][4];
    for (int i=0; i < 4; i++) {
      for (int j=0; j < 4; j++) {
	values[i][j] = (double) floatValues[i*4+j];
      }
    }
    return new RealMatrixImpl(values);
  }

  public static RealMatrix get4x4RealMatrix(float[][] fourbyfour) {
    double[][] doubles = new double[4][4];
    for (int i=0; i < 4; i++) {
      for (int j=0; j < 4; j++) {
	doubles[i][j] = fourbyfour[i][j];
      }
    }
    return MatrixUtils.createRealMatrix(doubles);
//     double[][] values = new double[4][4];
//     StringBuffer b = new StringBuffer();
//     for (int i=0; i < 4; i++) {
//       for (int j=0; j < 4; j++) {
// 	values[j][i] = (double) fourbyfour[j][i];
// 	b.append(Debug.num(values[j][i]) + "\t");
//       }
//       b.append("\n");
//     }
//     Debug.out("MathUtils", b.toString());
//     return new RealMatrixImpl(values);    
  }

  /**
   * Given ANY string, produce a floating point value. If the string
   * doesn't parse to a float, just return zero.
   */
  public static float toFloat(String n) {
    float ret = 0;
    try {
      ret = (float) Double.parseDouble(n);
    } catch (Exception ignore) { 
      Debug.out("MathUtils", "Error converting string '" + n + "' to number. returning 0.");
    }
    return ret;
  }

  public static boolean toTruth(String n) {
    return "true".equals(n);
  }

  /**
   * If you have a matrix of reals, fill a double buffer with the values.
   */
  public static void fillDoubleBuffer(RealMatrix m, DoubleBuffer fillMe) {
    double[][] data = m.getData();
    int w = data.length;
    int h = data[0].length;
    // fill the buffer in column major mode, so:
    // a e i m
    // b f j n  ---> [a, b, c, d, e, f ... ] 
    // c g k o 
    // d h l p
    for (int i=0; i < w; i++) {
      for (int j=0; j < h; j++) { 
	fillMe.put(h * j + i, data[i][j]);
      }
    }
  }

  public static boolean isNumber(String s) {
    boolean ret = false;
    try {
      Double.parseDouble(s);
      ret = true;
    } catch (Exception ex) { }
    return ret;
  }

  /* ------------------------------     Test functions. ------- */

  @Test public void testRotationMatrix() {
//     Direction dir = new Direction(0d, 0d, 1d); // z axis
//     double angle = degToRad(-90d);
//     RealMatrix mat = getRotationMatrix(dir, angle);
//     Vertex before = new Vertex(1d, 0d, 0d);
//     Vertex after = before.getTransformed(mat);
//     assertEquals(0.0, after.x(), FLOAT_TOLERANCE);
//     assertEquals(1.0, after.y(), FLOAT_TOLERANCE);
//     assertEquals(0.0, after.x(), FLOAT_TOLERANCE);
    
//     // now try rotating by the same amount but about some other
//     // point. Take the false origin to be at, say, {-4, 10, 7}, and
//     // the point to rotate is at {-3, 10, 7}. You should end up with
//     // {-4, 11, 7}.
//     before = new Vertex(-3d, 10d, 7d);
//     Vertex falseOrigin = new Vertex(-4d, 10d, 7d);
//     after = before.getRotated(mat, falseOrigin);
//     assertEquals(-4d, after.x(), FLOAT_TOLERANCE);
//     assertEquals(11d, after.y(), FLOAT_TOLERANCE);
//     assertEquals(7d, after.z(), FLOAT_TOLERANCE);
  }

  @Test public void testTranslationMatrix() {
    Vertex upV = new Vertex(0d, 0d, 10d);
    Vertex overV = new Vertex(10d, 0d, 0d);
    RealMatrix up = getTranslationMatrix(upV);
    RealMatrix over = getTranslationMatrix(overV);

    Vertex v = new Vertex(1d,1d,1d);
    v.transform(up);
    assertEquals(1d, v.x());
    assertEquals(1d, v.y());
    assertEquals(11d, v.z());
    
    v.transform(over);
    assertEquals(11d, v.x());
    assertEquals(1d, v.y());
    assertEquals(11d, v.z());
  }

  
}
