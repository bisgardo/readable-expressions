package readable_expressions.generate;

import readable_expressions.JavaRegexPair;
import readable_expressions.JavaRegexPair.List;

import static java.lang.String.format;
import static readable_expressions.Regexes.*;

public class TestGeneratorFunctions {
	
	public static List empty() {
		return new List();
	}
	
	public static JavaRegexPair group(JavaRegexPair pair) {
		return new JavaRegexPair(pair.java + ".asGroup()", pair.regex.asGroup());
	}
	
	public static List group(List pairs) {
		List list = empty();
		for (JavaRegexPair pair : pairs) {
			list.add(group(pair));
		}
		return list;
	}
	
	public static List group(char c) {
		return group(character(c));
	}
	
	public static List character(char c) {
		List list = empty();
		list.add(format("classOf(string('%c'))", c), classOf(string(c)));
		list.add(format("classOf('%c')", c), classOf(c));
		list.add(format("string(\"%c\")", c), string("" + c));
		return list;
	}
	
	public static List characterClass(char c1, char c2) {
		List list = empty();
		list.add(format("classOf(string('%c').or('%c')", c1, c2), classOf(string(c1).or(c2)));
		list.add(format("classOf('%c', '%c')", c1, c2), classOf(c1, c2));
		list.add(format("classOf(eitherOf('%c', '%c'))", c1, c2), classOf(eitherOf(c1, c2)));
		list.add(format("classOf(\"%c%c\")", c1, c2), classOf("" + c1 + c2));
		list.add(format("classOf(eitherOf(\"%c%c\"))", c1, c2), classOf(eitherOf("" + c1 + c2)));
		return list;
	}
	
	public static List concat(List lefts, List rights) {
		List list = empty();
		for (JavaRegexPair left : lefts) {
			for (JavaRegexPair right : rights) {
				list.add(format("%s.followedBy(%s)", left.java, right.java), left.regex.followedBy(right.regex));
			}
		}
		return list;
	}
	
	public static List concat(List lefts, char right) {
		List list = concat(lefts, character(right));
		for (JavaRegexPair left : lefts) {
//			list.add(format("%s.followedBy('%c')", left.java, right), left.regex.followedBy(right));
			list.add(format("%s.followedBy(string('%c'))", left.java, right), left.regex.followedBy(string(right)));
		}
		return list;
	}
	
	public static List concat(char left, List rights) {
		List list = concat(character(left), rights);
		for (JavaRegexPair right : rights) {
			list.add(format("string('%c').followedBy(%s)", left, right.java), string(left).followedBy(right.regex));
		}
		return list;
	}
	
	public static List concat(char left, char right) {
		List list = concat(character(left), character(right));
		for (JavaRegexPair l : character(left)) {
//			list.add(format("%s.followedBy('%c')", l.java, right), l.regex.followedBy(right));
			list.add(format("%s.followedBy(string('%c'))", l.java, right), l.regex.followedBy(string(right)));
		}
		for (JavaRegexPair r : character(right)) {
			list.add(format("string('%c').followedBy(%s)", left, r.java), string(left).followedBy(r.regex));
		}
//		list.add(format("string('%c', '%c')", left, right), string(left, right));
		list.add(format("string(\"%c%c\")", left, right), string("" + left + right));
		return list;
	}
	
	public static List concat(char left, char middle, char right) {
		List list = empty();
		// TODO The two commented out expressions don't work because they cause mutable objects to be reused. We don't
		//      want to use immutable objects only for performance and simplicity reasons. Instead, `List` objects
		//      should be wrapped in supplier functions such that only freshly created objects are mutated and then
		//      never reused later on.
//		list.addAll(concat(concat(character(left), middle), right));
		list.addAll(concat(character(left), concat(middle, right)));
//		list.addAll(concat(concat(left, middle), character(right)));
		
		
//		for (JavaRegexPair l : character(left)) {
//			list.add(format("%s.followedBy('%c').followedBy('%c')", l.java, middle, right), l.regex.followedBy(middle).followedBy(right));
//			for (JavaRegexPair r : character(right)) {
//				list.add(format("%s.followedBy('%c').followedBy(%s)", l.java, middle, r.java), l.regex.followedBy(middle).followedBy(r.regex));
//			}
//		}
//		list.add(format("string('%c', '%c', '%c')", left, middle, right), string(left, middle, right));
		list.add(format("string(\"%c%c%c\")", left, middle, right), string("" + left + middle + right));
		return list;
	}
	
	public static List or(List lefts, List rights) {
		List list = empty();
		for (JavaRegexPair left : lefts) {
			for (JavaRegexPair right : rights) {
				list.add(format("%s.or(%s)", left.java, right.java), left.regex.or(right.regex));
			}
		}
		return list;
	}
	
	public static List or(List lefts, char right) {
		List list = or(lefts, character(right));
		list.addAll(orChar(lefts, right));
		return list;
	}
	
	public static List or(char left, List rights) {
		List list = or(character(left), rights);
		list.addAll(charOr(left, rights));
		return list;
	}
	
	private static List charOr(char left, List rights) {
		List list = empty();
		for (JavaRegexPair right : rights) {
			list.add(format("string('%c').or(%s)", left, right.java), string(left).or(right.regex));
		}
		return list;
	}
	
	private static List orChar(List lefts, char right) {
		List list = empty();
		for (JavaRegexPair left : lefts) {
			list.add(format("%s.or('%c')", left.java, right), left.regex.or(right));
			list.add(format("%s.or(string('%c'))", left.java, right), left.regex.or(string(right)));
		}
		return list;
	}
	
	public static List or(char left, char right) {
		List list = or(character(left), character(right));
		list.addAll(orChar(character(left), right));
		list.addAll(charOr(left, character(right)));
		return list;
	}
	
	public static List or(char left, char middle, char right) {
		List list = empty();
		list.addAll(or(character(left), or(middle, right)));
		list.addAll(or(or(left, middle), character(right)));
		for (JavaRegexPair l : character(left)) {
			list.add(format("%s.or('%c').or('%c')", l.java, middle, right), l.regex.or(middle).or(right));
			for (JavaRegexPair r : character(right)) {
				list.add(format("%s.or('%c').or(%s)", l.java, middle, r.java), l.regex.or(middle).or(r.regex));
			}
		}
		return list;
	}
	
	public static List repeatedTimes(JavaRegexPair pair, int times) {
		List list = empty();
		list.add(format("%s.repeated(times(%d))", pair.java, times), pair.regex.repeated(times(times)));
//		list.add(pairOf(format("%s.repeatedTimes(%d)", pair.java, times), pair.regex.repeatedTimes(times)));
		list.addAll(repeatedBetweenTimes(pair, times, times));
		return list;
	}
	
	public static List repeatedBetweenTimes(JavaRegexPair pair, int fromTimes, int toTimes) {
		List list = empty();
		list.add(format("%s.repeated(betweenTimes(%d, %d))", pair.java, fromTimes, toTimes), pair.regex.repeated(betweenTimes(fromTimes, toTimes)));
//		list.add(format("%s.repeatedBetweenTimes(%d, %d)", pair.java, fromTimes, toTimes), pair.regex.repeatedBetweenTimes(fromTimes, toTimes));
		return list;
	}
}
