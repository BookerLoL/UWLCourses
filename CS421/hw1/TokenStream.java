import java.util.Enumeration;

public class TokenStream implements Enumeration<String> {
	private String[] tokens;
	private int position;

	public TokenStream(String tokens) {
		this(tokens, Constants.DEFAULT_DELIMITER);
	}

	public TokenStream(String tokens, String delimiter) {
		if (!isInvalidInput(tokens) && !isInvalidInput(delimiter)) {
			this.tokens = tokens.split(delimiter);
			position = 0;
		}
	}

	private boolean isInvalidInput(String input) {
		if (input == null || input.length() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String nextElement() {
		String token;
		if (!hasMoreElements()) {
			token = null;
		} else {
			token = tokens[position];
			++position;
		}
		return token;
	}

	@Override
	public boolean hasMoreElements() {
		return position < tokens.length;
	}
}
