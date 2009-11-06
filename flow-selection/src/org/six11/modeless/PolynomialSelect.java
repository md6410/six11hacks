package org.six11.modeless;

/**
 *
 */
public class PolynomialSelect extends LinearSelect {

  protected double exponent;

  public PolynomialSelect(double exponent) {
    this.exponent = exponent;
  }

  protected double strengthFunction(double thisDist, double maxDistance) {
    return Math.max(0.0, 1.0 - Math.pow(thisDist/maxDistance, exponent));
  }

}
