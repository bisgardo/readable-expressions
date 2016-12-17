package readable_expressions.parse;

import readable_expressions.regex.Group;
import readable_expressions.regex.Regex;

public class GroupRegexBuilder extends RegexBuilder {
	private final Regex regex;
	private final String groupName;
	
	public GroupRegexBuilder(RegexBuilder parent, Regex regex, String groupName) {
		super(parent);
		
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		
		this.regex = regex;
		this.groupName = groupName;
	}
	
	@Override
	public Regex build(Regex child) {
		Group group = child.asGroup(groupName);
		return regex.followedBy(group);
	}
}
