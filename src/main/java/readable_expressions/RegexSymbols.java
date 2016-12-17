package readable_expressions;

import static readable_expressions.RegexMetacharacters.*;

public class RegexSymbols {
	
	/* REPETITION */
	
	public static final String ZERO_OR_ONE_TIMES = "" + QUESTION_MARK;
	public static final String ZERO_OR_MORE_TIMES = "" + STAR;
	public static final String ONE_OR_MORE_TIMES = "" + PLUS;
	
	public static final String GREEDY = EMPTY;
	public static final String RELUCTANT = "" + QUESTION_MARK;
	public static final String POSSESSIVE = "" + PLUS;
	
	public static final String ZERO_OR_ONE_TIMES_GREEDY = ZERO_OR_ONE_TIMES + GREEDY;
	public static final String ZERO_OR_MORE_TIMES_GREEDY = ZERO_OR_MORE_TIMES + GREEDY;
	public static final String ONE_OR_MORE_TIMES_GREEDY = ONE_OR_MORE_TIMES + GREEDY;
	public static final String ZERO_OR_ONE_TIMES_RELUCTANT = ZERO_OR_ONE_TIMES + RELUCTANT;
	public static final String ZERO_OR_MORE_TIMES_RELUCTANT = ZERO_OR_MORE_TIMES + RELUCTANT;
	public static final String ONE_OR_MORE_TIMES_RELUCTANT = ONE_OR_MORE_TIMES + RELUCTANT;
	public static final String ZERO_OR_ONE_TIMES_POSSESSIVE = ZERO_OR_ONE_TIMES + POSSESSIVE;
	public static final String ZERO_OR_MORE_TIMES_POSSESSIVE = ZERO_OR_MORE_TIMES + POSSESSIVE;
	public static final String ONE_OR_MORE_TIMES_POSSESSIVE = ONE_OR_MORE_TIMES + POSSESSIVE;
	
	// TODO Rename such that "BEGIN"/"END" is prefix?
	
	/* GROUP */
	
	public static final String NAME_BEGIN = "" + LESS_THAN;
	public static final String NAME_END = "" + GREATER_THAN;
	
	public static final String GROUP_BEGIN = "" + LEFT_PARENTHESIS;
	public static final String GROUP_END = "" + RIGHT_PARENTHESIS;
	
	private static final String GROUP_BEGIN_SPECIAL = GROUP_BEGIN + QUESTION_MARK;
	
	public static final String GROUP_BEGIN_NON_CAPTURING = GROUP_BEGIN_SPECIAL + COLON;
	public static final String GROUP_BEGIN_NAMED = GROUP_BEGIN_SPECIAL + NAME_BEGIN;
	
	public static final String NAMED_BACK_REFERENCE_BEGIN = "\\k" + NAME_BEGIN;
	public static final String NAMED_BACK_REFERENCE_END = NAME_END;
	
	/* LOOK-AROUND */
	
	private static final String LOOKAROUND_BEGIN = GROUP_BEGIN_SPECIAL;
	private static final String LOOKAROUND_END = GROUP_END;
	
	public static final String POSITIVE_LOOKAHEAD_BEGIN = LOOKAROUND_BEGIN + EQUALS;
	public static final String POSITIVE_LOOKAHEAD_END = LOOKAROUND_END;
	
	public static final String NEGATIVE_LOOKAHEAD_BEGIN = LOOKAROUND_BEGIN + EXCLAMATION_MARK;
	public static final String NEGATIVE_LOOKAHEAD_END = LOOKAROUND_END;
	
	public static final String POSITIVE_LOOKBEHIND_BEGIN = LOOKAROUND_BEGIN + LESS_THAN + EQUALS;
	public static final String POSITIVE_LOOKBEHIND_END = LOOKAROUND_END;
	
	public static final String NEGATIVE_LOOKBEHIND_BEGIN = LOOKAROUND_BEGIN + LESS_THAN + EXCLAMATION_MARK;
	public static final String NEGATIVE_LOOKBEHIND_END = LOOKAROUND_END;
	
	/* CHARACTER CLASSES */
	
	public static final String ANYTHING = "" + DOT;
	public static final String BEGINNING_OF_LINE = "" + CARET;
	public static final String END_OF_LINE = "" + DOLLAR_SIGN;
	public static final String BEGIN_QUOTE = "\\Q";
	public static final String END_QUOTE = "\\E";
	public static final String WORD_BOUNDARY = "\\b";
	public static final String NON_WORD_BOUNDARY = "\\B";
	public static final String BEGINNING_OF_INPUT = "\\A";
	public static final String END_OF_INPUT = "\\z";
	public static final String END_OF_PREVIOUS_MATCH = "\\G";
	public static final String END_OF_PREVIOUS_MATCH_FOR_FINAL_TERMINATOR = "\\Z";
	
	public static final String NUMERIC = "\\d";
	public static final String NON_NUMERIC = "\\D";
	public static final String WORD = "\\w";
	public static final String NON_WORD = "\\W";
	public static final String WHITESPACE = "\\s";
	public static final String NON_WHITESPACE = "\\S";
	
