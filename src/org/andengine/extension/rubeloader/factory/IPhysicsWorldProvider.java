package org.andengine.extension.rubeloader.factory;

import org.andengine.extension.physics.box2d.PhysicsWorld;

public interface IPhysicsWorldProvider {
	PhysicsWorld getWorld();
	void dispose();
}
