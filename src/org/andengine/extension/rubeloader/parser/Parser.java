package org.andengine.extension.rubeloader.parser;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.parser.ContainerFactory;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.andengine.extension.rubeloader.json.AutocastMap;

public abstract class Parser<T> {
	private final JSONParser mParser =new JSONParser(JSONParser.USE_INTEGER_STORAGE | JSONParser.ACCEPT_USELESS_COMMA);

	private T mInflatedResult;
	private AutocastMap mInflatedMap;

	ContainerFactory mContainerFactory = new ContainerFactory() {
		public AutocastMap createObjectContainer() {
			return new AutocastMap();
		}
		@Override
		public List<Object> createArrayContainer() {
			return new ArrayList<Object>();
		}
	};

	public T getInflatedResult() {
		return mInflatedResult;
	}

	public AutocastMap getInflatedMap() {
		return mInflatedMap;
	}

	protected abstract T doParse(AutocastMap pMap);

	public T parse(AutocastMap pMap) {
		T ret = doParse(pMap);
		this.mInflatedResult = ret;
		this.mInflatedMap = pMap;
		return ret;
	}

	public T parse(String pStringToParse) throws ParseException {
		return parse(loadMapFromString(pStringToParse));
	}

	protected AutocastMap loadMapFromString(String pStringToParse) throws ParseException {
		return (AutocastMap) mParser.parse(pStringToParse, mContainerFactory);
	}
}