	public static final String VERTICAL_WHITESPACE = "\\v";
	public static final String NON_VERTICAL_WHITESPACE = "\\V";
	
	public static final String HORIZONTAL_WHITESPACE = "\\h";
	public static final String NON_HORIZONTAL_WHITESPACE = "\\H";
	
	public static final String TAB = "\\t";
	public static final String NEWLINE = "\\n";
	public static final String CARRIAGE_RETURN = "\\r";
	public static final String FORM_FEED = "\\f";
	public static final String ALERT = "\\a";
	public static final String ESCAPE = "\\e";
	
	/* New in Java8: Equivalent to `\u000D\u000A|[\u000A\u000B\u000C\u000D\u0085\u2028\u2029]`. */
	public static final String LINEBREAK = "\\R";
	
	public static final String CONTROL_PREFIX = "\\c";
	
	public static String control(char character) {
		// It appears that any character will compile, though that doesn't necessarily mean that it will match anything.
		return CONTROL_PREFIX + character;
	}
	
	// TODO Instead of having ''- and 'non'-everything; make interfaces/enums and just two functions `in` and `notIn`
	//      for wrapping in `\p{...}` and `\P{...}`, respectively.
	// TODO Should classes know their negations? And will/should this also work for "standard" classes?
	
	public interface Posix {
		String LOWER_CASE_ALPHA = "\\p{Lower}";  // [a-z]
		String NON_LOWER_CASE_ALPHA = "\\P{Lower}";
		String UPPER_CASE_ALPHA = "\\p{Upper}";  // [A-Z]
		String NON_UPPER_CASE_ALPHA = "\\P{Upper}";
		String ASCII = "\\p{ASCII}";  // [\x00-\x7F]
		String NON_ASCII = "\\P{ASCII}";
		String ALPHA = "\\p{Alpha}";  // [\p{Lower}\p{Upper}]
		String NON_ALPHA = "\\P{Alpha}";
		String DIGIT = "\\p{Digit}";  // [0-9]
		String NON_DIGIT = "\\P{Digit}";
		String ALPLA_NUMERIC = "\\p{Alnum}";  // [\p{Alpha}\p{Digit}]
		String NON_ALPHA_NUMERIC = "\\P{Alnum}";
		String PUNCTUATION = "\\p{Punct}";  // [!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]
		String NON_PUNCTUATION = "\\P{Punct}";
		String GRAPHICAL = "\\p{Graph}";  // [\p{Alnum}\p{Punct}]
		String NON_GRAPHICAL = "\\P{Graph}";
		String PRINTABLE = "\\p{Print}";  // [\p{Graph}\x20]
		String NON_PRINTABLE = "\\P{Print}";
		String BLANK = "\\p{Blank}";  // [ \t]
		String NON_BLANK = "\\P{Blank}";
		String CONTROL = "\\p{Cntrl}";  // [\x00-\x1F\x7F]
		String NON_CONTROL = "\\P{Cntrl}";
		String HEX_DIGIT = "\\p{XDigit}";  // [0-9a-fA-F]
		String NON_HEX_DIGIT = "\\P{XDigit}";
		String WHITESPACE = "\\p{Space}";  // [ \t\n\x0B\f\r]
		String NON_WHITESPACE = "\\P{Space}";
	}
	
	public interface Java {
		String LOWER_CASE = "\\p{javaLowerCase}";  // java.lang.Character.isLowerCase()
		String NON_LOWER_CASE = "\\P{javaLowerCase}";
		String UPPER_CASE = "\\p{javaUpperCase}";  // java.lang.Character.isUpperCase()
		String NON_UPPER_CASE = "\\P{javaUpperCase}";
		String WHITESPACE = "\\p{javaWhitespace}";  // java.lang.Character.isWhitespace()
		String NON_WHITESPACE = "\\P{javaWhitespace}";
		String MIRRORED = "\\p{javaMirrored}";  // java.lang.Character.isMirrored()
		String NON_MIRRORED = "\\P{javaMirrored}";
	}
	
	public static class Unicode {
		@SuppressWarnings("Since15")
		public static String inScript(Character.UnicodeScript unicodeScript) {
			if (unicodeScript == null) {
				throw new NullPointerException("unicodeScript");
			}
			return inScript(unicodeScript.name());
		}
		
		@SuppressWarnings("Since15")
		public static String notInScript(Character.UnicodeScript unicodeScript) {
			if (unicodeScript == null) {
				throw new NullPointerException("unicodeScript");
			}
			return notInScript(unicodeScript.name());
		}
		
		public static String inScript(String unicodeScriptName) {
			if (unicodeScriptName == null) {
				throw new NullPointerException("unicodeScriptName");
			}
			return "\\p{Is" + unicodeScriptName + "}";
		}
		
		public static String notInScript(String unicodeScriptName) {
			if (unicodeScriptName == null) {
				throw new NullPointerException("unicodeScriptName");
			}
			return "\\P{Is" + unicodeScriptName + "}";
		}
		
		public static String inBlock(Character.UnicodeBlock block) {
			if (block == null) {
				throw new NullPointerException("block");
			}
			return inBlock(block.toString());
		}
		
