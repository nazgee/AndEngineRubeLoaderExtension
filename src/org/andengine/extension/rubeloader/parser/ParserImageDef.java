package org.andengine.extension.rubeloader.parser;

import java.util.Vector;

import org.andengine.extension.rubeloader.def.ImageDef;
import org.andengine.extension.rubeloader.json.AutocastMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;


public class ParserImageDef extends ParserDef<ImageDef> {

	@Override
	protected ImageDef doParse(AutocastMap pMap) {
		ImageDef img = new ImageDef();

		String imageName = pMap.getString("name", "");
		if (!imageName.equals("")) {
			img.name = imageName;
		}

		String fileName = pMap.getString("file", "");
		if (!fileName.equals(""))
			img.file = fileName;

		img.center = pMap.getVector2("center");
		img.angle = pMap.getFloat("angle", 0);
		img.scale = pMap.getFloat("scale");
		img.opacity = pMap.getFloat("opacity");
		img.renderOrder = pMap.getFloat("renderOrder", 0);

		img.flip = pMap.getBool("flip", false);

		img.filter = pMap.getInt("filter", 1);

		img.corners = pMap.getVector2Array("corners");

//		if (img.file.contains("bg-mountains-3")) {
//			System.out.println("bg-mountains-3 = " + pMap);
//			System.out.println("bg-mountains-3.get(customProperties) = " + pMap.get("customProperties"));
//		}

//		JSONArray vertexPointerArray = imageValue.optJSONArray("glVertexPointer");
//		JSONArray texCoordArray = imageValue.optJSONArray("glVertexPointer");
//		if (null != vertexPointerArray && null != texCoordArray && vertexPointerArray.length() == texCoordArray.length()) {
//			int numFloats = vertexPointerArray.length();
//			img.numPoints = numFloats / 2;
//			img.points = new float[numFloats];
//			img.uvCoords = new float[numFloats];
//			for (int i = 0; i < numFloats; i++) {
//				img.points[i] = jsonToFloat("glVertexPointer", imageValue, i);
//				img.uvCoords[i] = jsonToFloat("glTexCoordPointer", imageValue, i);
//			}
//		}
//
//		JSONArray drawElementsArray = imageValue.optJSONArray("glDrawElements");
//		if (null != drawElementsArray) {
//			img.numIndices = drawElementsArray.length();
//			img.indices = new short[img.numIndices];
//			for (int i = 0; i < img.numIndices; i++)
//				img.indices[i] = (short) drawElementsArray.getInt(i);
//		}

		return img;
	}

	public void bindWithBody(ImageDef imageDef, AutocastMap pMap, Vector<Body> pBody) {
		int bodyIndex = pMap.getInt("body", -1);
		if (-1 != bodyIndex)
			imageDef.body = pBody.get(bodyIndex);
	}
}