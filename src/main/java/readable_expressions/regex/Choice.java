package readable_expressions.regex;

import org.jetbrains.annotations.NotNull;
import readable_expressions.Regexes;

import java.util.Iterator;
import java.util.List;

public class Choice extends CompositeRegex {
	
	@Override
	public Choice or(Regex regex) {
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
			if (stringBuilder.length() > 0) {
				stringBuilder.append('|');
			}
			String pattern = regex.toString();
			stringBuilder.append(pattern);
		}
		
		return stringBuilder.toString();
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		final List<Regex> regexes = getRegexes();
		if (regexes.isEmpty()) {
			return Regexes.empty.iterateMatches();
		}
		
		return join(new Iterator<Iterator<String>>() {
			private final Iterator<Regex> iterator = regexes.iterator();
			
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			public Iterator<String> next() {
				Regex regex = iterator.next();
				return regex.iterateMatches();
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	@NotNull
	public static <T> Iterator<T> join(final Iterator<? extends Iterator<T>> iterators) {
		return new Iterator<T>() {
			private Iterator<T> iterator = null;
			
			//@Override
			public boolean hasNext() {
				if (iterator != null && iterator.hasNext()) {
					return true;
				}
				while (iterators.hasNext()) {
					iterator = iterators.next();
					if (iterator == null) {
						throw new NullPointerException("iterator");
					}
					if (iterator.hasNext()) {
						return true;
					}
				}
				return false;
			}
			
			//@Override
			public T next() {
				T next = iterator.next();
				if (next == null) {
					throw new NullPointerException("next");
				}
				return next;
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
