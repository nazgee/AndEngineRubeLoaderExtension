package org.andengine.extension.rubeloader.factory;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.def.WorldDef;
import org.andengine.extension.rubeloader.json.AutocastMap;
import org.andengine.extension.rubeloader.parser.ParserWorldDef;

public class PhysicsWorldProvider implements IPhysicsWorldProvider{

	private PhysicsWorld mPhysicsWorld;

	public PhysicsWorldProvider(AutocastMap pMap, IPhysicsWorldFactory pPhysicsWorldFactory ) {

		ParserWorldDef worldParser = new ParserWorldDef();
		WorldDef worldDef = worldParser.parse(pMap);
		mPhysicsWorld = pPhysicsWorldFactory.populate(worldDef);
	}

	@Override
	public PhysicsWorld getWorld() {
		return mPhysicsWorld;
	}

	@Override
	public void dispose() {
		if (this.mPhysicsWorld != null) {
			this.mPhysicsWorld.clearPhysicsConnectors();
			this.mPhysicsWorld.setContactListener(null);
			this.mPhysicsWorld.dispose();
			this.mPhysicsWorld = null;
		}
	}
}
