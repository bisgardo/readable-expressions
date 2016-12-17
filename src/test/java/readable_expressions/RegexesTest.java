package readable_expressions;

import org.junit.Test;
import readable_expressions.generate.TestGenerators;
import readable_expressions.regex.Regex;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static readable_expressions.RegexMatcher.pattern;
import static readable_expressions.generate.TestGeneratorFunctions.*;

public class RegexesTest {
	
	private static final Set<String> PATTERNS = new HashSet<String>();
	
	@SuppressWarnings("LanguageMismatch")
	private static void testRoundTrip(String pattern) {
		if (!PATTERNS.add(pattern)) {
			throw new IllegalStateException(String.format("Duplicate pattern: '%s'", pattern));
		}
		
		String java = String.format("literal(\"%s\")", pattern);
		Regex regex = Regexes.literal(pattern);
		JavaRegexPair pair = new JavaRegexPair(java, regex);
		assertThat(pair, is(pattern(pattern)));
	}
	
	@Test
	public void a() {
		for (JavaRegexPair a : character('a')) {
			assertThat(a, is(pattern("a")));
			assertThat(group(a), is(pattern("(a)")));
			
			// TODO Test that pattern can be parsed back to expression...
		}
		testRoundTrip("a");
		testRoundTrip("(a)");
		
		for (JavaRegexPair p : character('(')) {
			assertThat(p, is(pattern("\\(")));
			assertThat(group(p), is(pattern("(\\()")));
		}
		testRoundTrip("\\(");
		testRoundTrip("(\\()");
	}
	
	@Test
	public void ab() {
		for (JavaRegexPair ab : TestGenerators.ab()) {
			assertThat(ab, is(pattern("ab")));
			assertThat(group(ab), is(pattern("(ab)")));
		}
		testRoundTrip("ab");
		testRoundTrip("(ab)");
		
		for (JavaRegexPair ab : TestGenerators.a$b$()) {
			assertThat(ab, is(pattern("a(b)")));
			assertThat(group(ab), is(pattern("(a(b))")));
		}
		testRoundTrip("a(b)");
		testRoundTrip("(a(b))");
		
		for (JavaRegexPair ab : TestGenerators.$a$b()) {
			assertThat(ab, is(pattern("(a)b")));
			assertThat(group(ab), is(pattern("((a)b)")));
		}
		testRoundTrip("(a)b");
		testRoundTrip("((a)b)");
		
		for (JavaRegexPair ab : TestGenerators.$a$$b$()) {
			assertThat(ab, is(pattern("(a)(b)")));
			assertThat(group(ab), is(pattern("((a)(b))")));
		}
		testRoundTrip("(a)(b)");
		testRoundTrip("((a)(b))");
	}
	
	@Test
	public void abc() {
		for (JavaRegexPair abc : TestGenerators.abc()) {
			assertThat(abc, is(pattern("abc")));
		}
		testRoundTrip("abc");
		
		
		testRoundTrip("(a)bc");
		
		
		testRoundTrip("a(b)c");
		
		
		testRoundTrip("ab(c)");
		
		
		testRoundTrip("(ab)c");
		
		
		testRoundTrip("a(bc)");
		
		
		testRoundTrip("(a)(bc)");
		
		
		testRoundTrip("(ab)(c)");
		
		
		testRoundTrip("(a(b)c)");
		testRoundTrip("(a(bc))");
		testRoundTrip("((ab)c)");
		testRoundTrip("((a(b))c)");
	}
	
	@Test
	public void ab_class() {
		for (JavaRegexPair ab_class : characterClass('a', 'b')) {
			assertThat(ab_class, is(pattern("[ab]")));
			assertThat(group(ab_class), is(pattern("([ab])")));
		}
		// TODO Support...
//		testRoundTrip("[ab]");
//		testRoundTrip("([ab])");
	}
	
