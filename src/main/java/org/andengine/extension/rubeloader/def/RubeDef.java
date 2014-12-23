package org.andengine.extension.rubeloader.def;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.andengine.entity.IEntity;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.rubeloader.factory.IPhysicsWorldProvider;
import org.andengine.util.adt.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;

public class RubeDef {
	public IPhysicsWorldProvider worldProvider;

	public RubeDefDataPrimitivesList primitives = new RubeDefDataPrimitivesList();
	public RubeDefDataIndexesMap indexes = new RubeDefDataIndexesMap();
	public RubeDefDataNamesMap names = new RubeDefDataNamesMap();
	public RubeDefDataCustomizedSet customs = new RubeDefDataCustomizedSet();
	public Map<Body, LinkedList<IEntity>> mBodyToEntitiesMap = new HashMap<Body, LinkedList<IEntity>>();
	public Map<IEntity, ImageDef> mEntityToDef = new HashMap<IEntity, ImageDef>();

	// This maps an item (Body, Fixture etc) to a set of custom properties.
	// Use null for world properties.
	public Map<Object, RubeDefDataCustomProperties> m_RubeDefDataCustomPropertiesMap = new HashMap<Object, RubeDef.RubeDefDataCustomProperties>();

	public RubeDef() {
		this(null);
	}

	/**
	 * Use this constructor to have multiple RUBE files loaded into single PhysicsWorld
	 * @param pWorld - instance of {@link PhysicsWorld} to be reused when loading data from RUBE file
	 */
	public RubeDef(IPhysicsWorldProvider pPhysicsWorldProvider) {
		worldProvider = pPhysicsWorldProvider;
	}

	public class RubeDefDataCustomProperties {
		Map<String, Integer> m_customPropertyMap_int;
		Map<String, Float> m_customPropertyMap_float;
		Map<String, String> m_customPropertyMap_string;
		Map<String, Vector2> m_customPropertyMap_vec2;
		Map<String, Boolean> m_customPropertyMap_bool;
		Map<String, Color> m_customPropertyMap_color;

		public RubeDefDataCustomProperties() {
			m_customPropertyMap_int = new HashMap<String, Integer>();
			m_customPropertyMap_float = new HashMap<String, Float>();
			m_customPropertyMap_string = new HashMap<String, String>();
			m_customPropertyMap_vec2 = new HashMap<String, Vector2>();
			m_customPropertyMap_bool = new HashMap<String, Boolean>();
			m_customPropertyMap_color = new HashMap<String, Color>();
		}
	}

	public static class RubeDefDataPrimitivesList {
		public Vector<Body> bodies = new Vector<Body>();
		public Vector<Joint> joints = new Vector<Joint>();
		public Vector<IEntity> images = new Vector<IEntity>();

		public void assureCapacities(int bodycount, int jointcount, int imagecount) {
			bodies.ensureCapacity(bodycount);
			joints.ensureCapacity(jointcount);
			images.ensureCapacity(imagecount);
		}
	}

	public static class RubeDefDataCustomizedSet {
		public Set<Body> m_bodiesWithRubeDefDataCustomProperties = new HashSet<Body>();
		public Set<Fixture> m_fixturesWithRubeDefDataCustomProperties = new HashSet<Fixture>();
		public Set<Joint> m_jointsWithRubeDefDataCustomProperties = new HashSet<Joint>();
		public Set<IEntity> m_imagesWithRubeDefDataCustomProperties = new HashSet<IEntity>();

		public void register(Object item) {
			if (item instanceof Body) {
				m_bodiesWithRubeDefDataCustomProperties.add((Body) item);
			} else if (item instanceof Fixture) {
				m_fixturesWithRubeDefDataCustomProperties.add((Fixture) item);
			} else if (item instanceof Joint) {
				m_jointsWithRubeDefDataCustomProperties.add((Joint) item);
			} else if (item instanceof IEntity) {
				m_imagesWithRubeDefDataCustomProperties.add((IEntity) item);
			}
		}
	}

