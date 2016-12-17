package readable_expressions;

import org.intellij.lang.annotations.Language;
import readable_expressions.parse.Parser;
import readable_expressions.regex.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import static readable_expressions.RegexSymbols.*;
import static readable_expressions.RegexSymbols.Java.MIRRORED;
import static readable_expressions.RegexSymbols.Java.NON_MIRRORED;
import static readable_expressions.RegexSymbols.Posix.*;
import static readable_expressions.RegexSymbols.Unicode.BinaryProperty;
import static readable_expressions.RegexSymbols.Unicode.BinaryProperty.*;

public abstract class Regexes {
	
	/*
	 * TODO Possible future transformation settings:
	 *      - Expand finite repetition of strings
	 *      - Remove repetition symbol of single repetitions and entire zero repetition
	 *      - Always use capturing groups for parentheses
	 *      - Replace choice of singleton strings with character class (and merge choice of character classes)
	 *      - Replace "not(class)" with negated class (i.e., `not(whitespace)` -> `nonWhitespace`)
	 *      - Replace tab/newline characters with escaped ones.
	 */
	
	public static final SingletonRegex anything = new SingletonRegex(ANYTHING) {
		@Override
		public Iterator<String> iterateMatches() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	};
	
	/* BOUNDARY MATCHERS */
	
	public static final SingletonRegex beginningOfLine = new EmptyMatchSingletonRegex(BEGINNING_OF_LINE);
	public static final SingletonRegex endOfLine = new EmptyMatchSingletonRegex(END_OF_LINE);
	
	/* CHARACTER CLASSES */
	
	// - quotes
	public static final SingletonRegex beginQuote = new SingletonRegex(BEGIN_QUOTE) {
		@Override
		public Iterator<String> iterateMatches() {
			return empty.iterateMatches();
		}
	};
	public static final SingletonRegex endQuote = new SingletonRegex(END_QUOTE) {
		@Override
		public Iterator<String> iterateMatches() {
			return empty.iterateMatches();
		}
	};
	
	public static final SingletonRegex wordBoundary = new SingletonRegex(WORD_BOUNDARY) {
		@Override
		public Iterator<String> iterateMatches() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	};
	public static final SingletonRegex nonWordBoundary = new SingletonRegex(NON_WORD_BOUNDARY) {
		@Override
		public Iterator<String> iterateMatches() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	};
	
	public static final SingletonRegex beginningOfInput = new EmptyMatchSingletonRegex(BEGINNING_OF_INPUT);
	public static final SingletonRegex endOfInput = new EmptyMatchSingletonRegex(END_OF_INPUT);
	public static final SingletonRegex endOfPreviousMatch = new SingletonRegex(END_OF_PREVIOUS_MATCH) {
		@Override
		public Iterator<String> iterateMatches() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	};
	public static final SingletonRegex endOfInputForFinalTerminator = new SingletonRegex(END_OF_PREVIOUS_MATCH_FOR_FINAL_TERMINATOR) {
		@Override
		public Iterator<String> iterateMatches() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	};
	
	public static final ShorthandCharacterClass numeric = new ShorthandCharacterClass(NUMERIC);
	public static final ShorthandCharacterClass nonNumeric = new ShorthandCharacterClass(NON_NUMERIC);
	public static final ShorthandCharacterClass word = new ShorthandCharacterClass(WORD);
	public static final ShorthandCharacterClass nonWord = new ShorthandCharacterClass(NON_WORD);
	public static final ShorthandCharacterClass whitespace = new ShorthandCharacterClass(RegexSymbols.WHITESPACE);
	public static final ShorthandCharacterClass nonWhitespace = new ShorthandCharacterClass(RegexSymbols.NON_WHITESPACE);
	
	public static final ShorthandCharacterClass horizontalWhitespace = new ShorthandCharacterClass(RegexSymbols.HORIZONTAL_WHITESPACE);
	public static final ShorthandCharacterClass nonHorizontalWhitespace = new ShorthandCharacterClass(RegexSymbols.NON_HORIZONTAL_WHITESPACE);
	
