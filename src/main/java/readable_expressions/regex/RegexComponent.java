package readable_expressions.regex;

import readable_expressions.Regexes;

public abstract class RegexComponent {
	
	// TODO Replace with visitor pattern (gives ability to setup optimizations in printer, output 'grinj-ox' expressions etc.).
	@Override
	public abstract String toString();
	
//	/**
//	 * (Simplest) Java expression that would construct this regex.
//	 * @return
//	 */
//	public abstract String toJava();
	
	protected abstract Regex toRegex();
	
	// TODO If `regex` is mutable (i.e. extends `CompositeRegex`), it should be copied in `followedBy` and `or`.
	
	public Regex followedBy(Regex regex) {
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		
		Regex self = toRegex();
		if (self.getCardinality() == 0) {
			return regex;
		}
		
		return new Concatenation()
				.followedBy(self)
				.followedBy(regex);
	}
	
	public Regex followedBy(String string) {
		return followedBy(Regexes.string(string));
	}
	
	public Regex followedBy(CharacterClassComponent component) {
		if (component == null) {
			throw new NullPointerException("component");
		}
		return followedBy(component.toRegex());
	}
	
//	public Regex followedBy(char character) {
//		return followedBy(Regexes.string(character));
//	}
	
	public Regex maybeFollowedBy(Regex regex) {
		return followedBy(Regexes.maybe(regex));
	}
	
	public Regex maybeFollowedBy(String string) {
		return maybeFollowedBy(Regexes.string(string));
	}
	
	public Regex maybeFollowedBy(CharacterClassComponent component) {
		if (component == null) {
			throw new NullPointerException("component");
		}
		return maybeFollowedBy(component.toRegex());
	}
	
//	public Regex maybeFollowedBy(char string) {
//		return maybeFollowedBy(Regexes.string(string));
//	}
	
	public Choice or(Regex regex) {
		Regex self = toRegex();
		Choice choice = new Choice();
		if (self.getCardinality() > 0) {
			choice = choice.or(self);
		}
		return choice.or(regex);
	}
	
	public Choice or(String string) {
		return or(Regexes.string(string));
	}
	
	public Repetition repeated(Repetition.Quantifier.Times times) {
		return repeated(times, Repetition.Quantifier.Type.GREEDY);
	}
	
	public Repetition repeated(Repetition.Quantifier quantifier) {
		return repeated(quantifier.times, quantifier.type);
	}
	
	public Repetition repeated(Repetition.Quantifier.Times times, Repetition.Quantifier.Type type) {
		return new Repetition(toRegex(), times, type);
	}
	
	public Group asGroup() {
		return asGroup(null);
	}
	
	public Group asGroup(String groupName) {
		return new Group(toRegex(), groupName);
	}
	
//	public static Lookaround withPositiveLookahead(Regex regex) {
//		if (regex == null) {
//			throw new NullPointerException("regex");
//		}
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
//	
//	public static Lookaround withNegativeLookahead(Regex regex) {
//		if (regex == null) {
//			throw new NullPointerException("regex");
//		}
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
//	
//	public static Lookaround withPositiveLookbehind(Regex regex) {
//		if (regex == null) {
//			throw new NullPointerException("regex");
//		}
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
//	
//	public static Lookaround withNegativeLookbehind(Regex regex) {
//		if (regex == null) {
//			throw new NullPointerException("regex");
//		}
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
}
