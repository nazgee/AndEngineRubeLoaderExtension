package org.andengine.extension.rubeloader.def;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class ImageDef {
	public String name;
	public String file;
	public Body body;
	public Vector2 center;
	public float angle;
	public float heightWorldUnits;
	public float aspectScale;
	public boolean flip;
	public float opacity;
	public int filter; // 0 = nearest, 1 = linear
	public float renderOrder;

	public Vector2 corners[];

	public int numPoints;
	public float points[];
	public float uvCoords[];
	public int numIndices;
	public short indices[];

}