	public static final ShorthandCharacterClass verticalWhitespace = new ShorthandCharacterClass(RegexSymbols.VERTICAL_WHITESPACE);
	public static final ShorthandCharacterClass nonVerticalWhitespace = new ShorthandCharacterClass(RegexSymbols.NON_VERTICAL_WHITESPACE);
	
	interface posix {
		ShorthandCharacterClass lowerCaseAlpha = new ShorthandCharacterClass(LOWER_CASE_ALPHA);
		ShorthandCharacterClass nonLowerCaseAlpha = new ShorthandCharacterClass(NON_LOWER_CASE_ALPHA);
		ShorthandCharacterClass upperCaseAlpha = new ShorthandCharacterClass(UPPER_CASE_ALPHA);
		ShorthandCharacterClass nonUpperCaseAlpha = new ShorthandCharacterClass(NON_UPPER_CASE_ALPHA);
		ShorthandCharacterClass ascii = new ShorthandCharacterClass(ASCII);
		ShorthandCharacterClass nonAscii = new ShorthandCharacterClass(NON_ASCII);
		ShorthandCharacterClass alpha = new ShorthandCharacterClass(ALPHA);
		ShorthandCharacterClass nonAlpha = new ShorthandCharacterClass(NON_ALPHA);
		ShorthandCharacterClass digit = new ShorthandCharacterClass(Posix.DIGIT);
		ShorthandCharacterClass nonDigit = new ShorthandCharacterClass(Posix.NON_DIGIT);
		ShorthandCharacterClass alphaNumeric = new ShorthandCharacterClass(ALPLA_NUMERIC);
		ShorthandCharacterClass nonAlphaNumeric = new ShorthandCharacterClass(NON_ALPHA_NUMERIC);
		ShorthandCharacterClass punctuation = new ShorthandCharacterClass(Posix.PUNCTUATION);
		ShorthandCharacterClass nonPunctuation = new ShorthandCharacterClass(Posix.NON_PUNCTUATION);
		ShorthandCharacterClass graphical = new ShorthandCharacterClass(GRAPHICAL);
		ShorthandCharacterClass nonGraphical = new ShorthandCharacterClass(NON_GRAPHICAL);
		ShorthandCharacterClass printable = new ShorthandCharacterClass(PRINTABLE);
		ShorthandCharacterClass nonPrintable = new ShorthandCharacterClass(NON_PRINTABLE);
		ShorthandCharacterClass blank = new ShorthandCharacterClass(BLANK);
		ShorthandCharacterClass nonBlank = new ShorthandCharacterClass(NON_BLANK);
		ShorthandCharacterClass control = new ShorthandCharacterClass(Posix.CONTROL);
		ShorthandCharacterClass nonControl = new ShorthandCharacterClass(Posix.NON_CONTROL);
		ShorthandCharacterClass hexDigit = new ShorthandCharacterClass(Posix.HEX_DIGIT);
		ShorthandCharacterClass nonHexDigit = new ShorthandCharacterClass(Posix.NON_HEX_DIGIT);
		ShorthandCharacterClass whitespace = new ShorthandCharacterClass(Posix.WHITESPACE);
		ShorthandCharacterClass nonWhitespace = new ShorthandCharacterClass(Posix.NON_WHITESPACE);
	}
	
	interface java {
		ShorthandCharacterClass lowerCase = new ShorthandCharacterClass(Java.LOWER_CASE);
		ShorthandCharacterClass nonLowerCase = new ShorthandCharacterClass(Java.NON_LOWER_CASE);
		ShorthandCharacterClass upperCase = new ShorthandCharacterClass(Java.UPPER_CASE);
		ShorthandCharacterClass nonUpperCase = new ShorthandCharacterClass(Java.NON_UPPER_CASE);
		ShorthandCharacterClass whitespace = new ShorthandCharacterClass(Java.WHITESPACE);
		ShorthandCharacterClass nonWhitespace = new ShorthandCharacterClass(Java.NON_WHITESPACE);
		ShorthandCharacterClass mirrored = new ShorthandCharacterClass(MIRRORED);
		ShorthandCharacterClass nonMirrored = new ShorthandCharacterClass(NON_MIRRORED);
	}
	
