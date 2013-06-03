package org.andengine.extension.rubeloader.parser;

import java.util.ArrayList;

import org.andengine.extension.rubeloader.json.AutocastMap;

public abstract class ParserDef<T> extends Parser<T> {
	private final AdapterListToParser<AutocastMap> mInflaterOfCustomProperties = new CustomPropertiesInflator("customProperties", new EmptyParserDef());
	private ArrayList<AutocastMap> mInflatedCustomProperties; 

	@Override
	public T parse(AutocastMap pMap) {
		T ret = super.parse(pMap);
		mInflatedCustomProperties = mInflaterOfCustomProperties.parse(pMap);

		return ret;
	}

	public ArrayList<AutocastMap> getInflatedCustomProperties() {
		return mInflatedCustomProperties;
	}

	private static final class CustomPropertiesInflator extends AdapterListToParser<AutocastMap> {
		private CustomPropertiesInflator(String pKeyToInflate, Parser<AutocastMap> pInflator) {
			super(pKeyToInflate, pInflator);
		}
	}
}