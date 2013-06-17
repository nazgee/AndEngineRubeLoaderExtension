package org.andengine.extension.rubeloader.factory;

import org.andengine.entity.IEntity;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.def.ImageDef;
import org.andengine.extension.rubeloader.json.AutocastMap;

public interface IEntityFactory {
	public IEntity produce(PhysicsWorld pWorld, ImageDef imageDef, AutocastMap pAutocastMap);
}
