package org.andengine.extension.rubeloader.factory;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public interface IBodyFactory {
	public Body produce(PhysicsWorld pWorld,final BodyDef pBodyDef, AutocastMap pMap);
}
