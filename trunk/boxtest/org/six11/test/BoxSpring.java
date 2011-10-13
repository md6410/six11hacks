package org.six11.test;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.testbed.framework.TestbedMain;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.tests.TestList;

public class BoxSpring extends TestbedTest {

  Body thingA;
  Body thingB;
  Body thingC;
  Body thingD;
  Joint distJointAB;
  Joint distJointBC;
  Joint distJointCA;
  Joint distJointCD;
  
  public String getTestName() {
    return "BoxSpring";
  }

  public void initTest() {
    m_world.getGravity().setZero();

    Body ground = null;
    {
      BodyDef bd = new BodyDef();
      ground = m_world.createBody(bd);

      PolygonShape shape = new PolygonShape();
      shape.setAsEdge(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
      ground.createFixture(shape, 0.0f);
    }

    {

      BodyDef bd = new BodyDef();
      bd.type = BodyType.DYNAMIC;
      bd.angularDamping = 5.0f;
      bd.linearDamping = 5.0f;
      
      bd.position.set(0.0f, 15.0f);
      thingA = m_world.createBody(bd);
      thingA.createFixture(box(1.5f, 1.5f), 5.0f);

      bd.position.set(10.f, 5.0f);
      thingB = m_world.createBody(bd);
      thingB.createFixture(box(2.0f, 2.0f), 5.0f);
      
      bd.position.set(6.0f, 20.0f);
      thingC = m_world.createBody(bd);
      thingC.createFixture(box(2.5f, 2.5f), 5.0f);
      
      bd.position.set(25.0f, 2.0f);
      thingD = m_world.createBody(bd);
      thingD.createFixture(ball(1.0f), 5.0f);

      DistanceJointDef jd = new DistanceJointDef();

      jd.frequencyHz = 10.0f;
      jd.dampingRatio = 1.0f;
      jd.localAnchorA.set(0.0f, 0.0f);
      jd.localAnchorB.set(0.0f, 0.0f);
      
      jd.bodyA = thingA;
      jd.bodyB = thingB;
      jd.length = 15f;
      distJointAB = m_world.createJoint(jd);
      
      jd.bodyA = thingB;
      jd.bodyB = thingC;
      jd.length = 20f;
      distJointBC = m_world.createJoint(jd);
      
      jd.bodyA = thingC;
      jd.bodyB = thingA;
      jd.length = 25f;
      distJointCA = m_world.createJoint(jd);
      
      jd.bodyA = thingC;
      jd.bodyB = thingD;
      jd.length = 10f;
      distJointCD = m_world.createJoint(jd);

    }
  }

  private Shape ball(float val) {
    CircleShape s = new CircleShape();
    s.m_radius = val;
    return s;
  }

  private PolygonShape box(float halfX, float halfY) {
    PolygonShape s = new PolygonShape();
    s.setAsBox(halfX, halfY);
    return s;
  }

  public static void main(String[] args) {
    TestList.tests.clear();
    TestList.tests.add(new BoxSpring());
    TestbedMain.main(args);
  }

}