	public static class unicode {
		
		public static ShorthandCharacterClass inScript(String unicodeScriptName) {
			return new ShorthandCharacterClass(Unicode.inScript(unicodeScriptName));
		}
		
		public static ShorthandCharacterClass notInScript(String unicodeScriptName) {
			return new ShorthandCharacterClass(Unicode.notInScript(unicodeScriptName));
		}
		
		public static ShorthandCharacterClass inBlock(String unicodeBlockName) {
			return new ShorthandCharacterClass(Unicode.inBlock(unicodeBlockName));
		}
		
		public static ShorthandCharacterClass notInBlock(String unicodeBlockName) {
			return new ShorthandCharacterClass(Unicode.notInBlock(unicodeBlockName));
		}
		
		public interface property {
			ShorthandCharacterClass alphabetic = new ShorthandCharacterClass(ALPHABETIC);
			ShorthandCharacterClass nonAlphabetic = new ShorthandCharacterClass(NON_ALPHABETIC);
			ShorthandCharacterClass ideographic = new ShorthandCharacterClass(IDEOGRAPHIC);
			ShorthandCharacterClass nonIdeographic = new ShorthandCharacterClass(NON_IDEOGRAPHIC);
			ShorthandCharacterClass letter = new ShorthandCharacterClass(LETTER);
			ShorthandCharacterClass nonLetter = new ShorthandCharacterClass(NON_LETTER);
			ShorthandCharacterClass lowerCase = new ShorthandCharacterClass(BinaryProperty.LOWER_CASE);
			ShorthandCharacterClass nonLowerCase = new ShorthandCharacterClass(BinaryProperty.NON_LOWER_CASE);
			ShorthandCharacterClass upperCase = new ShorthandCharacterClass(BinaryProperty.UPPER_CASE);
			ShorthandCharacterClass nonUpperCase = new ShorthandCharacterClass(BinaryProperty.NON_UPPER_CASE);
			ShorthandCharacterClass titleCase = new ShorthandCharacterClass(TITLE_CASE);
			ShorthandCharacterClass nonTitleCase = new ShorthandCharacterClass(NON_TITLE_CASE);
			ShorthandCharacterClass punctuation = new ShorthandCharacterClass(BinaryProperty.PUNCTUATION);
			ShorthandCharacterClass nonPunctuation = new ShorthandCharacterClass(BinaryProperty.NON_PUNCTUATION);
			ShorthandCharacterClass control = new ShorthandCharacterClass(BinaryProperty.CONTROL);
			ShorthandCharacterClass nonControl = new ShorthandCharacterClass(BinaryProperty.NON_CONTROL);
			ShorthandCharacterClass whitespace = new ShorthandCharacterClass(BinaryProperty.WHITESPACE);
			ShorthandCharacterClass nonWhitespace = new ShorthandCharacterClass(BinaryProperty.NON_WHITESPACE);
			ShorthandCharacterClass digit = new ShorthandCharacterClass(BinaryProperty.DIGIT);
			ShorthandCharacterClass nonDigit = new ShorthandCharacterClass(BinaryProperty.NON_DIGIT);
			ShorthandCharacterClass hexDigit = new ShorthandCharacterClass(BinaryProperty.HEX_DIGIT);
			ShorthandCharacterClass nonHexDigit = new ShorthandCharacterClass(BinaryProperty.NON_HEX_DIGIT);
			ShorthandCharacterClass joinControl = new ShorthandCharacterClass(JOIN_CONTROL);
			ShorthandCharacterClass nonJoinControl = new ShorthandCharacterClass(NON_JOIN_CONTROL);
			ShorthandCharacterClass noncharacterCodePoint = new ShorthandCharacterClass(NONCHARACTER_CODE_POINT);
			ShorthandCharacterClass nonNoncharacterCodePoint = new ShorthandCharacterClass(NON_NONCHARACTER_CODE_POINT);
			ShorthandCharacterClass assigned = new ShorthandCharacterClass(ASSIGNED);
			ShorthandCharacterClass nonAssigned = new ShorthandCharacterClass(NON_ASSIGNED);
		}
		
//		interface category {
//			// TODO Add all `readable_expressions.UnicodeCategory`.
//			ShorthandCharacterClass letter = new ShorthandCharacterClass("\\p{L}");
//			ShorthandCharacterClass upperCaseLetter = new ShorthandCharacterClass("\\p{Lu}"); // An uppercase letter (category).
//			ShorthandCharacterClass currency = new ShorthandCharacterClass("\\p{Sc}"); // A currency symbol.
//		}
//		
//		// TODO (Re)move (just included as general examples?)? Add other oftenly-used classes?
//		ShorthandCharacterClass nonGreek = new ShorthandCharacterClass("\\P{InGreek}"); // Any character except one in the Greek block (negation).
//		SingletonRegex nonUpperCaseLetter = new SingletonRegex("[\\p{L}&&[^\\p{Lu}]]"); // Any letter except an uppercase letter (subtraction).
	}
	
