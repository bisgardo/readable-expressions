package readable_expressions.regex;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharacterRange extends CharacterClassComponent {
	private final char from;
	private final char to;
	
	public CharacterRange(char from, char to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public Iterator<Character> iterateMatches() {
		return new Iterator<Character>() {
			private char character = from;
			
			//@Override
			public boolean hasNext() {
				return character <= to;
			}
			
			//@Override
			public Character next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return character++;
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public String toString() {
		return String.valueOf(new char[]{from, '-', to});
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
}
