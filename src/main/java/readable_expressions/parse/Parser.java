package readable_expressions.parse;

import readable_expressions.RegexSymbols;
import readable_expressions.Regexes;
import readable_expressions.regex.Regex;

public class Parser {
	
//	public static Regex parseRegex(@Language("RegExp") String input) throws ParseException {
//		return parseRegex((CharSequence) input);
//	}
	
	public static Regex parseRegex(CharSequence input) throws RegexParseException {
		if (input == null) {
			throw new NullPointerException("input");
		}
		
		// Pointer to the head of a linked stack.
		RegexBuilder head = new RegexBuilder(null) {
			@Override
			public Regex build(Regex child) {
				return child;
			}
		};
		
		Regex result = Regexes.empty;
		
		boolean escaped = false;
		boolean inCharacterClass = false;
		for (int index = 0; index < input.length(); index++) {
			char character = input.charAt(index);
			
			if (escaped) {
				escaped = false;
				if (Character.isLetterOrDigit(character)) {
					// TODO Call `result = result.followedBy(regex)` with correct `regex`.
					throw new UnsupportedOperationException(
							String.format(
									"Support for escaped letter (character class etc.) or digit (back-reference) '%s' (index %d) is not yet implemented.",
									character,
									index
							)
					);
				}
			} else if (character == '\\') {
				escaped = true;
				continue;
			} else {
				switch (character) {
					case '(':
						// Push group builder.
						// TODO Interpret group "kind".
						head = new GroupRegexBuilder(head, result, null);
						result = Regexes.empty;
						continue;
					case ')':
						if (head instanceof ChoiceBuilder) { // <=> "not instance of `GroupBuilder`"
							// Pop builder.
							result = head.build(result);
							head = head.getParent();
						}
						// Pop group.
						result = head.build(result);
						head = head.getParent();
						continue;
					case '|':
						// Push builder.
						head = new ChoiceBuilder(head, result);
						result = Regexes.empty;
						continue;
				}

				// TODO Interpret special symbols...
				if (RegexSymbols.isSpecialCharacter(character, inCharacterClass)) {
					throw new UnsupportedOperationException(
							String.format(
									"Support for special character '%s' (index %d) is not yet implemented.",
									character,
									index
							)
					);
				}
			}
			// TODO Optimize `followedBy` for single-character string by appending to string builder (which is then replaced with substring extraction).
			Regex regex = Regexes.string(String.valueOf(character));
			result = result.followedBy(regex);
		}
		
		if (escaped) {
			throw new RegexParseException("Input ends with a dangling escape character", input.length());
		}
		
		if (head instanceof ChoiceBuilder) { // <=> "not instance of `GroupBuilder`"
			result = head.build(result);
			head = head.getParent();
		}
		
		if (head.getParent() != null) {
			// Sanity check.
			throw new IllegalStateException(
					String.format("Final builder element '%s' has unexpected parent '%s'", head, head.getParent())
			);
		}
		
		return head.build(result);
	}
}