	public static final Repetition.Quantifier.Times zeroOrOneTimes = new Repetition.Quantifier.Times() {
		//@Override
		public int getFromTimes() {
			return 0;
		}
		
		//@Override
		public int getToTimes() {
			return 1;
		}
		
		@Override
		public String toString() {
			return ZERO_OR_ONE_TIMES;
		}
	};
	
	public static final Repetition.Quantifier.Times zeroOrMoreTimes = new Repetition.Quantifier.Times() {
		//@Override
		public int getFromTimes() {
			return 0;
		}
		
		//@Override
		public int getToTimes() {
			return Integer.MAX_VALUE;
		}
		
		@Override
		public String toString() {
			return ZERO_OR_MORE_TIMES;
		}
	};
	
	public static final Repetition.Quantifier.Times oneOrMoreTimes = new Repetition.Quantifier.Times() {
		//@Override
		public int getFromTimes() {
			return 1;
		}
		
		//@Override
		public int getToTimes() {
			return Integer.MAX_VALUE;
		}
		
		@Override
		public String toString() {
			return ONE_OR_MORE_TIMES;
		}
	};
	
	public static final Repetition.Quantifier.Type greedily = Repetition.Quantifier.Type.GREEDY;
	public static final Repetition.Quantifier.Type reluctantly = Repetition.Quantifier.Type.RELUCTANT;
	public static final Repetition.Quantifier.Type possessively = Repetition.Quantifier.Type.POSSESSIVE;
	
	/* PLACEHOLDERS */
	
	public static final Repetition.Quantifier zeroOrOneTimesGreedy = new Repetition.Quantifier(zeroOrOneTimes, greedily);
	public static final Repetition.Quantifier zeroOrMoreTimesGreedy = new Repetition.Quantifier(zeroOrMoreTimes, greedily);
	public static final Repetition.Quantifier oneOrMoreTimesGreedy = new Repetition.Quantifier(oneOrMoreTimes, greedily);
	
	public static final Repetition.Quantifier zeroOrOneTimesReluctant = new Repetition.Quantifier(zeroOrOneTimes, reluctantly);
	public static final Repetition.Quantifier zeroOrMoreTimesReluctant = new Repetition.Quantifier(zeroOrMoreTimes, reluctantly);
	public static final Repetition.Quantifier oneOrMoreTimesReluctant = new Repetition.Quantifier(oneOrMoreTimes, reluctantly);
	
