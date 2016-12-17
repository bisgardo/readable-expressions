package readable_expressions.regex;

import readable_expressions.RegexSymbols;

import java.util.Iterator;

public class Group extends Regex {
	private final Regex regex;
	private final String groupName;
	
	public Group(Regex regex, String groupName) {
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		if (groupName != null && groupName.length() == 0) {
			groupName = null;
		}
		this.regex = regex;
		this.groupName = groupName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	@Override
	protected int getCardinality() {
		return 1;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(RegexSymbols.GROUP_BEGIN);
		if (groupName != null) {
			stringBuilder
					.append(RegexSymbols.NAME_BEGIN)
					.append(groupName)
					.append(RegexSymbols.NAME_END);
		}
		
		return stringBuilder
				.append(regex)
				.append(RegexSymbols.GROUP_END)
				.toString();
	}
	
	@Override
	public Iterator<String> iterateMatches() {
		return regex.iterateMatches();
	}
}
