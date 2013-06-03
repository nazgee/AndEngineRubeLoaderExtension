package org.andengine.extension.rubeloader.parser;

import org.andengine.extension.rubeloader.json.AutocastMap;

public class EmptyParserDef extends Parser<AutocastMap> {
	@Override
	protected AutocastMap doParse(AutocastMap pMap) {
		return pMap;
	}
}