	public static class RubeDefDataNamesMap {
		public Map<Body, String> bodyToNameMap = new HashMap<Body, String>();
		public Map<Fixture, String> fixtureToNameMap = new HashMap<Fixture, String>();
		public Map<Joint, String> jointToNameMap = new HashMap<Joint, String>();
		public Map<IEntity, String> imageToNameMap = new HashMap<IEntity, String>();
	}
	public static class RubeDefDataIndexesMap {
		public Map<Integer, Body> indexToBodyMap = new HashMap<Integer, Body>();
		public Map<Body, Integer> bodyToIndexMap = new HashMap<Body, Integer>();
		public Map<Joint, Integer> jointToIndexMap = new HashMap<Joint, Integer>();
	}
	public void registerBody(Body body, int index, String pName) {
		if (body != null) {
			primitives.bodies.add(index, body);
			names.bodyToNameMap.put(body, pName);
			indexes.bodyToIndexMap.put(body, index);
			indexes.indexToBodyMap.put(index, body);
		}
	}
	public void registerFixture(Fixture fixture, String pName) {
		if (fixture != null) {
			names.fixtureToNameMap.put(fixture, pName);
		}
	}
	public void registerJoint(Joint joint, int index, String pName) {
		if (joint != null) {
			primitives.joints.add(index, joint);
			names.jointToNameMap.put(joint, pName);
			indexes.jointToIndexMap.put(joint, index);
		}
	}
	public void registerEntity(IEntity image, int index, String pName, ImageDef imageDef) {
		if (image != null) {
			primitives.images.add(index, image);
			names.imageToNameMap.put(image, pName);
			mEntityToDef.put(image, imageDef);
		}
	}

	public void mapEntityToBody(IEntity pEntity, Body body) {
		LinkedList<IEntity> entities = mBodyToEntitiesMap.get(body);
		if (entities == null) {
			entities = new LinkedList<IEntity>();
			mBodyToEntitiesMap.put(body, entities);
		}
		entities.add(pEntity);
	}

	public LinkedList<IEntity> getImages(Body pBody) {
		return mBodyToEntitiesMap.get(pBody);
	}




































