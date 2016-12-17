package readable_expressions;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RegexMatcher extends TypeSafeMatcher<JavaRegexPair> {
	private final String regex;
	
	private static final AtomicInteger COUNT = new AtomicInteger();
	private static final Map<String, Integer> TESTS = new HashMap<String, Integer>();
	
	public RegexMatcher(String regex) {
		this.regex = regex;
	}
	
	public static RegexMatcher pattern(String regex) {
		return new RegexMatcher(regex);
	}
	
	@Override
	protected boolean matchesSafely(JavaRegexPair pair) {
		int c = COUNT.incrementAndGet();
		
		String java = pair.java;
		Integer old = TESTS.put(java, c);
		if (old != null) {
			throw new IllegalStateException(String.format("Duplicate test on Java expression: '%s' (#%d)", java, old));
		}
		
		System.out.println(String.format("#%d: %s -> %s", c, java, regex));
		
		return regex.equals(pair.regex.toString());
	}
	
	//@Override
	public void describeTo(Description description) {
		description.appendText(regex);
	}
	
	@Override
	protected void describeMismatchSafely(JavaRegexPair pair, Description mismatchDescription) {
		mismatchDescription.appendText("was ").appendText(pair.regex.toString());
	}
}
