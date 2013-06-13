package org.andengine.extension.rubeloader.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface IFixtureFactory {
	public Fixture produce(FixtureDef fixtureDef, Body body);
}
