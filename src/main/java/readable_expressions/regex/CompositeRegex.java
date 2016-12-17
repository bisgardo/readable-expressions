package readable_expressions.regex;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeRegex extends Regex {
	private final List<Regex> regexes = new ArrayList<Regex>();
	
	protected List<Regex> getRegexes() {
		return regexes;
	}
	
//	abstract CompositeRegex copy();
	
	@Override
	public int getCardinality() {
		return regexes.size();
	}
	
	@Override
	public Group asGroup(String groupName) {
		if (regexes.size() == 1) {
			Regex regex = regexes.get(0);
			return regex.asGroup(groupName);
		}
		return super.asGroup(groupName);
	}
}
