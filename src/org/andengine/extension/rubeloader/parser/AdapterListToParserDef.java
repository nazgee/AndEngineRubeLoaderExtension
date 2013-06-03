package org.andengine.extension.rubeloader.parser;

import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.rubeloader.json.AutocastMap;




public class AdapterListToParserDef<T> extends AdapterListToParser<T> {
	private final ArrayList<ArrayList<AutocastMap>> mInflatedCustomProperties = new ArrayList<ArrayList<AutocastMap>>();

	public AdapterListToParserDef(String pKeyName) {
		super(pKeyName);
	}

	public AdapterListToParserDef(String pKeyName, ParserDef<T> pParser) {
		super(pKeyName, pParser);
	}

	public ParserDef<T> getInflator() {
		return (ParserDef<T>) super.getInflator();
	}

	public void setParser(ParserDef<T> pParser) {
		super.setInflator(pParser);
	}

	@Override
	protected void onParsingStarted(int pNumberOfObjectsToInflate) {
		super.onParsingStarted(pNumberOfObjectsToInflate);

		mInflatedCustomProperties.clear();
		mInflatedCustomProperties.ensureCapacity(pNumberOfObjectsToInflate);
	}

	@Override
	protected void onParsed(AutocastMap map, T result) {
		super.onParsed(map, result);

		mInflatedCustomProperties.add(getInflator().getInflatedCustomProperties());
	}

	@Override
	protected void onParsingFinished() {
		super.onParsingFinished();
	}

	public ArrayList<ArrayList<AutocastMap>> getInflatedCustomPropertiesList() {
		return mInflatedCustomProperties;
	}
}