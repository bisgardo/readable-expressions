package readable_expressions.parse;

import readable_expressions.regex.Regex;

public class ChoiceBuilder extends RegexBuilder {
	private final Regex regex;
	
	public ChoiceBuilder(RegexBuilder parent, Regex regex) {
		super(parent);
		
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		this.regex = regex;
	}
	
	@Override
	public Regex build(Regex child) {
		return regex.or(child);
	}
}