	@Test
	public void a_or_b() {
		for (JavaRegexPair a_or_b : TestGenerators.a_or_b()) {
			assertThat(a_or_b, is(pattern("a|b")));
			assertThat(group(a_or_b), is(pattern("(a|b)")));
		}
		testRoundTrip("a|b");
		testRoundTrip("(a|b)");
		
		for (JavaRegexPair $a$_or_b : TestGenerators.$a$_or_b()) {
			assertThat($a$_or_b, is(pattern("(a)|b")));
			assertThat(group($a$_or_b), is(pattern("((a)|b)")));
		}
		testRoundTrip("(a)|b");
		testRoundTrip("((a)|b)");
		
		for (JavaRegexPair a_or_$b$ : TestGenerators.a_or_$b$()) {
			assertThat(a_or_$b$, is(pattern("a|(b)")));
			assertThat(group(a_or_$b$), is(pattern("(a|(b))")));
		}
		testRoundTrip("a|(b)");
		testRoundTrip("(a|(b))");
		
		for (JavaRegexPair $a$_or_$b$ : TestGenerators.$a$_or_$b$()) {
			assertThat($a$_or_$b$, is(pattern("(a)|(b)")));
			assertThat(group($a$_or_$b$), is(pattern("((a)|(b))")));
		}
		testRoundTrip("(a)|(b)");
		testRoundTrip("((a)|(b))");
	}
	
	@Test
	public void ab_or_c() {
		// ab|c
		for (JavaRegexPair ab_or_c : TestGenerators.ab_or_c()) {
			assertThat(ab_or_c, is(pattern("ab|c")));
			assertThat(group(ab_or_c), is(pattern("(ab|c)")));
		}
		testRoundTrip("ab|c");
		testRoundTrip("(ab|c)");
		
		// (a)b|c
		for (JavaRegexPair $a$b_or_c : TestGenerators.$a$b_or_c()) {
			assertThat($a$b_or_c, is(pattern("(a)b|c")));
		}
		testRoundTrip("(a)b|c");
		
		// a(b)|c
		for (JavaRegexPair a$b$_or_c : TestGenerators.a$b$_or_c()) {
			assertThat(a$b$_or_c, is(pattern("a(b)|c")));
		}
		testRoundTrip("a(b)|c");
		
		// ab|(c)
		for (JavaRegexPair ab_or_$c$ : TestGenerators.ab_or_$c$()) {
			assertThat(ab_or_$c$, is(pattern("ab|(c)")));
		}
		testRoundTrip("ab|(c)");
		
		// (ab)|c
		for (JavaRegexPair $ab$_or_c : TestGenerators.$ab$_or_c()) {
			assertThat($ab$_or_c, is(pattern("(ab)|c")));
		}
		testRoundTrip("(ab)|c");
		
		// (ab)|(c)
		for (JavaRegexPair $ab$_or_$c$ : TestGenerators.$ab$_or_$c$()) {
			assertThat($ab$_or_$c$, is(pattern("(ab)|(c)")));
		}
		testRoundTrip("(ab)|(c)");
		
		// ((a)b)|c
		for (JavaRegexPair $$a$b$_or_c : TestGenerators.$$a$b$_or_c()) {
			assertThat($$a$b$_or_c, is(pattern("((a)b)|c")));
		}
		testRoundTrip("((a)b)|c");
		
		// (a(b))|c
		for (JavaRegexPair $a$b$$_or_c : TestGenerators.$a$b$$_or_c()) {
			assertThat($a$b$$_or_c, is(pattern("(a(b))|c")));
		}
		testRoundTrip("(a(b))|c");
		
		// ((a)(b))|c
		for (JavaRegexPair $$a$$b$$_or_c : TestGenerators.$$a$$b$$_or_c()) {
			assertThat($$a$$b$$_or_c, is(pattern("((a)(b))|c")));
		}
		testRoundTrip("((a)(b))|c");
		
		// (a)b|(c)
		for (JavaRegexPair $a$b_or_$c$ : TestGenerators.$a$b_or_$c$()) {
			assertThat($a$b_or_$c$, is(pattern("(a)b|(c)")));
		}
		testRoundTrip("(a)b|(c)");
		
		// a(b)|(c)
		for (JavaRegexPair a$b$_or_$c$ : TestGenerators.a$b$_or_$c$()) {
			assertThat(a$b$_or_$c$, is(pattern("a(b)|(c)")));
		}
		testRoundTrip("a(b)|(c)");
		
		// ((a)b)|(c)
		for (JavaRegexPair $a$b$$_or_$c$ : TestGenerators.$$a$b$_or_$c$()) {
			assertThat($a$b$$_or_$c$, is(pattern("((a)b)|(c)")));
		}
		testRoundTrip("((a)b)|(c)");
		
		// (a(b))|(c)
		for (JavaRegexPair $a$b$$_or_$c$ : TestGenerators.$a$b$$_or_$c$()) {
			assertThat($a$b$$_or_$c$, is(pattern("(a(b))|(c)")));
		}
		testRoundTrip("(a(b))|(c)");
		
		// ((a)(b))|(c)
		for (JavaRegexPair $$a$$b$$_or_$c$ : TestGenerators.$$a$$b$$_or_$c$()) {
			assertThat($$a$$b$$_or_$c$, is(pattern("((a)(b))|(c)")));
		}
		testRoundTrip("((a)(b))|(c)");
	}
	
