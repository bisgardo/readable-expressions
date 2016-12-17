package readable_expressions.regex;

import readable_expressions.RegexSymbols;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Escaped extends Regex {
	private final String string;
	
	public Escaped(String string) {
		if (string == null) {
			throw new NullPointerException("string");
		}
		this.string = string;
	}
	
	@Override
	public int getCardinality() {
		return string.length();
	}
	
	@Override
	public String toString() {
		return RegexSymbols.escaped(string, false);
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		return new Iterator<String>() {
			private String next = string;
			
			//@Override
			public boolean hasNext() {
				return next != null;
			}
			
			//@Override
			public String next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				String result = next;
				next = null;
				return result;
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
