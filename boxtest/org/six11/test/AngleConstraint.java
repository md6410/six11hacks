package org.six11.test;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.testbed.framework.TestbedMain;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.tests.TestList;
import org.six11.util.Debug;
import org.six11.util.pen.Functions;
import org.six11.util.pen.Pt;
import org.six11.util.pen.Vec;

public class AngleConstraint extends TestbedTest {

  private static final double DESIRED_ANGLE_DEG = 135;
  private static final float DESIRED_LENGTH = 18;
  Body thingA;
  Body thingB;
  Body fulcrum;
  Body worldOrigin;
  float amt = 2.8f;

  RevoluteJoint angleJointABC;
//  DistanceJoint distA, distB;

  public String getTestName() {
    return "AngleConstraint";
  }

  public void initTest() {
    m_world.getGravity().setZero();

    BodyDef bd = new BodyDef();
    //    bd.type = BodyType.STATIC;
    //    worldOrigin = m_world.createBody(bd);
    //    worldOrigin.createFixture(ball(0.5f), 5f); // 'ball' returns a CircleShape with the given radius

    bd.type = BodyType.DYNAMIC;
    bd.angularDamping = 5.0f;
    bd.linearDamping = 5.0f;

    bd.position.set(s(0), s(1));
    fulcrum = m_world.createBody(bd);
    fulcrum.createFixture(box(0.5f, 0.5f), 5.0f);

    bd.position.set(s(0), s(4));
    thingA = m_world.createBody(bd);
    thingA.createFixture(ball(1.0f), 5.0f);

    bd.position.set(s(0), s(4));
    thingB = m_world.createBody(bd);
    thingB.createFixture(ball(1.0f), 5.0f);

    buildJoint();

//    DistanceJointDef jdef = new DistanceJointDef();
//    jdef.length = DESIRED_LENGTH;
//    jdef.bodyA = fulcrum;
//    jdef.bodyB = thingA;
//    distA = (DistanceJoint) m_world.createJoint(jdef);
//
//    jdef.bodyA = fulcrum;
//    jdef.bodyB = thingB;
//    distB = (DistanceJoint) m_world.createJoint(jdef);
  }

  private void buildJoint() {
    RevoluteJointDef angleDef = new RevoluteJointDef();
    angleDef.bodyA = thingA;
    angleDef.bodyB = thingB;
    angleDef.localAnchorA = spot(thingA); // coordinate of fulcrum for thingA 
    angleDef.localAnchorB = spot(thingB); // coordinate of fulcrum for thingB
    angleDef.lowerAngle = (float) Math.toRadians(DESIRED_ANGLE_DEG);
    angleDef.upperAngle = (float) Math.toRadians(DESIRED_ANGLE_DEG);
    angleDef.enableLimit = true;
    angleJointABC = (RevoluteJoint) m_world.createJoint(angleDef);
  }

  private float s(float v) {
    return amt * v;
  }

  private PolygonShape box(float halfX, float halfY) {
    PolygonShape s = new PolygonShape();
    s.setAsBox(halfX, halfY);
    return s;
  }

  public void step(TestbedSettings settings) {
    super.step(settings);
    float fToA = MathUtils.distance(fulcrum.getPosition(), thingA.getPosition());
    float fToB = MathUtils.distance(fulcrum.getPosition(), thingB.getPosition());
    addTextLine("Fulcrum to A: " + Debug.num(fToA)); // converges to the correct value
    addTextLine("Fulcrum to B: " + Debug.num(fToB)); // converges to the correct value
    m_world.destroyJoint(angleJointABC);
    Pt fulPt = makePt(fulcrum.getPosition());
    Pt aPt = makePt(thingA.getPosition());
    Pt bPt = makePt(thingB.getPosition());
    double ang = Math.toDegrees(Functions.getAngleBetween(new Vec(fulPt, aPt), new Vec(fulPt, bPt)));
    addTextLine("Angle: " + Debug.num(ang)); // converges to the wrong value
    buildJoint(); // set up joint for next iteration
  }

  private Pt makePt(Vec2 v) {
    return new Pt(v.x, v.y);
  }

  private Vec2 spot(Body who) {
    Vec2 a = who.getWorldCenter();
    Vec2 b = fulcrum.getWorldCenter();
    Vec2 ret = new Vec2(-a.x + b.x, -a.y + b.y);
    return ret;
  }

  private Shape ball(float val) {
    CircleShape s = new CircleShape();
    s.m_radius = val;
    return s;
  }

  public static void main(String[] args) {
    TestList.tests.clear();
    TestList.tests.add(new AngleConstraint());
    TestbedMain.main(args);
  }

}
