package org.andengine.extension.rubeloader;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class CustomProperties {

	Map<String, Integer> m_customPropertyMap_int;
	Map<String, Double> m_customPropertyMap_float;
	Map<String, String> m_customPropertyMap_string;
	Map<String, Vector2> m_customPropertyMap_vec2;
	Map<String, Boolean> m_customPropertyMap_bool;
	
	public CustomProperties() {
		m_customPropertyMap_int = new HashMap<String, Integer>();
		m_customPropertyMap_float = new HashMap<String, Double>();
		m_customPropertyMap_string = new HashMap<String, String>();
		m_customPropertyMap_vec2 = new HashMap<String, Vector2>();
		m_customPropertyMap_bool = new HashMap<String, Boolean>();
	}
}