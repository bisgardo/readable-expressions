package readable_expressions.regex;

import java.util.Iterator;

public class ShorthandCharacterClass extends CharacterClassComponent {
	private final String string;
	
	public ShorthandCharacterClass(String string) {
		if (string == null) {
			throw new NullPointerException("string");
		}
		this.string = string;
	}
	
	@Override
	public Iterator<Character> iterateMatches() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
}
