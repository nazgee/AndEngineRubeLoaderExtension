package org.andengine.extension.rubeloader.factory;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;

public class BodyFactory implements IBodyFactory {
	@Override
	public Body produce(PhysicsWorld pWorld,final BodyDef pBodyDef, AutocastMap pMap) {
		Body body = pWorld.createBody(pBodyDef);

		MassData massData = new MassData();
		massData.mass = pMap.getFloat("massData-mass", 0);
		massData.center.set(pMap.getVector2("massData-center", new Vector2(0, 0)));
		massData.I = pMap.getFloat("massData-I", 0);
		body.setMassData(massData);

		return body;
	}
}
