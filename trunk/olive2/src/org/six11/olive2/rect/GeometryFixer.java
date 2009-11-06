package org.six11.olive2.rect;

import org.six11.util.pen.Sequence;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Functions;
import org.six11.util.math.Function;
import org.six11.util.math.Function.FunctionData;
import org.six11.util.math.FunctionFeatureFinder;
import org.six11.util.gui.shape.Triangle;
import org.six11.util.Debug;
import java.io.FileWriter;
import java.util.List;

/**
 * 
 **/
public abstract class GeometryFixer {
  
  public static void main(String[] args) throws Exception {
    final Triangle pure = new Triangle(2d, 2d, 1d);
    final Sequence userData = new Sequence();
    userData.add(new Pt(2.0, 2.56));
    userData.add(new Pt(1.5, 1.5));
    userData.add(new Pt(1.408, 1.48));
    userData.add(new Pt(2.0, 2.56));
    String file = "data.txt";
    if (args.length > 0) {
      file = args[0];
    }

    Function errorFunction = new Function() {
	public double eval(double x) {
	  pure.rotateTo(x);
	  return measureError(join(pure.getGeometry()), userData);
	}
      };
    //    simulate(file, errorFunction);
    double lower = 0d;
    double upper = Math.PI * 4d;
    FunctionData functionData = errorFunction.getFunctionData(lower, upper, 0.1);
//     Debug.out("GeometryFixer", "According to the errorFunction, it's smallest value in the range of " + 
// 	      Debug.num(lower) + " to " + Debug.num(upper) + " is f(" + Debug.num(functionData.x_min, 5) +
// 	      ") = " + Debug.num(functionData.f_min, 5) + ".");
    FunctionFeatureFinder fff = new FunctionFeatureFinder(100, 0.0001, 0.0001);
    double min = fff.seekMin(errorFunction, functionData.x_min);
//     Debug.out("GeometryFixer", "According to the feature finder, the min is f(" + 
// 	      Debug.num(min, 5) +") = " + 
// 	      Debug.num(errorFunction.eval(min), 5) + ".");
  }

  public static double findMinimalError(Function errorFunction,
					double lower,
					double upper,
					double stepSize) {
    FunctionData functionData = errorFunction.getFunctionData(lower, upper, stepSize);
//     Debug.out("GeometryFixer", "According to the errorFunction, it's smallest value in the range of " + 
// 	      Debug.num(lower) + " to " + Debug.num(upper) + " is f(" + Debug.num(functionData.x_min, 5) +
// 	      ") = " + Debug.num(functionData.f_min, 5) + ".");
    FunctionFeatureFinder fff = new FunctionFeatureFinder(100, 0.0001, 0.0001);
    double min = fff.seekMin(errorFunction, functionData.x_min);
//     Debug.out("GeometryFixer", "According to the feature finder, the min is f(" + 
// 	      Debug.num(min, 5) +") = " + 
// 	      Debug.num(errorFunction.eval(min), 5) + ".");
    return min;
  }

  public static void simulate(String file, Function errorFunction) throws Exception {

    double step = 0.1;
    double x;
    FileWriter writer = new FileWriter(file);
    for (int i=0; ((double)i) * step < (20d* Math.PI); i++) {
      x = ((double)i) * step;
      writer.write(x + "\t" + errorFunction.eval(x) + "\n");
    }
    writer.close();
  }

  public static double rotateToMinimizeError(Sequence changeMe, Pt changeMePivot, 
					     Sequence target, Pt targetPivot) {
    return 0d;
  }

  public static Sequence join(List<Sequence> many) {
    Sequence ret = new Sequence();
    for (Sequence seq : many) {
      for (Pt pt : seq) {
	ret.add(pt.copy());
      }
    }
    return ret;
  }

  public static double measureError(Sequence seq, Sequence target) {
    double error = 0d;
    for (Pt pt : seq) {
      error = error + Functions.getMinDistBetweenPointAndSequence(pt, target);
    }
    return error;
  }

}
