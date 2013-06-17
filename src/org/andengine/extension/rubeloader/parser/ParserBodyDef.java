package org.andengine.extension.rubeloader.parser;

import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class ParserBodyDef extends ParserDef<BodyDef> {

	@Override
	protected BodyDef doParse(AutocastMap pMap) {
		BodyDef bodyDef = new BodyDef();
		switch (pMap.getInt("type")) {
		case 0:
			bodyDef.type = BodyType.StaticBody;
			break;
		case 1:
			bodyDef.type = BodyType.KinematicBody;
			break;
		case 2:
			bodyDef.type = BodyType.DynamicBody;
			break;
		}
		bodyDef.position.set(pMap.getVector2("position"));
		bodyDef.angle = pMap.getFloat("angle");
		bodyDef.linearVelocity.set(pMap.getVector2("linearVelocity", new Vector2(0, 0)));
		bodyDef.angularVelocity = pMap.getFloat("angularVelocity", 0);
		bodyDef.linearDamping = pMap.getFloat("linearDamping", 0);
		bodyDef.angularDamping = pMap.getFloat("angularDamping", 0);
		bodyDef.gravityScale = pMap.getFloat("gravityScale", 1);

		bodyDef.allowSleep = pMap.getBool("allowSleep", true);
		bodyDef.awake = pMap.getBool("awake", false);
		bodyDef.fixedRotation = pMap.getBool("fixedRotation", false);
		bodyDef.bullet = pMap.getBool("bullet", false);
		bodyDef.active = pMap.getBool("active", true);

		return bodyDef;
	}
}