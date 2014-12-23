package org.andengine.extension.rubeloader.factory;

import java.util.List;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;

public class JointsFactory implements IJointsFactory {

	public Joint produce(PhysicsWorld pWorld, List<Body> pBodies, final JointDef pJointDef, final AutocastMap pMap) {
		if (pJointDef.type != JointType.GearJoint) {
			Joint joint = doCreateJoint(pWorld, pBodies, pJointDef, pMap);
			if (joint != null) {
				if (pJointDef.type == JointType.MouseJoint) {
					Vector2 mouseJointTarget = pMap.getVector2("target");
					((MouseJoint) joint).setTarget(mouseJointTarget);
				}
			}
			return joint;
		} else {
			return null;
		}
	}

	public Joint produceGearJoint(PhysicsWorld pWorld, List<Body> pBodies, List<Joint> pJoints, final JointDef pJointDef, final AutocastMap pMap) {
		if (pJointDef.type == JointType.GearJoint) {
			int jointIndex1 = pMap.getInt("joint1");
			int jointIndex2 = pMap.getInt("joint2");

			GearJointDef gearJointDef = (GearJointDef) pJointDef;
			gearJointDef.joint1 = pJoints.get(jointIndex1);
			gearJointDef.joint2 = pJoints.get(jointIndex2);

			if ((gearJointDef.joint1 == null) || (gearJointDef.joint2 == null)) {
				System.out.println("Bad Joint Index! joint1=" + jointIndex1 + "; joint2=" + jointIndex2);
				return null;
			}

			Joint joint = doCreateJoint(pWorld, pBodies, pJointDef, pMap);
			return joint;
		} else {
			return null;
		}
	}

	private Joint doCreateJoint(PhysicsWorld pWorld, List<Body> pBodies, final JointDef pJointDef, final AutocastMap pMap) {
		int bodyIndexA = pMap.getInt("bodyA");
		int bodyIndexB = pMap.getInt("bodyB");

		if (bodyIndexA >= pBodies.size() || bodyIndexB >= pBodies.size()) {
			System.out.println("Bad Body Index! bodyA=" + bodyIndexA + "; bodyB=" + bodyIndexB + " bodies=" + pBodies.size());
			return null;
		}

		pJointDef.bodyA = pBodies.get(bodyIndexA);
		pJointDef.bodyB = pBodies.get(bodyIndexB);

		Joint joint = pWorld.createJoint(pJointDef);

		return joint;
	}
}
