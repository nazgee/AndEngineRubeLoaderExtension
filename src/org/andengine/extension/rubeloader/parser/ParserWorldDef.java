package org.andengine.extension.rubeloader.parser;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.def.WorldDef;
import org.andengine.extension.rubeloader.json.AutocastMap;


public class ParserWorldDef extends ParserDef<WorldDef> {

	@Override
	protected WorldDef doParse(AutocastMap pMap) {
		WorldDef world = new WorldDef();

		world.positionIterations = pMap.getInt("positionIterations");
		world.velocityIterations = pMap.getInt("velocityIterations");
		world.allowSleep = pMap.getBool("allowSleep");
		world.autoClearForces = pMap.getBool("autoClearForces");
		world.warmStarting = pMap.getBool("warmStarting");
		world.continuousPhysics = pMap.getBool("continuousPhysics");
		world.gravity = pMap.getVector2("gravity");

		return world;
	}

	// XXX factory?
	public PhysicsWorld createWorld(final WorldDef pWorldDef) {
		PhysicsWorld ret = new PhysicsWorld(pWorldDef.gravity, pWorldDef.allowSleep, pWorldDef.positionIterations, pWorldDef.velocityIterations);
		ret.setAutoClearForces(pWorldDef.autoClearForces);
		ret.setWarmStarting(pWorldDef.warmStarting);
		ret.setContinuousPhysics(pWorldDef.continuousPhysics);
		return ret;
	}
}