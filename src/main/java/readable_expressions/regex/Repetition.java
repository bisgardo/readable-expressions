package readable_expressions.regex;

import readable_expressions.Regexes;
import readable_expressions.RegexSymbols;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static readable_expressions.RegexSymbols.*;

public class Repetition extends Regex {
	
	public static class Quantifier {
		
		public interface Times {
			
			int getFromTimes();
			
			int getToTimes();
			
			@Override
			String toString();
		}
		
		public enum Type {
			GREEDY(RegexSymbols.GREEDY), RELUCTANT(RegexSymbols.RELUCTANT), POSSESSIVE(RegexSymbols.POSSESSIVE);
			
			public final String symbol;
			
			Type(String symbol) {
				this.symbol = symbol;
			}
		}
		
		public final Times times;
		public final Type type;
		
		public Quantifier(Times times, Type type) {
			if (times == null) {
				throw new NullPointerException("times");
			}
			if (type == null) {
				throw new NullPointerException("type");
			}
			this.times = times;
			this.type = type;
		}
	}
	
	private final Regex regex;
	private final Quantifier.Times times;
	private final Quantifier.Type type;
	
	public Repetition(Regex regex, Quantifier.Times times, Quantifier.Type type) {
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		if (times == null) {
			throw new NullPointerException("times");
		}
		this.regex = regex;
		this.times = times;
		this.type = type;
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
	
	@Override
	public String toString() {
		int fromTimes = times.getFromTimes();
		int toTimes = times.getToTimes();
		if (fromTimes == 0 && toTimes == 0) {
			return "";
		}
		
		String pattern = regex.toString();
		if (fromTimes == 1 && toTimes == 1) {
			return pattern;
		}
		
		String timesPattern = times.toString();
		if (type != null) {
			timesPattern += type.symbol;
		}
		if (regex.getCardinality() > 1 || regex instanceof Repetition) {
			return GROUP_BEGIN_NON_CAPTURING + pattern + GROUP_END + timesPattern;
		}
		
		return pattern + timesPattern;
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		// Concatenate iterators that concatenate "from" times until "to" times.
		
		final int fromTimes = times.getFromTimes();
		final int toTimes = times.getToTimes();
		
//		if (fromTimes == toTimes) {
//			return repeat(regex, fromTimes);
//		}
		
		return Choice.join(new Iterator<Iterator<String>>() {
			private int times = fromTimes;
			
			//@Override
			public boolean hasNext() {
				return times <= toTimes;
			}
			
			//@Override
			public Iterator<String> next() {
				return repeat(regex, times++);
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	private static Iterator<String> repeat(final Regex regex, final int times) {
		if (times == 0) {
			return Regexes.empty.iterateMatches();
		}
		
		return Concatenation.join(new Iterator<Regex>() {
			private int index = 0;
			
			//@Override
			public boolean hasNext() {
				return index < times;
			}
			
			//@Override
			public Regex next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				index++;
				return regex;
			}
			
			//@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		});
	}
}
