package org.six11.hacks.ballz;

import java.util.ArrayList;

import processing.core.*;
import static processing.core.PApplet.min;

public class OldBall {
  float d;
  PVector loc;
  PVector vec;
  int hitCount;
  boolean angry, happy;
  PApplet p;
  float pOneChild = 0.4f;
  float pTwoChild = 0.2f;
  int maxBalls = 100;

  OldBall(PApplet parent, float x, float y, float d) {
    this.p = parent;
    this.loc = new PVector(x, y);
    this.d = d;
    this.vec = new PVector(p.random(-1.0f, 1.0f), p.random(-1.0f, 1.0f));
    this.hitCount = 90;
  }

  void move() {
    loc.x = loc.x + vec.x;
    loc.y = loc.y + vec.y;
    float r = d / 2;
    if (loc.y - r < 0 && vec.y < 0) {
      vec.y = -vec.y;
      d++;
    }
    if (loc.y + r > p.height && vec.y > 0) {
      vec.y = -vec.y;
      d++;
    }
    if (loc.x - r < 0 && vec.x < 0) {
      vec.x = -vec.x;
      d++;
    }
    if (loc.x + r > p.width && vec.x > 0) {
      vec.x = -vec.x;
      d++;
    }
  }

  public float radius() {
    return d / 2;
  }

  boolean collide(OldBall other) {
    float dist = other.loc.dist(loc);
    float thresh = other.radius() + radius();
    if (dist < thresh) {
      return true;
    } else {
      return false;
    }
  }

  int getColor() {
    return p.color(angry ? 255 : 0, hitCount, happy ? 255 : 0);
  }

  void whack() {
    hitCount = min(hitCount + 1, 255);
  }

  void collide(ArrayList<OldBall> others) {
    vec.limit(5);
    if (others.contains(this)) {
      ArrayList<OldBall> doomed = new ArrayList<OldBall>();
      ArrayList<OldBall> children = new ArrayList<OldBall>();
      for (int i = 0; i < others.size(); i++) {
        OldBall other = (OldBall) others.get(i);
        if (other != this) {
          if (collide(other)) {
            PVector toOther = new PVector(other.loc.x - loc.x, other.loc.y - loc.y);
            toOther.normalize();
            other.vec.add(toOther);
            toOther = new PVector(-toOther.x, -toOther.y);
            vec.add(toOther);
            whack();
            other.whack();
            float fate = p.random(1.0f);
            float thresh = (255.0f - hitCount) / 255.0f;
            thresh = (thresh * thresh) / 2.0f;
            if (fate < thresh) {
              if (d > 2 * other.d) {
                if (happy && other.angry) {
                  d = d * 1.3f;
                  doomed.add(other);
                }
              } else {
                doomed.add(this);
                OldBall a = new OldBall(p, loc.x, loc.y, d * 0.7f);
                a.vec = vec;
                a.angry = true;
                OldBall b = new OldBall(p, loc.x, loc.y, d * 0.7f);
                b.vec = new PVector(vec.y, vec.x); // x and y swapped
                b.happy = true;
                if (p.random(1.0f) > pOneChild) {
                  children.add(a);
                }
                if (p.random(1.0f) > pTwoChild) {
                  children.add(b);
                }
              }
            }
          }
        }
      }
      others.removeAll(doomed);
      for (int i = 0; i < children.size(); i++) {
        if (others.size() < maxBalls) {
          others.add(children.get(i));
        }
      }
    }
    if (others.size() > 20) {
      vec.mult(0.993f);
    }
  }

  void draw() {
    p.fill(getColor());
    p.noStroke();
    p.ellipse(loc.x, loc.y, d, d);
  }
}