	public Body[] getBodiesByName(String name) {
		Set<Body> keys = new HashSet<Body>();
		for (Entry<Body, String> entry : names.bodyToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys.toArray(new Body[0]);
	}

	public Fixture[] getFixturesByName(String name) {
		Set<Fixture> keys = new HashSet<Fixture>();
		for (Entry<Fixture, String> entry : names.fixtureToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys.toArray(new Fixture[0]);
	}

	public Joint[] getJointsByName(String name) {
		Set<Joint> keys = new HashSet<Joint>();
		for (Entry<Joint, String> entry : names.jointToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys.toArray(new Joint[0]);
	}

	public IEntity[] getImagesByName(String name) {
		Set<IEntity> keys = new HashSet<IEntity>();
		for (Entry<IEntity, String> entry : names.imageToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys.toArray(new IEntity[0]);
	}

	public Vector<IEntity> getAllImages() {
		return primitives.images;
	}

	public Body getBodyByName(String name) {
		for (Entry<Body, String> entry : names.bodyToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public Fixture getFixtureByName(String name) {
		for (Entry<Fixture, String> entry : names.fixtureToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public Joint getJointByName(String name) {
		for (Entry<Joint, String> entry : names.jointToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public IEntity getImageByName(String name) {
		for (Entry<IEntity, String> entry : names.imageToNameMap.entrySet()) {
			if (name.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public RubeDefDataCustomProperties getRubeDefDataCustomPropertiesForItem(Object item, boolean createIfNotExisting) {
		
		if (m_RubeDefDataCustomPropertiesMap.containsKey(item))
			return m_RubeDefDataCustomPropertiesMap.get(item);

		if (!createIfNotExisting)
			return null;

		RubeDefDataCustomProperties props = new RubeDefDataCustomProperties();
		m_RubeDefDataCustomPropertiesMap.put(item, props);

		return props;
	}

	// setCustomXXX

	public void setCustomInt(Object item, String propertyName, int val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_int.put(propertyName, val);
	}

	public void setCustomFloat(Object item, String propertyName, float val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_float.put(propertyName, val);
	}

	public void setCustomString(Object item, String propertyName, String val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_string.put(propertyName, val);
	}

	public void setCustomVector(Object item, String propertyName, Vector2 val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_vec2.put(propertyName, val);
	}

	public void setCustomBool(Object item, String propertyName, boolean val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_bool.put(propertyName, val);
	}

	public void setCustomColor(Object item, String propertyName, Color val) {
		customs.register(item);
		getRubeDefDataCustomPropertiesForItem(item, true).m_customPropertyMap_color.put(propertyName, val);
	}

	public boolean hasCustomInt(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_int.containsKey(propertyName);
	}

	public boolean hasCustomFloat(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_float.containsKey(propertyName);
	}

	public boolean hasCustomString(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_string.containsKey(propertyName);
	}

	public boolean hasCustomVector(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_vec2.containsKey(propertyName);
	}

	public boolean hasCustomBool(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_bool.containsKey(propertyName);
	}

	public boolean hasCustomColor(Object item, String propertyName) {
		return getRubeDefDataCustomPropertiesForItem(item, false) != null &&
				getRubeDefDataCustomPropertiesForItem(item, false).m_customPropertyMap_color.containsKey(propertyName);
	}

	// getCustomXXX

	public int getCustomInt(Object item, String propertyName, int defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_int.containsKey(propertyName))
			return (Integer) props.m_customPropertyMap_int.get(propertyName);
		return defaultVal;
	}

	public float getCustomFloat(Object item, String propertyName, float defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_float.containsKey(propertyName))
			return (Float)props.m_customPropertyMap_float.get(propertyName);
		return defaultVal;
	}

	public String getCustomString(Object item, String propertyName, String defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_string.containsKey(propertyName))
			return (String) props.m_customPropertyMap_string.get(propertyName);
		return defaultVal;
	}

	public Vector2 getCustomVector(Object item, String propertyName, Vector2 defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_vec2.containsKey(propertyName))
			return (Vector2) props.m_customPropertyMap_vec2.get(propertyName);
		return defaultVal;
	}

	public boolean getCustomBool(Object item, String propertyName, boolean defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_bool.containsKey(propertyName))
			return (Boolean) props.m_customPropertyMap_bool.get(propertyName);
		return defaultVal;
	}

	public Color getCustomColor(Object item, String propertyName, Color defaultVal) {
		RubeDefDataCustomProperties props = getRubeDefDataCustomPropertiesForItem(item, false);
		if (null == props)
			return defaultVal;
		if (props.m_customPropertyMap_color.containsKey(propertyName))
			return props.m_customPropertyMap_color.get(propertyName);
		return defaultVal;
	}

	// get by custom property value (vector version, body)
	public int getBodiesByCustomInt(String propertyName, int valueToMatch, Vector<Body> items) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getBodiesByCustomFloat(String propertyName, float valueToMatch, Vector<Body> items) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getBodiesByCustomString(String propertyName, String valueToMatch, Vector<Body> items) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch) )
				items.add(item);
		}
		return items.size();
	}

	public int getBodiesByCustomVector(String propertyName, Vector2 valueToMatch, Vector<Body> items) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getBodiesByCustomBool(String propertyName, boolean valueToMatch, Vector<Body> items) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	// get by custom property value (single version, body)
	Body getBodyByCustomInt(String propertyName, int valueToMatch) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Body getBodyByCustomFloat(String propertyName, float valueToMatch) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Body getBodyByCustomString(String propertyName, String valueToMatch) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				return item;
		}
		return null;
	}

	Body getBodyByCustomVector(String propertyName, Vector2 valueToMatch) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				return item;
		}
		return null;
	}