	@Test
	public void a_or_bc() {
		for (JavaRegexPair a_or_bc : TestGenerators.a_or_bc()) {
			assertThat(a_or_bc, is(pattern("a|bc")));
			assertThat(group(a_or_bc), is(pattern("(a|bc)")));
		}
		testRoundTrip("a|bc");
		testRoundTrip("(a|bc)");
		
		// a|(b)c
		for (JavaRegexPair a_or_$b$c : TestGenerators.a_or_$b$c()) {
			assertThat(a_or_$b$c, is(pattern("a|(b)c")));
		}
		testRoundTrip("a|(b)c");
		
		// a|b(c)
		for (JavaRegexPair a_or_b$c$ : TestGenerators.a_or_b$c$()) {
			assertThat(a_or_b$c$, is(pattern("a|b(c)")));
		}
		testRoundTrip("a|b(c)");
		
		// a|(bc)
		for (JavaRegexPair a_or_$bc$ : TestGenerators.a_or_$bc$()) {
			assertThat(a_or_$bc$, is(pattern("a|(bc)")));
		}
		testRoundTrip("a|(bc)");
		
		// a|((b)c)
		for (JavaRegexPair a_or_$$b$c$ : TestGenerators.a_or_$$b$c$()) {
			assertThat(a_or_$$b$c$, is(pattern("a|((b)c)")));
		}
		testRoundTrip("a|((b)c)");
		
		// a|(b(c))
		for (JavaRegexPair a_or_$b$c$$ : TestGenerators.a_or_$b$c$$()) {
			assertThat(a_or_$b$c$$, is(pattern("a|(b(c))")));
		}
		testRoundTrip("a|(b(c))");
		
		// a|((b)(c))
		for (JavaRegexPair a_or_$$b$$c$$ : TestGenerators.a_or_$$b$$c$$()) {
			assertThat(a_or_$$b$$c$$, is(pattern("a|((b)(c))")));
		}
		testRoundTrip("a|((b)(c))");
		
		// a|(b)(c)
		for (JavaRegexPair a_or_$b$$c$ : TestGenerators.a_or_$b$$c$()) {
			assertThat(a_or_$b$$c$, is(pattern("a|(b)(c)")));
		}
		testRoundTrip("a|(b)(c)");
		
		// (a)|bc
		for (JavaRegexPair $a$_or_bc : TestGenerators.$a$_or_bc()) {
			assertThat($a$_or_bc, is(pattern("(a)|bc")));
		}
		testRoundTrip("(a)|bc");
		
		// (a)|(bc)
		for (JavaRegexPair $a$_or_$bc$ : TestGenerators.$a$_or_$bc$()) {
			assertThat($a$_or_$bc$, is(pattern("(a)|(bc)")));
		}
		testRoundTrip("(a)|(bc)");
		
		// (a)|b(c)
		for (JavaRegexPair $a$_or_b$c$ : TestGenerators.$a$_or_b$c$()) {
			assertThat($a$_or_b$c$, is(pattern("(a)|b(c)")));
		}
		testRoundTrip("(a)|b(c)");
		
		// (a)|((b)c)
		for (JavaRegexPair $a$_or_$$b$c$ : TestGenerators.$a$_or_$$b$c$()) {
			assertThat($a$_or_$$b$c$, is(pattern("(a)|((b)c)")));
		}
		testRoundTrip("(a)|((b)c)");
		
		// (a)|(b(c))
		for (JavaRegexPair $a$_or_$b$c$$ : TestGenerators.$a$_or_$b$c$$()) {
			assertThat($a$_or_$b$c$$, is(pattern("(a)|(b(c))")));
		}
		testRoundTrip("(a)|(b(c))");
		
		// (a)|((b)(c))
		for (JavaRegexPair $a$_or_$$b$$c$$ : TestGenerators.$a$_or_$$b$$c$$()) {
			assertThat($a$_or_$$b$$c$$, is(pattern("(a)|((b)(c))")));
		}
		testRoundTrip("(a)|((b)(c))");
	}
	
