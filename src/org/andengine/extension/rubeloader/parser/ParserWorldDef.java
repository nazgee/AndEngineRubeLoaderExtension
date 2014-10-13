package org.andengine.extension.rubeloader.parser;

import org.andengine.extension.rubeloader.def.WorldDef;
import org.andengine.extension.rubeloader.json.AutocastMap;


public class ParserWorldDef extends ParserDef<WorldDef> {

	@Override
	protected WorldDef doParse(AutocastMap pMap, float tX, float tY) {
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
}