	Body getBodyByCustomBool(String propertyName, boolean valueToMatch) {
		Iterator<Body> iterator = customs.m_bodiesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Body item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				return item;
		}
		return null;
	}

	// get by custom property value (vector version, Fixture)
	public int getFixturesByCustomInt(String propertyName, int valueToMatch, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesWithCustomInt(String propertyName, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomInt(item, propertyName))
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesByCustomFloat(String propertyName, float valueToMatch, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesWithCustomFloat(String propertyName, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomFloat(item, propertyName))
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesByCustomString(String propertyName, String valueToMatch, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesWithCustomString(String propertyName, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomString(item, propertyName))
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesByCustomVector(String propertyName, Vector2 valueToMatch, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesWithCustomVector(String propertyName, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomVector(item, propertyName))
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesByCustomBool(String propertyName, boolean valueToMatch, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getFixturesWithCustomBool(String propertyName, Vector<Fixture> items) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomBool(item, propertyName))
				items.add(item);
		}
		return items.size();
	}
	// get by custom property value (single version, Fixture)
	Fixture getFixtureByCustomInt(String propertyName, int valueToMatch) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Fixture getFixtureByCustomFloat(String propertyName, float valueToMatch) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Fixture getFixtureByCustomString(String propertyName, String valueToMatch) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				return item;
		}
		return null;
	}

	Fixture getFixtureByCustomVector(String propertyName, Vector2 valueToMatch) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				return item;
		}
		return null;
	}

	Fixture getFixtureByCustomBool(String propertyName, boolean valueToMatch) {
		Iterator<Fixture> iterator = customs.m_fixturesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Fixture item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				return item;
		}
		return null;
	}

	// get by custom property value (vector version, Joint)
	public int getJointsByCustomInt(String propertyName, int valueToMatch, Vector<Joint> items) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getJointsByCustomFloat(String propertyName, float valueToMatch, Vector<Joint> items) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getJointsByCustomString(String propertyName, String valueToMatch, Vector<Joint> items) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				items.add(item);
		}
		return items.size();
	}

	public int getJointsByCustomVector(String propertyName, Vector2 valueToMatch, Vector<Joint> items) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getJointsByCustomBool(String propertyName, boolean valueToMatch, Vector<Joint> items) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	// get by custom property value (single version, Joint)
	Joint getJointByCustomInt(String propertyName, int valueToMatch) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Joint getJointByCustomFloat(String propertyName, float valueToMatch) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	Joint getJointByCustomString(String propertyName, String valueToMatch) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				return item;
		}
		return null;
	}

	Joint getJointByCustomVector(String propertyName, Vector2 valueToMatch) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				return item;
		}
		return null;
	}

	Joint getJointByCustomBool(String propertyName, boolean valueToMatch) {
		Iterator<Joint> iterator = customs.m_jointsWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			Joint item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				return item;
		}
		return null;
	}

	// get by custom property value (vector version, Image)
	public int getImagesByCustomInt(String propertyName, int valueToMatch, Vector<IEntity> items) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getImagesByCustomFloat(String propertyName, float valueToMatch, Vector<IEntity> items) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getImagesByCustomString(String propertyName, String valueToMatch, Vector<IEntity> items) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				items.add(item);
		}
		return items.size();
	}

	public int getImagesByCustomVector(String propertyName, Vector2 valueToMatch, Vector<IEntity> items) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	public int getImagesByCustomBool(String propertyName, boolean valueToMatch, Vector<IEntity> items) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				items.add(item);
		}
		return items.size();
	}

	// get by custom property value (single version, Image)
	IEntity getImageByCustomInt(String propertyName, int valueToMatch) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomInt(item, propertyName) && getCustomInt( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	IEntity getImageByCustomFloat(String propertyName, float valueToMatch) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomFloat(item, propertyName) && getCustomFloat( item, propertyName, 0 ) == valueToMatch)
				return item;
		}
		return null;
	}

	IEntity getImageByCustomString(String propertyName, String valueToMatch) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomString(item, propertyName) && getCustomString( item, propertyName, new String() ).equals(valueToMatch))
				return item;
		}
		return null;
	}

	IEntity getImageByCustomVector(String propertyName, Vector2 valueToMatch) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomVector(item, propertyName) && getCustomVector( item, propertyName, new Vector2(0,0) ) == valueToMatch)
				return item;
		}
		return null;
	}

	IEntity getImageByCustomBool(String propertyName, boolean valueToMatch) {
		Iterator<IEntity> iterator = customs.m_imagesWithRubeDefDataCustomProperties.iterator();
		while (iterator.hasNext()) {
			IEntity item = iterator.next();
			if (hasCustomBool(item, propertyName) && getCustomBool( item, propertyName, false ) == valueToMatch)
				return item;
		}
		return null;
	}

}
