/* TestMatrix.java */
package org.six11.flatcad;

import org.six11.util.Debug;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.apache.commons.math.linear.RealMatrix;
import java.io.*; // never do this in a real application
import java.util.*; 

/**
 * My test file looks like this:
 * <pre>
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 10 11 12
 * 13 14 15
 * 16 17 18
 * </pre>
 */
public class TestMatrix {
  public static final void main(String[] args) throws Exception {
    if (args.length == 1) {
      BufferedReader in = new BufferedReader(new FileReader(args[0]));
      RealMatrix a, b;
      a = readOneMatrix(in);
      b = readOneMatrix(in);
      report("a", a);
      report("b", b);
      report("a * b", a.multiply(b));
      report("b * a", b.multiply(a));
      double[] pt = new double[] {1d, 1d, 1d};
      double[] pt_transformed = a.operate(pt);
      report ("a * " + getDoubleArrToStr(pt), pt_transformed);

      
    }
  }

  private static String getDoubleArrToStr(double[] data) {
    StringBuffer buf = new StringBuffer();
    buf.append("{");
    boolean comma = false;
    for (int i=0; i < data.length; i++) {
      if (comma) {
	buf.append(", ");
      }
      comma = true;
      buf.append(Debug.num(data[i]));

    }
    buf.append("}");
    return buf.toString();
  }

  private static void report(String name, double[] data) {
    String buf = getDoubleArrToStr(data);
    Debug.out("TestMatrix", data.length + " data points, " + name + " is " + buf);
  }

  private static void report(String name, RealMatrix mat) {
    Debug.out("TestMatrix", "Matrix " + name + " is " + mat);
  }

  private static RealMatrix readOneMatrix(BufferedReader in) throws Exception {
    RealMatrix ret = null;
    List rows = new ArrayList();
    double[] numbers = null;
    StringTokenizer lineTok;
    String line;
    while (in.ready() && ((line = in.readLine()).length() > 0)) {
      lineTok = new StringTokenizer(line);
      numbers = new double[lineTok.countTokens()];
      for (int i=0; i < numbers.length; i++) {
	numbers[i] = Double.parseDouble(lineTok.nextToken());
      }
      rows.add(numbers);
    }
    if (numbers != null) {
      double[][] matData = new double[rows.size()][numbers.length];
      double[] rowData;
      for (int i=0; i < rows.size(); i++) {
	rowData = (double[]) rows.get(i);
	for (int j=0; j < rowData.length; j++) {
	  matData[i][j] = rowData[j];
	}
      }
      ret = new RealMatrixImpl(matData);
    }
    return ret;
  }
}
