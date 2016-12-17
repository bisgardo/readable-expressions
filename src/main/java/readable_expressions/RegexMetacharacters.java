package readable_expressions;

public interface RegexMetacharacters {
	
	// TODO Add repeater number ('{', '}'), character groups, ...
	
	// TODO Document the meaning of the strings and constructs in `RegexSymbols` that references them.
	
	// TODO Add small helper program that can search the fields (reflectively) and output documentation?
	
	// Extra meaning: Concatenation.
	String EMPTY = "";
	
	char STAR = '*';
	
	char PLUS = '+';
	
	char QUESTION_MARK = '?';
	
	char LEFT_PARENTHESIS = '(';
	
	char RIGHT_PARENTHESIS = ')';
	
	char COLON = ':';
	
	char LESS_THAN = '<';
	
	char GREATER_THAN = '>';
	
	char EQUALS = '=';
	
	char EXCLAMATION_MARK = '!';
	
	char DOT = '.';
	
	char CARET = '^';
	
	char DOLLAR_SIGN = '$';
	
}
