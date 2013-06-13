package org.andengine.extension.rubeloader.factory;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.def.WorldDef;

public interface IPhysicsWorldFactory {
	PhysicsWorld populate(WorldDef pWorldDef);
}