	public static final Repetition.Quantifier zeroOrOneTimesPossessive = new Repetition.Quantifier(zeroOrOneTimes, possessively);
	public static final Repetition.Quantifier zeroOrMoreTimesPossessive = new Repetition.Quantifier(zeroOrMoreTimes, possessively);
	public static final Repetition.Quantifier oneOrMoreTimesPossessive = new Repetition.Quantifier(oneOrMoreTimes, possessively);
	
//	// Doesn't match anything at all.
//	public static final Regex nothing = new Regex() {
//		@Override
//		protected int getCardinality() {
//			return 1;
//		}
//		
//		@Override
//		public String toString() {
//			// Negative lookahead for empty string.
//			return "(?!)";
//		}
//		
//		@Override
//		public Iterator<String> iterateMatches() {
//			return new Iterator<String>() {
//				public boolean hasNext() {
//					return false;
//				}
//				
//				public String next() {
//					throw new NoSuchElementException();
//				}
//				
//				public void remove() {
//					throw new UnsupportedOperationException();
//				}
//			};
//		}
//	};
	
	public static final Regex empty = new Regex() {
		@Override
		protected int getCardinality() {
			return 0;
		}
		
		@Override
		public String toString() {
			return "";
		}
		
		@Override
		public Iterator<String> iterateMatches() {
			return new Iterator<String>() {
				private boolean hasNext = true;
				
				//@Override
				public boolean hasNext() {
					return hasNext;
				}
				
				//@Override
				public String next() {
					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					hasNext = false;
					return "";
				}
				
				//@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	};
	
	// TODO Add character from octal, hexadecimal, code point, ...?
	
	// TODO Rename. Could use "s" for "single" (or "s" for "string" and "c" for "character").
	
	public static SingletonCharacter string(char character) {
		return new SingletonCharacter(character);
	}
	
	public static Escaped string(String string) {
		return new Escaped(string);
	}
	
//	public static Concatenation concatenationOf(Regex... regexes) {
//		Concatenation concatenation = new Concatenation();
//		for (Regex regex : regexes) {
//			concatenation = concatenation.followedBy(regex);
//		}
//		return concatenation;
//	}
//
//	public static Choice choiceOf(Regex... regexes) {
//		Choice choice = new Choice();
//		for (Regex regex : regexes) {
//			choice = choice.or(regex);
//		}
//		return choice;
//	}
	
	public static Repetition maybe(Regex regex) {
		if (regex == null) {
			throw new NullPointerException("regex");
		}
		return regex.repeated(zeroOrOneTimes);
	}
	
	public static Repetition maybe(String string) {
		return maybe(string(string));
	}
	
	public static CharacterRange anythingInRange(char from, char to) {
		return new CharacterRange(from, to);
	}
	
	public static Group groupOf(RegexComponent component) {
		return component.asGroup();
	}
	
	public static Group groupOf(String name, RegexComponent component) {
		return component.asGroup(name);
	}
	
	public static CharacterClass classOf(String characters) {
		return classOf(characters.toCharArray());
	}
	
	public static CharacterClass classOf(char... characters) {
		return classOf(eitherOf(characters));
	}
	
	@Deprecated
	public static CharacterClass classOf(CharacterClass characterClass) {
		// No harm done in allowing this... But document it's unneededness.
		if (characterClass == null) {
			throw new NullPointerException("characterClass");
		}
		return characterClass;
	}
	
	public static CharacterClass classOf(CharacterClassComponent component) {
		return component.toRegex();
	}
	
	public static CharacterClass anythingExcept(CharacterClassComponent component) {
		return new CharacterClass(component, true, null);
	}
	
	public static CharacterClass anythingExcept(char character) {
		return anythingExcept(string(character));
	}
	
	public static CharacterEnum eitherOf(char... characters) {
		if (characters == null) {
			throw new NullPointerException("characters");
		}
		CharacterEnum characterEnum = new CharacterEnum();
		for (char character : characters) {
			characterEnum.or(string(character));
		}
		return characterEnum;
	}
	
	public static CharacterEnum eitherOf(String characters) {
		return eitherOf(characters.toCharArray());
	}
	
	// TODO FUTURE (once printer is extracted to visitor): Let output of repeaters be what one would naively expect from
	//      the name of the method that created them (i.e. respect indicated intention and remove "cleverness") unless
	//      the printer is explicitly instructed to transform/optimize.
	
	public static Repetition.Quantifier.Times times(final int times) {
		if (times < 0) {
			throw new IllegalArgumentException(String.format("times = %d < 0", times));
		}
		
		return new Repetition.Quantifier.Times() {
			//@Override
			public int getFromTimes() {
				return times;
			}
			
			//@Override
			public int getToTimes() {
				return times;
			}
			
			@Override
			public String toString() {
				return "{" + times + "}";
			}
		};
	}
	
	public static Repetition.Quantifier.Times atLeastTimes(final int fromTimes) {
		return betweenTimes(fromTimes, Integer.MAX_VALUE);
	}
	
	public static Repetition.Quantifier.Times atMostTimes(final int toTimes) {
		return betweenTimes(0, toTimes);
	}
	
	public static Repetition.Quantifier.Times betweenTimes(final int fromTimes, final int toTimes) {
		if (fromTimes == toTimes) {
			return times(fromTimes);
		}
		
		if (fromTimes == 0) {
			if (toTimes == 1) {
				return zeroOrOneTimes;
			} else if (toTimes == Integer.MAX_VALUE) {
				return zeroOrMoreTimes;
			}
		} else if (fromTimes == 1 && toTimes == Integer.MAX_VALUE) {
			return oneOrMoreTimes;
		}
		if (fromTimes > toTimes) {
			throw new IllegalArgumentException(String.format("fromTimes = %d > %d = toTimes", fromTimes, toTimes));
		}
		
		return new Repetition.Quantifier.Times() {
			//@Override
			public int getFromTimes() {
				return fromTimes;
			}
			
			//@Override
			public int getToTimes() {
				return toTimes;
			}
			
			@Override
			public String toString() {
				if (fromTimes == 0) {
					return "{," + toTimes + "}";
				}
				if (toTimes == Integer.MAX_VALUE) {
					return "{" + fromTimes + ",}";
				}
				return "{" + fromTimes + "," + toTimes + "}";
			}
		};
	}
	
	public static Regex literal(Pattern pattern) {
		return literal(pattern.pattern());
	}
	
	public static Regex literal(@Language("RegExp") final String pattern) {
		return Parser.parseRegex(pattern);
//		return new Regex() {
//			@Override
//			public String toString() {
//				return pattern;
//			}
//			
//			@Override
//			public Iterator<String> iterateMatches() {
//				throw new UnsupportedOperationException("Not yet implemented; need parser");
//			}
//			
//			protected int getCardinality() {
//				if (pattern.length() == 0) {
//					return 0;
//				}
//				return 1;
//			}
//		};
	}
	
	public static SingletonRegex backReference(Group group) {
		if (group == null) {
			throw new NullPointerException("group");
		}
		
		String groupName = group.getGroupName();
		if (groupName != null) {
			return backReference(groupName);
		}
		
		// TODO Should store group numbers in future printing visitor in order to implement this.
		throw new UnsupportedOperationException("Unnamed back-reference to group is not yet implemented");
	}
	
	public static SingletonRegex backReference(int groupNumber) {
		String backReference = RegexSymbols.backReference(groupNumber);
		return new SingletonRegex(backReference) {
			@Override
			public Iterator<String> iterateMatches() {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		};
	}
	
	public static SingletonRegex backReference(String groupName) {
		if (groupName == null) {
			throw new NullPointerException("groupName");
		}
		return new SingletonRegex(RegexSymbols.backReference(groupName)) {
			@Override
			public Iterator<String> iterateMatches() {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		};
	}
}
