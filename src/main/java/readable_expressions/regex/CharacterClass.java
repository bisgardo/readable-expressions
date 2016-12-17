package readable_expressions.regex;

import readable_expressions.RegexSymbols;
import readable_expressions.Regexes;

import java.util.Iterator;

public class CharacterClass extends Regex {
	private final CharacterClassComponent component;
	private final boolean negated;
	
	private final CharacterClass intersected;
	
	public CharacterClass(CharacterClassComponent component, boolean negated, CharacterClass intersected) {
		if (component == null) {
			throw new NullPointerException("component");
		}
		this.component = component;
		this.negated = negated;
		this.intersected = intersected;
	}
	
	public CharacterClass and(CharacterClassComponent component) {
		return new CharacterClass(component, negated, this);
	}
	
	public CharacterClass and(char character) {
		return and(Regexes.string(character));
	}
	
	public CharacterClass andEitherOf(char... characters) {
		return and(Regexes.eitherOf(characters));
	}
	
	public CharacterClass except(CharacterClassComponent component) {
		return new CharacterClass(component, !negated, this);
	}
	
	public CharacterClass except(char character) {
		return except(Regexes.string(character));
	}
	
	public CharacterClass exceptEitherOf(char... characters) {
		return except(Regexes.eitherOf(characters));
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
	
	@Override
	public String toString() {
		int cardinality = component.getCardinality();
		if (cardinality == 0) {
			return "";
		}
		if (intersected == null && !negated && cardinality == 1) {
			// Character class only contains single character (group).
			if (component instanceof ShorthandCharacterClass) {
				return component.toString();
			}
			return RegexSymbols.escaped(component.toString(), false);
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append('[');
		if (negated) {
			stringBuilder.append('^');
		}
		stringBuilder.append(component);
		if (intersected != null) {
			String intersectedString = intersected.toString();
			if (intersectedString.length() > 0) {
				stringBuilder.append("&&").append(intersectedString);
			}
		}
		stringBuilder.append(']');
		return stringBuilder.toString();
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		if (negated) {
			// TODO Could filter iterator that iterates all character values except the negated ones.
			throw new UnsupportedOperationException("Not yet implemented for negated character class");
		}
		
		if (intersected != null) {
			throw new UnsupportedOperationException("Not yet implemented for intersected character class");
		}
		
		return new Iterator<String>() {
			private final Iterator<Character> iterator = component.iterateMatches();
			
			//@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			//@Override
			public String next() {
				Character character = iterator.next();
				if (character == null) {
					throw new NullPointerException("character");
				}
				return character.toString();
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
