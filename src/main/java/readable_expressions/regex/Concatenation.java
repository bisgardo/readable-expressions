package readable_expressions.regex;

import readable_expressions.Regexes;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static readable_expressions.RegexSymbols.*;

public class Concatenation extends CompositeRegex {
	
	@Override
	public Concatenation followedBy(Regex regex) {
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		getRegexes().add(regex);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Regex regex : getRegexes()) {
			String pattern = regex.toString();
			if (regex.getCardinality() > 1 && regex instanceof Choice) {
				stringBuilder
						.append(GROUP_BEGIN_NON_CAPTURING)
						.append(pattern)
						.append(GROUP_END);
			} else {
				stringBuilder.append(pattern);
			}
		}
		
		return stringBuilder.toString();
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		final List<Regex> regexes = getRegexes();
		if (regexes.isEmpty()) {
			return Regexes.empty.iterateMatches();
		}
		
		// Construct iterator backwards such that they are linked in the correct order.
		return join(new Iterator<Regex>() {
//			private final List<Regex> regexes = getRegexes();
			private int index = regexes.size() - 1;
			
			//@Override
			public boolean hasNext() {
				return index >= 0;
			}
			
			//@Override
			public Regex next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return regexes.get(index--);
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	public static Iterator<String> join(Iterator<Regex> reversedIterator) {
		Iterator<String> joined = Regexes.empty.iterateMatches();
		while (reversedIterator.hasNext()) {
			Regex regex = reversedIterator.next();
			joined = join(joined, regex);
		}
		return joined;
	}
	
	private static Iterator<String> join(final Iterator<String> rights, final Regex leftsRegex) {
		if (rights == null) {
			throw new NullPointerException("rights");
		}
		if (leftsRegex == null) {
			throw new NullPointerException("leftsRegex");
		}
		
		// Shortcuts and initial extraction.
		if (!rights.hasNext()) {
			return rights;
		}
		final Iterator<String> firstLefts = leftsRegex.iterateMatches();
		if (firstLefts == null) {
			throw new NullPointerException("firstLefts");
		}
		if (!firstLefts.hasNext()) {
			return firstLefts;
		}
		
		final String firstRight = rights.next();
		if (firstRight == null) {
			throw new NullPointerException("firstRight");
		}
		
		return new Iterator<String>() {
			private String right = firstRight;
			private Iterator<String> lefts = firstLefts;
			
			//@Override
			public boolean hasNext() {
				if (!lefts.hasNext()) {
					if (!rights.hasNext()) {
						return false;
					}
					right = rights.next();
					if (right == null) {
						throw new NullPointerException("right");
					}
					lefts = leftsRegex.iterateMatches();
					if (lefts == null) {
						throw new NullPointerException("lefts");
					}
					// Originally returned `lefts.hasNext()` here, but since we know from the second shortcut above
					// that `lefts` is never empty, this always evaluates to `true`, which is returned anyway.
					// Note that this also means that if `hasNext()` is called two times in a row (without `next()` in
					// between), this if-statement is never executed in both calls.
				}
				return true;
			}
			
			//@Override
			public String next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				String left = lefts.next();
				if (left == null) {
					throw new NullPointerException("left");
				}
				return left.concat(right);
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
