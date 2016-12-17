package readable_expressions.parse;

public class RegexParseException extends RuntimeException {
	private static final long serialVersionUID = 6698788289158552499L;
	
	private final int index;
	
	public RegexParseException(String message, int index) {
		super(message);
		
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
