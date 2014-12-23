package org.andengine.extension.rubeloader.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class FixtureFactory implements IFixtureFactory {

	@Override
	public Fixture produce(FixtureDef fixtureDef, Body body) {
		return body.createFixture(fixtureDef);
	}

}
