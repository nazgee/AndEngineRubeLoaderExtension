package org.andengine.extension.rubeloader.factory;

import java.util.List;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;

public interface IJointsFactory {
	public Joint produce(PhysicsWorld pWorld, List<Body> pBodies, final JointDef pJointDef, final AutocastMap pMap);
	public Joint produceGearJoint(PhysicsWorld pWorld, List<Body> pBodies, List<Joint> pJoints, final JointDef pJointDef, final AutocastMap pMap);
}
