package readable_expressions.regex;

import readable_expressions.Regexes;

import java.util.Iterator;

public class EmptyMatchSingletonRegex extends SingletonRegex {
	
	public EmptyMatchSingletonRegex(String pattern) {
		super(pattern);
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		return Regexes.empty.iterateMatches();
	}
}
