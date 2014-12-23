package org.andengine.extension.rubeloader.parser;

import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.rubeloader.json.AutocastMap;

public class AdapterListToParser<T> extends Parser<ArrayList<T>> {
	private final String mKeyToInflate;
	private List<AutocastMap> mInflatedMaps;
	private Parser<T> mInflator;
	private IInflatingListener<T> mInflatingListener;


	public AdapterListToParser(String pKeyName) {
		this(pKeyName, null);
	}

	public AdapterListToParser(String pKeyToInflate, Parser<T> pInflator) {
		super();
		this.mKeyToInflate = pKeyToInflate;
		this.mInflator = pInflator;
	}

	@Override
	protected ArrayList<T> doParse(AutocastMap pMap, float tX, float tY) {
		this.mInflatedMaps = (List<AutocastMap>) pMap.get(this.getKeyToInflate());

		ArrayList<T> inflatedResults = null;

		if (mInflatedMaps != null) {
			int mapscount = mInflatedMaps.size();
			inflatedResults = new ArrayList<T>(mapscount);
			onParsingStarted(mapscount);
			for (AutocastMap map : this.mInflatedMaps) {
				T result = this.mInflator.parse(map, tX, tY);
				onParsed(map, result);
				inflatedResults.add(result);
			}
		} else {
			onParsingStarted(0);
		}
		onParsingFinished();
		return inflatedResults;
	}

	protected void onParsingFinished() {
		if (mInflatingListener != null) {
			mInflatingListener.onParsingFinished(this);
		}
	}

	protected void onParsed(AutocastMap map, T result) {
		if (mInflatingListener != null) {
			mInflatingListener.onParsed(this, result, map);
		}
	}

	protected void onParsingStarted(int pNumberOfObjectsToInflate) {
		if (mInflatingListener != null) {
			mInflatingListener.onParsingStarted(this);
		}
	}

	public List<AutocastMap> getInflatedMapsList() {
		return this.mInflatedMaps;
	}

	public Parser<T> getInflator() {
		return mInflator;
	}

	public void setInflator(Parser<T> pParser) {
		this.mInflator = pParser;
	}

	public IInflatingListener<T> getInflatingListener() {
		return this.mInflatingListener;
	}

	public void setParsingListener(IInflatingListener<T> pInflatingListener) {
		this.mInflatingListener = pInflatingListener;
	}

	public String getKeyToInflate() {
		return mKeyToInflate;
	}

	public static interface IInflatingListener<T> {
		void onParsed(AdapterListToParser<T> parser, T result, AutocastMap map);
		void onParsingStarted(AdapterListToParser<T> parser);
		void onParsingFinished(AdapterListToParser<T> parser);
	}
}