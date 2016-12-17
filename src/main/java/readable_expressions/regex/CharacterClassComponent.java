package readable_expressions.regex;

import readable_expressions.Regexes;

import java.util.Iterator;

public abstract class CharacterClassComponent extends RegexComponent {
	
	public abstract Iterator<Character> iterateMatches();
	
	@Override
	public CharacterClass toRegex() {
		return new CharacterClass(this, false, null);
	}
	
	public CharacterClass and(char character) {
		return and(Regexes.string(character));
	}
	
	public CharacterClass and(CharacterClassComponent component) {
		return new CharacterClass(component, false, new CharacterClass(this, false, null));
	}
	
	public CharacterClass except(char character) {
		return except(Regexes.string(character));
	}
	
	public CharacterClass except(CharacterClassComponent component) {
		return new CharacterClass(component, true, new CharacterClass(this, false, null));
	}
	
	public CharacterClassComponent or(char character) {
		return or(Regexes.string(character));
	}
	
	public CharacterClassComponent or(CharacterClassComponent component) {
		if (component == null) {
			throw new NullPointerException("component");
		}
		return new CharacterEnum().or(this).or(component);
	}
	
	protected abstract int getCardinality();
}
