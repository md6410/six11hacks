package org.six11.modeless;

/**
 * A trigonometric selection function. At the end of the selection region (the most weakly selected
 * points) the strength tapers off asymptotically to zero. Near the middle of the selection (near
 * the epicenter) the strength approaches 1.0 in a similar asymptotic fashion. The first derivative
 * of the graph is zero at the epicenter and the endpoint.
 **/
public class TrigSelect extends CurveSelect {
  protected double strengthFunction(double thisDist, double maxDistance) {
    double x = thisDist / maxDistance;
    double str;
    if (x >= 1.0) {
      str = 0.0;
    } else {
      str = 1.0 - ((-1.0 * Math.cos(Math.PI * x)) + 1) / 2.0;
    }
    if (str < 0.0)
      str = 0.0;
    if (str > 1.0)
      str = 1.0;
    return str;
  }

}
