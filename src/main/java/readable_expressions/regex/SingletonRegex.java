package readable_expressions.regex;

public abstract class SingletonRegex extends Regex {
	private final String pattern;
	
	public SingletonRegex(String pattern) {
		if (pattern == null) {
			throw new NullPointerException("pattern");
		}
		this.pattern = pattern;
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
	
	@Override
	public String toString() {
		return pattern;
	}
}
