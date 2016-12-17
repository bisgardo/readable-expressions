package readable_expressions;

import readable_expressions.regex.Regex;

import java.util.ArrayList;

public class JavaRegexPair {
	public static class List extends ArrayList<JavaRegexPair> {
		private static final long serialVersionUID = 5093793850484268643L;
		
		public interface Supplier {
			List get();
		}
		
		public List() {
			super(128);
		}
		
		public boolean add(String java, Regex regex) {
			return add(new JavaRegexPair(java, regex));
		}
	}
	
	public final String java;
	public final Regex regex;
	
	public JavaRegexPair(String java, Regex regex) {
		if (java == null) {
			throw new NullPointerException("java");
		}
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		this.java = java;
		this.regex = regex;
	}
}