	@Test
	public void ab_or_cd() {
		// Wind down on obsessiveness now that "ab|c" and "a|bc" have been verified beyond what is reasonable... 
		for (JavaRegexPair ab_or_cd : TestGenerators.ab_or_cd()) {
			assertThat(ab_or_cd, is(pattern("ab|cd")));
			assertThat(group(ab_or_cd), is(pattern("(ab|cd)")));
		}
	}
	
//	/* ZERO REPETITION */
//	
//	@Test
//	public void zeroRepetition() {
//		// a
//		for (JavaRegexPair a : character('a')) {
//			for (JavaRegexPair a0 : repeatedTimes(a, 0)) {
//				assertThat(a0, is(pattern("")));
//				assertThat(group(a0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair a0 : repeatedTimes(group(a), 0)) {
//				assertThat(a0, is(pattern("")));
//				assertThat(group(a0), is(pattern("()")));
//			}
//		}
//		// ab
//		for (JavaRegexPair ab : TestGenerators.ab('a', 'b')) {
//			for (JavaRegexPair ab0 : repeatedTimes(ab, 0)) {
//				assertThat(ab0, is(pattern("")));
//				assertThat(group(ab0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair ab0 : repeatedTimes(group(ab), 0)) {
//				assertThat(ab0, is(pattern("")));
//				assertThat(group(ab0), is(pattern("()")));
//			}
//		}
//		// Character class
//		for (JavaRegexPair ab_class : characterClass('a', 'b')) {
//			for (JavaRegexPair ab_class0 : repeatedTimes(ab_class, 0)) {
//				assertThat(ab_class0, is(pattern("")));
//				assertThat(group(ab_class0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair ab_class0 : repeatedTimes(group(ab_class), 0)) {
//				assertThat(ab_class0, is(pattern("")));
//				assertThat(group(ab_class0), is(pattern("()")));
//			}
//		}
//		// a_or_b
//		for (JavaRegexPair a_or_b : TestGenerators.a_or_b('a', 'b')) {
//			for (JavaRegexPair a_or_b0 : repeatedTimes(a_or_b, 0)) {
//				assertThat(a_or_b0, is(pattern("")));
//				assertThat(group(a_or_b0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_b0 : repeatedTimes(group(a_or_b), 0)) {
//				assertThat(a_or_b0, is(pattern("")));
//				assertThat(group(a_or_b0), is(pattern("()")));
//			}
//		}
//		// a_or_bc
//		for (JavaRegexPair a_or_bc : TestGenerators.a_or_bc('a', 'b', 'c')) {
//			for (JavaRegexPair a_or_bc0 : repeatedTimes(a_or_bc, 0)) {
//				assertThat(a_or_bc0, is(pattern("")));
//				assertThat(group(a_or_bc0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_bc0 : repeatedTimes(group(a_or_bc), 0)) {
//				assertThat(a_or_bc0, is(pattern("")));
//				assertThat(group(a_or_bc0), is(pattern("()")));
//			}
//		}
//		// ab_or_c
//		for (JavaRegexPair ab_or_c : TestGenerators.ab_or_c('a', 'b', 'c')) {
//			for (JavaRegexPair ab_or_c0 : repeatedTimes(ab_or_c, 0)) {
//				assertThat(ab_or_c0, is(pattern("")));
//				assertThat(group(ab_or_c0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_c0 : repeatedTimes(group(ab_or_c), 0)) {
//				assertThat(ab_or_c0, is(pattern("")));
//				assertThat(group(ab_or_c0), is(pattern("()")));
//			}
//		}
//		// ab_or_cd
//		for (JavaRegexPair ab_or_cd : TestGenerators.ab_or_cd('a', 'b', 'c', 'd')) {
//			for (JavaRegexPair ab_or_cd0 : repeatedTimes(ab_or_cd, 0)) {
//				assertThat(ab_or_cd0, is(pattern("")));
//				assertThat(group(ab_or_cd0), is(pattern("()")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_cd0 : repeatedTimes(group(ab_or_cd), 0)) {
//				assertThat(ab_or_cd0, is(pattern("")));
//				assertThat(group(ab_or_cd0), is(pattern("()")));
//			}
//		}
//	}
//	
//	@Test
//	public void singleRepetition() {
//		// a
//		for (JavaRegexPair a : character('a')) {
//			for (JavaRegexPair a1 : repeatedTimes(a, 1)) {
//				assertThat(a1, is(pattern("a")));
//				assertThat(group(a1), is(pattern("(a)")));
//			}
//			// grouped
//			for (JavaRegexPair a1 : repeatedTimes(group(a), 1)) {
//				assertThat(a1, is(pattern("(a)")));
//				assertThat(group(a1), is(pattern("((a))")));
//			}
//		}
//		// ab
//		for (JavaRegexPair ab : TestGenerators.ab('a', 'b')) {
//			for (JavaRegexPair ab1 : repeatedTimes(ab, 1)) {
//				assertThat(ab1, is(pattern("ab")));
//				assertThat(group(ab1), is(pattern("(ab)")));
//			}
//			// grouped
//			for (JavaRegexPair ab1 : repeatedTimes(group(ab), 1)) {
//				assertThat(ab1, is(pattern("(ab)")));
//				assertThat(group(ab1), is(pattern("((ab))")));
//			}
//		}
//		// Character class
//		for (JavaRegexPair ab_class : characterClass('a', 'b')) {
//			for (JavaRegexPair ab_class1 : repeatedTimes(ab_class, 1)) {
//				assertThat(ab_class1, is(pattern("[ab]")));
//				assertThat(group(ab_class1), is(pattern("([ab])")));
//			}
//			// grouped
//			for (JavaRegexPair ab_class1 : repeatedTimes(group(ab_class), 1)) {
//				assertThat(ab_class1, is(pattern("([ab])")));
//				assertThat(group(ab_class1), is(pattern("(([ab]))")));
//			}
//		}
//		// a_or_b
//		for (JavaRegexPair a_or_b : TestGenerators.a_or_b('a', 'b')) {
//			for (JavaRegexPair a_or_b1 : repeatedTimes(a_or_b, 1)) {
//				assertThat(a_or_b1, is(pattern("a|b")));
//				assertThat(group(a_or_b1), is(pattern("(a|b)")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_b1 : repeatedTimes(group(a_or_b), 1)) {
//				assertThat(a_or_b1, is(pattern("(a|b)")));
//				assertThat(group(a_or_b1), is(pattern("((a|b))")));
//			}
//		}
//		// a_or_bc
//		for (JavaRegexPair a_or_bc : TestGenerators.a_or_bc('a', 'b', 'c')) {
//			for (JavaRegexPair a_or_bc1 : repeatedTimes(a_or_bc, 1)) {
//				assertThat(a_or_bc1, is(pattern("a|bc")));
//				assertThat(group(a_or_bc1), is(pattern("(a|bc)")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_bc1 : repeatedTimes(group(a_or_bc), 1)) {
//				assertThat(a_or_bc1, is(pattern("(a|bc)")));
//				assertThat(group(a_or_bc1), is(pattern("((a|bc))")));
//			}
//		}
//		// ab_or_c
//		for (JavaRegexPair ab_or_c : TestGenerators.ab_or_c('a', 'b', 'c')) {
//			for (JavaRegexPair ab_or_c1 : repeatedTimes(ab_or_c, 1)) {
//				assertThat(ab_or_c1, is(pattern("ab|c")));
//				assertThat(group(ab_or_c1), is(pattern("(ab|c)")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_c1 : repeatedTimes(group(ab_or_c), 1)) {
//				assertThat(ab_or_c1, is(pattern("(ab|c)")));
//				assertThat(group(ab_or_c1), is(pattern("((ab|c))")));
//			}
//		}
//		// ab_or_cd
//		for (JavaRegexPair ab_or_cd : TestGenerators.ab_or_cd('a', 'b', 'c', 'd')) {
//			for (JavaRegexPair ab_or_cd1 : repeatedTimes(ab_or_cd, 1)) {
//				assertThat(ab_or_cd1, is(pattern("ab|cd")));
//				assertThat(group(ab_or_cd1), is(pattern("(ab|cd)")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_cd1 : repeatedTimes(group(ab_or_cd), 1)) {
//				assertThat(ab_or_cd1, is(pattern("(ab|cd)")));
//				assertThat(group(ab_or_cd1), is(pattern("((ab|cd))")));
//			}
//		}
//	}
//	
//	@Test
//	public void doubleRepetition() {
//		// a
//		for (JavaRegexPair a : character('a')) {
//			for (JavaRegexPair a2 : repeatedTimes(a, 2)) {
//				assertThat(a2, is(pattern("a{2}")));
//				assertThat(group(a2), is(pattern("(a{2})")));
//			}
//			// grouped
//			for (JavaRegexPair a2 : repeatedTimes(group(a), 2)) {
//				assertThat(a2, is(pattern("(a){2}")));
//				assertThat(group(a2), is(pattern("((a){2})")));
//			}
//		}
//		// ab
//		for (JavaRegexPair ab : TestGenerators.ab('a', 'b')) {
//			for (JavaRegexPair ab2 : repeatedTimes(ab, 2)) {
//				assertThat(ab2, is(pattern("(?:ab){2}")));
//				assertThat(group(ab2), is(pattern("((?:ab){2})")));
//			}
//			// grouped
//			for (JavaRegexPair ab2 : repeatedTimes(group(ab), 2)) {
//				assertThat(ab2, is(pattern("(ab){2}")));
//				assertThat(group(ab2), is(pattern("((ab){2})")));
//			}
//		}
//		// Character class
//		for (JavaRegexPair ab_class : characterClass('a', 'b')) {
//			for (JavaRegexPair ab_class2 : repeatedTimes(ab_class, 2)) {
//				assertThat(ab_class2, is(pattern("[ab]{2}")));
//				assertThat(group(ab_class2), is(pattern("([ab]{2})")));
//			}
//			// grouped
//			for (JavaRegexPair ab_class2 : repeatedTimes(group(ab_class), 2)) {
//				assertThat(ab_class2, is(pattern("([ab]){2}")));
//				assertThat(group(ab_class2), is(pattern("(([ab]){2})")));
//			}
//		}
//		// a_or_b
//		for (JavaRegexPair a_or_b : TestGenerators.a_or_b('a', 'b')) {
//			for (JavaRegexPair a_or_b2 : repeatedTimes(a_or_b, 2)) {
//				assertThat(a_or_b2, is(pattern("(?:a|b){2}")));
//				assertThat(group(a_or_b2), is(pattern("((?:a|b){2})")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_b2 : repeatedTimes(group(a_or_b), 2)) {
//				assertThat(a_or_b2, is(pattern("(a|b){2}")));
//				assertThat(group(a_or_b2), is(pattern("((a|b){2})")));
//			}
//		}
//		// a_or_bc
//		for (JavaRegexPair a_or_bc : TestGenerators.a_or_bc('a', 'b', 'c')) {
//			for (JavaRegexPair a_or_bc2 : repeatedTimes(a_or_bc, 2)) {
//				assertThat(a_or_bc2, is(pattern("(?:a|bc){2}")));
//				assertThat(group(a_or_bc2), is(pattern("((?:a|bc){2})")));
//			}
//			// grouped
//			for (JavaRegexPair a_or_bc2 : repeatedTimes(group(a_or_bc), 2)) {
//				assertThat(a_or_bc2, is(pattern("(a|bc){2}")));
//				assertThat(group(a_or_bc2), is(pattern("((a|bc){2})")));
//			}
//		}
//		// ab_or_c
//		for (JavaRegexPair ab_or_c : TestGenerators.ab_or_c('a', 'b', 'c')) {
//			for (JavaRegexPair ab_or_c2 : repeatedTimes(ab_or_c, 2)) {
//				assertThat(ab_or_c2, is(pattern("(?:ab|c){2}")));
//				assertThat(group(ab_or_c2), is(pattern("((?:ab|c){2})")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_c2 : repeatedTimes(group(ab_or_c), 2)) {
//				assertThat(ab_or_c2, is(pattern("(ab|c){2}")));
//				assertThat(group(ab_or_c2), is(pattern("((ab|c){2})")));
//			}
//		}
//		// ab_or_cd
//		for (JavaRegexPair ab_or_cd : TestGenerators.ab_or_cd('a', 'b', 'c', 'd')) {
//			for (JavaRegexPair ab_or_cd2 : repeatedTimes(ab_or_cd, 2)) {
//				assertThat(ab_or_cd2, is(pattern("(?:ab|cd){2}")));
//				assertThat(group(ab_or_cd2), is(pattern("((?:ab|cd){2})")));
//			}
//			// grouped
//			for (JavaRegexPair ab_or_cd2 : repeatedTimes(group(ab_or_cd), 2)) {
//				assertThat(ab_or_cd2, is(pattern("(ab|cd){2}")));
//				assertThat(group(ab_or_cd2), is(pattern("((ab|cd){2})")));
//			}
//		}
//	}
}
