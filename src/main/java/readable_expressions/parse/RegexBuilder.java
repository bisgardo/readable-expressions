package readable_expressions.parse;

import readable_expressions.regex.Regex;

public abstract class RegexBuilder {
	private final RegexBuilder parent;

	public RegexBuilder(RegexBuilder parent) {
		this.parent = parent;
	}
	
	public RegexBuilder getParent() {
		return parent;
	}
	
	public abstract Regex build(Regex child);
}
