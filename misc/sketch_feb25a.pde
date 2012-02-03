
class Ball {
  float d;
  PVector loc;
  PVector vec;
  int hitCount;
  boolean angry, happy;

  Ball(float x, float y, float d) {
    this.loc = new PVector(x, y);
    this.d = d;
    this.vec = new PVector(random(-1.0, 1.0), random(-1.0, 1.0));
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
    if (loc.y + r > height && vec.y > 0) { 
      vec.y = - vec.y; 
      d++;
    }
    if (loc.x - r < 0 && vec.x < 0) { 
      vec.x = -vec.x; 
      d++;
    }
    if (loc.x + r > width && vec.x > 0) {
      vec.x = -vec.x; 
      d++;
    }
  }

  float radius() {
    return d / 2;
  }

  boolean collide(Ball other) {
    float dist = other.loc.dist(loc);
    float thresh = other.radius() + radius();
    if (dist < thresh) {
      return true;
    } 
    else {
      return false;
    }
  }

  color getColor() {
    return color(angry ? 255 : 0, hitCount, happy ? 255 : 0);
  }

  void whack() {
    hitCount = min(hitCount + 1, 255);
  }

  void collide(ArrayList others) {
    vec.limit(5);
    if (others.contains(this)) {
      ArrayList<Ball> doomed = new ArrayList<Ball>();
      ArrayList<Ball> children = new ArrayList<Ball>();
      for (int i=0; i < others.size(); i++) {
        Ball other = (Ball) others.get(i);
        if (other != this) {
          if (collide(other)) {
            PVector toOther = new PVector(other.loc.x - loc.x, other.loc.y - loc.y);
            toOther.normalize();
            other.vec.add(toOther);
            toOther = new PVector(-toOther.x, -toOther.y);
            vec.add(toOther);
            whack();
            other.whack();
            float fate = random(1.0);
            float thresh = (255.0 - hitCount) / 255.0;
            thresh = (thresh * thresh) / 2.0;
            if (fate < thresh) {
              if (d > 2*other.d) {
                if (happy && other.angry) {
                  d = d * 1.3;
                  doomed.add(other);
                }
              }
              else {
                doomed.add(this);
                Ball a = new Ball(loc.x, loc.y, d * 0.7);
                a.vec = vec;
                a.angry = true;
                Ball b =  new Ball(loc.x, loc.y, d * 0.7);
                b.vec = new PVector(vec.y, vec.x); // x and y swapped
                b.happy = true;
                if (random(1.0) > pOneChild) {
                  children.add(a);
                }
                if (random(1.0) > pTwoChild) {
                  children.add(b);
                }
              }
            }
          }
        }
      }
      others.removeAll(doomed);
      for (int i=0; i < children.size(); i++) {
        if (others.size() < maxBalls) {
          others.add(children.get(i));
        }
      }
    }
    if (others.size() > 20) {
      vec.mult(0.993);
    }
  }

  void draw() {
    fill(getColor());
    noStroke();
    ellipse(loc.x, loc.y, d, d);
  }
}

ArrayList balls;
float pOneChild = 0.4;
float pTwoChild = 0.2;
int maxBalls = 100;

void setup() {
  size(600, 400);
  smooth();
  float r = 35;
  balls = new ArrayList();
  for (int i=0; i < 4; i++) {
    balls.add(new Ball(random(r, width - r), random(r, height - r), 2 * r));
  }
}

void draw() {
  background(0);
  for (int i=0; i < balls.size(); i++) {
    Ball b = (Ball) balls.get(i);
    b.move();
    b.draw();
    b.collide(balls);
  }
}

