package readable_expressions.generate;

import readable_expressions.JavaRegexPair;
import readable_expressions.JavaRegexPair.List;
import readable_expressions.regex.Choice;
import readable_expressions.regex.Regex;

import static java.lang.String.format;
import static readable_expressions.Regexes.string;
import static readable_expressions.generate.TestGeneratorFunctions.*;

public class TestGenerators {
	
	/* VARIANTS OF "ab" */
	
	public static List ab() {
		return ab('a', 'b');
	}
	
	public static List $a$b() {
		return $a$b('a', 'b');
	}
	
	public static List a$b$() {
		return a$b$('a', 'b');
	}
	
	public static List $a$$b$() {
		return $a$$b$('a', 'b');
	}
	
	/* VARIANTS OF "a|b" */
	
	public static List a_or_b() {
		return a_or_b('a', 'b');
	}
	
	public static List $a$_or_b() {
		return or(group('a'), 'b');
	}
	
	public static List a_or_$b$() {
		return or('a', group('b'));
	}
	
	public static List $a$_or_$b$() {
		return or(group('a'), group('b'));
	}
	
	/* VARIANTS OF "ab|c" */
	
	// _|c
	
	public static List ab_or_c() {
		// TODO Pass "x" instead of null and ensure that the test passes correctly. Then add similar features to the
		//      other test generator functions.
		return ab_or_c('a', 'b', 'c', null);
	}
	
	public static List $a$b_or_c() {
		return or($a$b('a', 'b'), 'c');
	}
	
	public static List a$b$_or_c() {
		return or(a$b$('a', 'b'), 'c');
	}
	
	public static List $ab$_or_c() {
		return or(group(ab('a', 'b')), 'c');
	}
	
	public static List $$a$b$_or_c() {
		return or(group($a$b('a', 'b')), 'c');
	}
	
	public static List $a$b$$_or_c() {
		return or(group(a$b$('a', 'b')), 'c');
	}
	
	public static List $$a$$b$$_or_c() {
		return or(group($a$$b$('a', 'b')), 'c');
	}
	
	// _|(c)
	
	public static List ab_or_$c$() {
		return or(ab('a', 'b'), group('c'));
	}
	
	public static List $ab$_or_$c$() {
		return or(group(ab('a', 'b')), group('c'));
	}
	
	public static List $a$b_or_$c$() {
		return or($a$b('a', 'b'), group('c'));
	}
	
	public static List a$b$_or_$c$() {
		return or(a$b$('a', 'b'), group('c'));
	}
	
	public static List $$a$b$_or_$c$() {
		return or(group($a$b('a', 'b')), group('c'));
	}
	
	public static List $a$b$$_or_$c$() {
		return or(group(a$b$('a', 'b')), group('c'));
	}
	
	public static List $$a$$b$$_or_$c$() {
		return or(group($a$$b$('a', 'b')), group('c'));
	}
	
	/* VARIANTS OF "a|bc" */
	
	// a|_
	
	public static List a_or_bc() {
		return a_or_bc('a', 'b', 'c');
	}
	
	public static List a_or_$b$c() {
		return or('a', $a$b('b', 'c'));
	}
	
	public static List a_or_b$c$() {
		return or('a', a$b$('b', 'c'));
	}
	
	public static List a_or_$bc$() {
		return or('a', group(ab('b', 'c')));
	}
	
	public static List a_or_$b$$c$() {
		return or('a', $a$$b$('b', 'c'));
	}
	
	public static List a_or_$$b$c$() {
		return or('a', group($a$b('b', 'c')));
	}
	
	public static List a_or_$b$c$$() {
		return or('a', group(a$b$('b', 'c')));
	}
	
	public static List a_or_$$b$$c$$() {
		return or('a', group($a$$b$('b', 'c')));
	}
	
	// (a)|_
	
	public static List $a$_or_bc() {
		return or(group('a'), ab('b', 'c'));
	}
	
	public static List $a$_or_$a$b() {
		return or(group('a'), $a$b('b', 'c'));
	}
	
	public static List $a$_or_b$c$() {
		return or(group('a'), a$b$('b', 'c'));
	}
	
	public static List $a$_or_$bc$() {
		return or(group('a'), group(ab('b', 'c')));
	}
	
	public static List $a$_or_$b$$c$() {
		return or(group('a'), $a$$b$('b', 'c'));
	}
	
	public static List $a$_or_$$b$c$() {
		return or(group('a'), group($a$b('b', 'c')));
	}
	
	public static List $a$_or_$b$c$$() {
		return or(group('a'), group(a$b$('b', 'c')));
	}
	
	public static List $a$_or_$$b$$c$$() {
		return or(group('a'), group($a$$b$('b', 'c')));
	}
	
	/* VARIANTS OF "a|b|c" */
	
	public static List a_or_b_or_c() {
		return or('a', 'b', 'c');
	}
	
	public static List $a$_or_b_or_c() {
		List list = or(group('a'), or('b', 'c'));
		list.addAll(or(or(group('a'), 'b'), 'c'));
		return list;
	}
	
	/* VARIANTS OF "ab|cd" */
	
	public static List ab_or_cd() {
		return ab_or_cd('a', 'b', 'c', 'd');
	}
	
	/* VARIANTS OF "abc" */
	
	public static List abc() {
		return concat('a', 'b', 'c');
	}
	
	public static List $a$bc() {
		List list = concat(group('a'), concat('b', 'c'));
		list.addAll(concat(group('a'), character('b')));
		return list;
	}
	
	/* PARAMETERIZED FUNCTIONS */
	
	public static List ab(char a, char b) {
		return concat(a, b);
	}
	
	public static List $a$b(char a, char b) {
		// (a)b
		return concat(group(a), b);
	}
	
	public static List a$b$(char a, char b) {
		// a(b)
		return concat(a, group(b));
	}
	
	public static List $a$$b$(char a, char b) {
		// (a)(b)
		return concat(group(a), group(b));
	}
	
	public static List a_or_b(char a, char b) {
		// TODO Add variants with one more character added to either sides after calling `or` (i.e. the test should be unaffected by this).
		return or(a, b);
	}
	
	public static List ab_or_c(char a, char b, char c, String x) {
		// Cannot include variant where 'b' is added after calling 'or' because "a" must be a `Concatenation`.
		List list = or(ab(a, b), c);
		
		if (x != null) {
			// Add variants of `ab|c` where extra characters are appended after calling `or`.
			for (JavaRegexPair ab : ab(a, b)) {
				Choice ab_or_c = ab.regex.or(c);
				
				// `ab_or_c` should not be affected by this call.
				// TODO Test that `ab.regex` remains correct.
				ab.regex.followedBy(x);
				
				list.add(format("t=%s;r=t.or('%c');t.followedBy(\"%s\");r", ab.java, c, x), ab_or_c);
			}
			
			for (JavaRegexPair c_ : character(c)) {
				Regex ab = string("" + a + b);
				
				Choice ab_or_c = ab.or(c_.regex);
				
				// `ab_or_c` should not be affected by this call.
				// TODO Test that `c_.regex` remains correct.
				c_.regex.followedBy(x);
				
				list.add(format("t=string(\"%c%c\");r=t.or(%s);t.followedBy(\"%s\");r", a, b, c_.java, x), ab_or_c);
			}
		}
		
		return list;
	}
	
	public static List a_or_bc(char a, char b, char c) {
		return or(a, ab(b, c));
	}
	
	public static List ab_or_cd(char a, char b, char c, char d) {
		// TODO Include variants where 'b' and/or 'c' are added after calling 'or'.
		return or(ab(a, b), ab(c, d));
	}
}
