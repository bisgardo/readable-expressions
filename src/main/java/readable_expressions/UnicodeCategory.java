package readable_expressions;

/**
 * From <a href="https://en.wikipedia.org/wiki/Template:General_Category_(Unicode)">Wikipedia</a>.
 */
public interface UnicodeCategory {
	
	enum Letter {
		UPPER_CASE("Lu"),
		LOWER_CASE("Ll"),
		TITLE_CASE("Lt"),
		MODIFIER("Lm"),
		OTHER("Lo");
		
		public final String string;
		
		Letter(String string) {
			this.string = string;
		}
	}
	
	enum Mark {
		NONSPACING("Mn"),
		SPACING_COMBINING("Mc"),
		ENCLOSING("Me");
		
		public final String string;
		
		Mark(String string) {
			this.string = string;
		}
	}
	
	enum Number {
		DECIMAL_DIGIT("Nd"),
		LETTER("Nl"),
		OTHER("No");
		
		public final String string;
		
		Number(String string) {
			this.string = string;
		}
	}
	
	enum Punctuation {
		CONNECTOR("Pc"),
		DASH("Pd"),
		OPEN("Ps"),
		CLOSE("Pe"),
		INITIAL_QUOTE("Pi"),
		FINAL_QUOTE("Pf"),
		OTHER("Po");
		
		public final String string;
		
		Punctuation(String string) {
			this.string = string;
		}
	}
	
	enum Symbol {
		MATH("Sm"),
		CURRENCY("Sc"),
		MODIFIER("Sk"),
		OTHER("So");
		
		public final String string;
		
		Symbol(String string) {
			this.string = string;
		}
	}
	
	enum Separator {
		SPACE("Zs"),
		LINE("Zl"),
		PARAGRAPH("Zp");
		
		public final String string;
		
		Separator(String string) {
			this.string = string;
		}
	}
	
	enum Other {
		CONTROL("Cc"),
		FORMAT("Cf"),
		SURROGATE("Cs"),
		PRIVATE_USE("Co"),
		NOT_ASSIGNED("Cn");
		
		public final String string;
		
		Other(String string) {
			this.string = string;
		}
	}
}
