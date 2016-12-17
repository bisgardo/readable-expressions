package readable_expressions.regex;

import readable_expressions.RegexSymbols;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingletonCharacter extends CharacterClassComponent {
	private final char character;
	
	public SingletonCharacter(char character) {
		this.character = character;
	}
	
	public char getCharacter() {
		return character;
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
	
	@Override
	public Iterator<Character> iterateMatches() {
		return new Iterator<Character>() {
			private boolean hasNext = true;
			
			//@Override
			public boolean hasNext() {
				return hasNext;
			}
			
			//@Override
			public Character next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				hasNext = false;
				return character;
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public String toString() {
		if (RegexSymbols.isSpecialCharacterInCharacterClass(character)) {
			return "\\" + character;
		}
		
		return String.valueOf(character);
	}
}
