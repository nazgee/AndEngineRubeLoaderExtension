package org.andengine.extension.rubeloader.parser;

import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;


public class ParserJointDef extends ParserDef<JointDef> {

	@Override
	protected JointDef doParse(AutocastMap pMap) {

		String type = pMap.getString("type", "");
		if (type.equals("revolute")) {
			RevoluteJointDef revoluteDef = new RevoluteJointDef();
			revoluteDef.collideConnected = pMap.getBool("collideConnected", false);
			revoluteDef.localAnchorA.set(pMap.getVector2("anchorA"));
			revoluteDef.localAnchorB.set(pMap.getVector2("anchorB"));
			revoluteDef.referenceAngle = pMap.getFloat("refAngle");
			revoluteDef.enableLimit = pMap.getBool("enableLimit", false);
			revoluteDef.lowerAngle = pMap.getFloat("lowerLimit");
			revoluteDef.upperAngle = pMap.getFloat("upperLimit");
			revoluteDef.enableMotor = pMap.getBool("enableMotor", false);
			revoluteDef.motorSpeed = pMap.getFloat("motorSpeed");
			revoluteDef.maxMotorTorque = pMap.getFloat("maxMotorTorque");
			return revoluteDef;
		} else if (type.equals("prismatic")) {
			PrismaticJointDef prismaticDef = new PrismaticJointDef();
			prismaticDef.collideConnected = pMap.getBool("collideConnected", false);
			prismaticDef.localAnchorA.set(pMap.getVector2("anchorA"));
			prismaticDef.localAnchorB.set(pMap.getVector2("anchorB"));
			if (pMap.has("localAxisA"))
				prismaticDef.localAxisA.set(pMap.getVector2("localAxisA"));
			else
				prismaticDef.localAxisA.set(pMap.getVector2("localAxis1"));
			prismaticDef.referenceAngle = pMap.getFloat("refAngle");
			prismaticDef.enableLimit = pMap.getBool("enableLimit");
			prismaticDef.lowerTranslation = pMap.getFloat("lowerLimit");
			prismaticDef.upperTranslation = pMap.getFloat("upperLimit");
			prismaticDef.enableMotor = pMap.getBool("enableMotor");
			prismaticDef.motorSpeed = pMap.getFloat("motorSpeed");
			prismaticDef.maxMotorForce = pMap.getFloat("maxMotorForce");
			return prismaticDef;
		} else if (type.equals("distance")) {
			DistanceJointDef distanceDef = new DistanceJointDef();
			distanceDef.collideConnected = pMap.getBool("collideConnected", false);
			distanceDef.localAnchorA.set(pMap.getVector2("anchorA"));
			distanceDef.localAnchorB.set(pMap.getVector2("anchorB"));
			distanceDef.length = pMap.getFloat("length");
			distanceDef.frequencyHz = pMap.getFloat("frequency");
			distanceDef.dampingRatio = pMap.getFloat("dampingRatio");
			return distanceDef;
		} else if (type.equals("pulley")) {
			PulleyJointDef pulleyDef = new PulleyJointDef();
			pulleyDef.collideConnected = pMap.getBool("collideConnected", false);
			pulleyDef.groundAnchorA.set(pMap.getVector2("groundAnchorA"));
			pulleyDef.groundAnchorB.set(pMap.getVector2("groundAnchorB"));
			pulleyDef.localAnchorA.set(pMap.getVector2("anchorA"));
			pulleyDef.localAnchorB.set(pMap.getVector2("anchorB"));
			pulleyDef.lengthA = pMap.getFloat("lengthA");
			pulleyDef.lengthB = pMap.getFloat("lengthB");
			pulleyDef.ratio = pMap.getFloat("ratio");
			return pulleyDef;
		} else if (type.equals("mouse")) {
			MouseJointDef mouseDef = new MouseJointDef();
			mouseDef.collideConnected = pMap.getBool("collideConnected", false);
			mouseDef.target.set(pMap.getVector2("anchorB"));// alter after
															// creating
															// joint
			mouseDef.maxForce = pMap.getFloat("maxForce");
			mouseDef.frequencyHz = pMap.getFloat("frequency");
			mouseDef.dampingRatio = pMap.getFloat("dampingRatio");
			return mouseDef;
		} else if ( type.equals("gear") ) {
			GearJointDef gearDef = new GearJointDef();
			gearDef.collideConnected = pMap.getBool("collideConnected", false);
			gearDef.ratio = pMap.getFloat("ratio");
		} else if ( type.equals("wheel") ) {
			WheelJointDef wheelDef = new WheelJointDef();
			wheelDef.collideConnected = pMap.getBool("collideConnected", false);
			wheelDef.localAnchorA.set( pMap.getVector2("anchorA") );
			wheelDef.localAnchorB.set( pMap.getVector2("anchorB") );
			wheelDef.localAxisA.set( pMap.getVector2("localAxisA") );
			wheelDef.enableMotor = pMap.getBool("enableMotor",false);
			wheelDef.motorSpeed = pMap.getFloat("motorSpeed");
			wheelDef.maxMotorTorque = pMap.getFloat("maxMotorTorque");
			wheelDef.frequencyHz = pMap.getFloat("springFrequency");
			wheelDef.dampingRatio = pMap.getFloat("springDampingRatio");
			return wheelDef;
		} else if (type.equals("weld")) {
			WeldJointDef weldDef = new WeldJointDef();
			weldDef.collideConnected = pMap.getBool("collideConnected", false);
			weldDef.localAnchorA.set(pMap.getVector2("anchorA"));
			weldDef.localAnchorB.set(pMap.getVector2("anchorB"));
			weldDef.referenceAngle = 0;
			return weldDef;
		} else if (type.equals("friction")) {
			FrictionJointDef frictionDef = new FrictionJointDef();
			frictionDef.collideConnected = pMap.getBool("collideConnected", false);
			frictionDef.localAnchorA.set(pMap.getVector2("anchorA"));
			frictionDef.localAnchorB.set(pMap.getVector2("anchorB"));
			frictionDef.maxForce = pMap.getFloat("maxForce");
			frictionDef.maxTorque = pMap.getFloat("maxTorque");
			return frictionDef;
		} else if ( type.equals("rope") ) { 
			RopeJointDef ropeDef = new RopeJointDef();
			ropeDef.collideConnected = pMap.getBool("collideConnected", false);
			ropeDef.localAnchorA.set( pMap.getVector2("anchorA") );
			ropeDef.localAnchorB.set( pMap.getVector2("anchorB") );
			ropeDef.maxLength = pMap.getFloat("maxLength");
			return ropeDef;
		}

		return null;
	}
}