		public static String notInBlock(Character.UnicodeBlock block) {
			if (block == null) {
				throw new NullPointerException("block");
			}
			return notInBlock(block.toString());
		}
		
		public static String inBlock(String unicodeBlockName) {
			if (unicodeBlockName == null) {
				throw new NullPointerException("unicodeBlockName");
			}
			return "\\p{In" + unicodeBlockName + "}";
		}
		
		public static String notInBlock(String unicodeBlockName) {
			if (unicodeBlockName == null) {
				throw new NullPointerException("unicodeBlockName");
			}
			return "\\P{In" + unicodeBlockName + "}";
		}
		
		// TODO Extract to enum and add `hasProperty()`/`doesntHaveProperty()` methods?
		public interface BinaryProperty {
			String ALPHABETIC = "\\p{IsAlphabetic}";
			String NON_ALPHABETIC = "\\P{IsAlphabetic}";
			String IDEOGRAPHIC = "\\p{IsIdeographic}";
			String NON_IDEOGRAPHIC = "\\P{IsIdeographic}";
			String LETTER = "\\p{IsLetter}";
			String NON_LETTER = "\\P{IsLetter}";
			String LOWER_CASE = "\\p{IsLowercase}";
			String NON_LOWER_CASE = "\\P{IsLowercase}";
			String UPPER_CASE = "\\p{IsUppercase}";
			String NON_UPPER_CASE = "\\P{IsUppercase}";
			String TITLE_CASE = "\\p{IsTitlecase}";
			String NON_TITLE_CASE = "\\P{IsTitlecase}";
			String PUNCTUATION = "\\p{IsPunctuation}";
			String NON_PUNCTUATION = "\\P{IsPunctuation}";
			String CONTROL = "\\p{IsControl}";
			String NON_CONTROL = "\\P{IsControl}";
			String WHITESPACE = "\\p{IsWhiteSpace}";
			String NON_WHITESPACE = "\\P{IsWhiteSpace}";
			String DIGIT = "\\p{IsDigit}";
			String NON_DIGIT = "\\P{IsDigit}";
			String HEX_DIGIT = "\\p{IsHexDigit}";
			String NON_HEX_DIGIT = "\\P{IsHexDigit}";
			String JOIN_CONTROL = "\\p{IsJoinControl}";
			String NON_JOIN_CONTROL = "\\P{IsJoinControl}";
			String NONCHARACTER_CODE_POINT = "\\p{IsNoncharacterCodePoint}";
			String NON_NONCHARACTER_CODE_POINT = "\\P{IsNoncharacterCodePoint}";
			String ASSIGNED = "\\p{IsAssigned}";
			String NON_ASSIGNED = "\\P{IsAssigned}";
		}
	}
	
	public static String backReference(int groupNumber) {
		return "\\" + groupNumber;
	}
	
	public static String backReference(String groupName) {
		if (groupName == null) {
			throw new NullPointerException("groupName");
		}
		return NAMED_BACK_REFERENCE_BEGIN + groupName + NAMED_BACK_REFERENCE_END;
	}
	
	public static String escaped(String string, boolean inCharacterClass) {
		if (string == null) {
			throw new NullPointerException("string");
		}
		
		int length = string.length();
		if (length == 0) {
			return "";
		}
		if (length == 1) {
			char character = string.charAt(0);
			String replacement = escapeSpecialCharacterOrNull(character, false);
			if (replacement == null) {
				return string;
			}
			return replacement;
		}
		
		StringBuilder stringBuilder = new StringBuilder(length * 2);
		for (int index = 0; index < length; index++) {
			char character = string.charAt(index);
			String replacement = escapeSpecialCharacterOrNull(character, inCharacterClass);
			if (replacement == null) {
				stringBuilder.append(character);
			} else {
				stringBuilder.append(replacement);
			}
		}
		return stringBuilder.toString();
	}
	
	private static String escapeSpecialCharacterOrNull(char character, boolean inCharacterClass) {
		if (isSpecialCharacter(character, inCharacterClass)) {
			return escape(character);
		}
		return null;
	}
	
	private static String escape(char character) {
		return "\\" + character;
	}
	
	// TODO Implement for different contexts (determined from parameters).
	public static boolean isSpecialCharacter(char character, boolean inCharacterClass) {
		if (inCharacterClass) {
			return isSpecialCharacterInCharacterClass(character);
		}
		switch (character) {
			case DOT:
			case '|':
//			case LESS_THAN:
//			case GREATER_THAN:
			case LEFT_PARENTHESIS:
			case RIGHT_PARENTHESIS:
			case '[':
			case ']':
			case '{':
			case '}':
			case '\\':
			case CARET:
			case DOLLAR_SIGN:
//			case '-':
//			case EQUALS:
//			case EXCLAMATION_MARK:
			case QUESTION_MARK:
			case STAR:
			case PLUS:
				return true;
		}
		return false;
	}
	
	public static boolean isSpecialCharacterInCharacterClass(char character) {
		switch (character) {
			case '[':
			case ']':
			case '-':
			case CARET:
//			case '&':
				return true;
		}
		return false;
	}
}
