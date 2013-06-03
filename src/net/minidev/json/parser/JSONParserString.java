package net.minidev.json.parser;

/*
 *    Copyright 2011 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import static net.minidev.json.parser.ParseException.ERROR_UNEXPECTED_EOF;

/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
class JSONParserString extends JSONParserMemory {
	private String in;

	public JSONParserString(int permissiveMode) {
		super(permissiveMode);
	}

	/**
	 * use to return Primitive Type, or String, Or JsonObject or JsonArray
	 * generated by a ContainerFactory
	 */
	public Object parse(String in) throws ParseException {
		return parse(in, ContainerFactory.FACTORY_SIMPLE, ContentHandlerDumy.HANDLER);
	}

	/**
	 * use to return Primitive Type, or String, Or JsonObject or JsonArray
	 * generated by a ContainerFactory
	 */
	public Object parse(String in, ContainerFactory containerFactory) throws ParseException {
		return parse(in, containerFactory, ContentHandlerDumy.HANDLER);
	}

	/**
	 * use to return Primitive Type, or String, Or JsonObject or JsonArray
	 * generated by a ContainerFactory
	 */
	public Object parse(String in, ContainerFactory containerFactory, ContentHandler handler) throws ParseException {
		this.in = in;
		this.len = in.length();
		return parse(containerFactory, handler);
	}

	protected void extractString(int beginIndex, int endIndex) {
		xs = in.substring(beginIndex, endIndex);
	}

	protected int indexOf(char c, int pos) {
		return in.indexOf(c, pos);
	}

	protected void read() {
		if (++pos >= len)
			this.c = EOI;
		else
			this.c = in.charAt(pos);
	}

	/**
	 * Same as read() in memory parssing
	 */
	protected void readS() {
		if (++pos >= len)
			this.c = EOI;
		else
			this.c = in.charAt(pos);
	}
	/**
	 * read data can not be EOI
	 */
	protected void readNoEnd() throws ParseException {
		if (++pos >= len) {
			this.c = EOI;
			throw new ParseException(pos - 1, ERROR_UNEXPECTED_EOF, "EOF");
		} else
			this.c = in.charAt(pos);
	}
}
