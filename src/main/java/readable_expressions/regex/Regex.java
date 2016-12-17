package readable_expressions.regex;

import readable_expressions.Regexes;

import java.util.Iterator;
import java.util.regex.Pattern;

public abstract class Regex extends RegexComponent {
	
	protected abstract int getCardinality();
	
	public abstract String toString();
	
	// TODO We need to pass in contextual information in order to implement for things like word boundary, lookaround,
	//      and back reference.
	//      Note: Matches are always generated left to right(?). This means that the previously matched groups is needed
	//      (the raw match string is already available). The only issue is in concatenation, where the only thing to do
	//      is to filter the next iterator. If nasty lookarounds are involved, this could cause infinite spinning.
	//      Static analysis would be needed to improve upon that - and most likely impossible to solve completely.
	// TODO Move to visitor along with other analyses like `isFinite`, `matchesEmpty`, `matchesNonEmpty`,
	//      `matchesAnything`, `minMatchLength`, `maxMatchLength`, ...
	//      Return nullable `Boolean` with null meaning "maybe".
	public abstract Iterator<String> iterateMatches();
	
	@Override
	@Deprecated
	public Regex toRegex() {
		return this;
	}
	
	public Choice or(CharacterClassComponent component) {
		// Cannot be pulled up to `RegexComponent`, because in `CharacterClassComponent` it means "adding" characters to
		// the character class (and it has return type `CharacterClassComponent`).
		if (component == null) {
			throw new NullPointerException("component");
		}
		return or(component.toRegex());
	}
	
	public Choice or(char character) {
		return or(Regexes.string(character));
	}
	
	public Pattern compile() {
		String pattern = toString();
		return Pattern.compile(pattern);
	}
}
