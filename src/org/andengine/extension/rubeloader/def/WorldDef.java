package org.andengine.extension.rubeloader.def;

import com.badlogic.gdx.math.Vector2;

public class WorldDef {
	public int positionIterations;
	public int velocityIterations;
	public boolean allowSleep;
	public boolean autoClearForces;
	public boolean warmStarting;
	public boolean continuousPhysics;
	public Vector2 gravity = new Vector2();
}
