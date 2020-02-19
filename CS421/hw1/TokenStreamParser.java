import java.util.*;

public class TokenStreamParser {
	protected Enumeration<String> tokenizer;

	public TokenStreamParser() {
		tokenizer = new TokenStream(null);
	}

	public void setInput(String string) {
		tokenizer = new TokenStream(string);
	}

	public String evaluate() throws IllegalArgumentException {
		Stack<Environment> environments = new Stack<>();
		Environment currentEnv = new Environment(Constants.INIT_ENVIRONMENT_PARENT,
				Constants.INIT_ENVIRONMENT_PARENTHESIS);
		environments.push(currentEnv);
		int parenthesisCount = 0;

		while (tokenizer.hasMoreElements()) {
			String firstToken = tokenizer.nextElement();
			if (firstToken.equals(Constants.OPEN_PARENTHESIS)) {
				String secondToken = tokenizer.nextElement();
				if (secondToken.equals(Constants.OPEN_PARENTHESIS)) {
					currentEnv = new Environment(currentEnv, parenthesisCount);
					environments.push(currentEnv);
					parenthesisCount += 2;
				} else if (isVariable(secondToken) || isOperator(secondToken)) {
					++parenthesisCount;
					currentEnv.bits.add(secondToken);
				} else {
					throw new IllegalArgumentException(Constants.INVALID_INPUT + firstToken);
				}
			} else if (firstToken.equals(Constants.CLOSE_PARENTHESIS)) {
				--parenthesisCount;
				currentEnv = evaluateEndOfParenthesis(currentEnv, environments, parenthesisCount);
			} else {
				if (!isBitLiteral(firstToken) && !isVariable(firstToken) && !isUndefined(firstToken)) {
					throw new IllegalArgumentException(Constants.INVALID_INPUT + firstToken);
				} else {
					if (isAboveLengthLimit(firstToken.length())) {
						currentEnv.bits.add(Constants.UNDEFINED);
					} else {
						currentEnv.bits.add(firstToken);
					}
				}
			}
		}
		return getEvironmentValueAfterEnd(currentEnv);
	}

	private Environment endEnvironmentTransition(Environment currentEnv, Stack<Environment> environments) {
		String endEvaluatedValue = getEvironmentValueAfterEnd(currentEnv);
		environments.pop();
		currentEnv = environments.peek();
		currentEnv.bits.add(endEvaluatedValue);
		return currentEnv;
	}

	private Environment evaluateEndOfParenthesis(Environment currentEnv, Stack<Environment> environments,
			int parenthesis) {
		if (isEndOfEnvironment(parenthesis, currentEnv)) {
			currentEnv = endEnvironmentTransition(currentEnv, environments);
		} else {
			if (parenthesis == currentEnv.endParenthesis + 1) {
				currentEnv.finishedNamespace = true;
			}
			String evaluatedValue = simulateOneEvaluation(currentEnv);
			if (evaluatedValue != null) {
				currentEnv.bits.add(evaluatedValue);
			}
		}
		return currentEnv;
	}

	private boolean isEndOfEnvironment(int currentParenthesis, Environment env) {
		return currentParenthesis == env.endParenthesis;
	}

	private String getEvironmentValueAfterEnd(Environment currentEnv) throws IllegalStateException {
		if (currentEnv.bits.isEmpty()) {
			return Constants.UNDEFINED;
		} else if (currentEnv.bits.size() > Constants.MININUM_VALUES_IN_ENVIRONMENT) {
			throw new IllegalStateException(Constants.ONE_EXPRESSION_LEFT);
		}

		return isVariableGetValue(currentEnv.bits.pop(), currentEnv);
	}

	protected String simulateOneEvaluation(Environment currentEnv) {
		String output = null;
		if (currentEnv.bits.size() > Constants.MININUM_VALUES_IN_ENVIRONMENT) {
			String last = currentEnv.bits.removeLast();
			String secondLast = currentEnv.bits.removeLast();
			if (!isBinary(secondLast)) {
				last = isVariableGetValue(last, currentEnv);
				if (isUnary(secondLast)) {
					output = evaluateUnaryOperation(secondLast, last);
				} else {
					if (!currentEnv.bits.isEmpty() && isBinary(currentEnv.bits.getLast())) {
						String thirdLast = currentEnv.bits.removeLast();
						secondLast = isVariableGetValue(secondLast, currentEnv);
						output = evaluateBinaryOperation(thirdLast, secondLast, last);
					} else {
						currentEnv.variables.put(secondLast, last);
					}
				}
			} else {
				currentEnv.bits.add(secondLast);
				currentEnv.bits.add(last);
			}
		} else if (currentEnv.bits.size() == Constants.MININUM_VALUES_IN_ENVIRONMENT) {
			output = isVariableGetValue(currentEnv.bits.removeFirst(), currentEnv);
		}
		if (output != null && isAboveLengthLimit(output.length())) {
			output = Constants.UNDEFINED;
		}
		return output;
	}

	private String evaluateBinaryOperation(String operator, String left, String right) {
		String output;
		if (this.isUndefined(left) || this.isUndefined(right)) {
			output = Constants.UNDEFINED;
		} else if (operator.equals(Constants.CONTATENATION)) {
			output = concatenate(left, right);
		} else if (operator.equals(Constants.ZIPPER)) {
			output = zipper(left, right);
		} else {
			output = exclusiveOr(left, right);
		}
		return output;
	}

	private String evaluateUnaryOperation(String operator, String input) {
		String output;
		if (isUndefined(input)) {
			output = Constants.UNDEFINED;
		} else if (operator.equals(Constants.NEGATION)) {
			output = negation(input);
		} else {
			output = rotational(input);
		}
		return output;
	}

	public boolean isBinary(String token) {
		return token.equals(Constants.CONTATENATION) || token.equals(Constants.ZIPPER)
				|| token.equals(Constants.EXCLUSIVE_OR);
	}

	public boolean isUnary(String token) {
		return token.equals(Constants.NEGATION) || token.equals(Constants.ROTATION);
	}

	public boolean isUndefined(String token) {
		return token.equals(Constants.UNDEFINED);
	}

	public boolean isVariable(String token) {
		return token.length() == Constants.MAX_VAR_LENGTH && token.charAt(0) >= Constants.VAR_ALPHABET_START
				&& token.charAt(0) <= Constants.VAR_ALPHABET_END;
	}

	public String isVariableGetValue(String token, Environment current) {
		if (isVariable(token)) {
			return current.getVariableValue(token);
		}
		return token;
	}

	public boolean isBitLiteral(String token) {
		for (char bit : token.toCharArray()) {
			if (bit != Constants.BIT_0 && bit != Constants.BIT_1) {
				return false;
			}
		}
		return true;
	}

	public boolean isOperator(String token) {
		switch (token) {
		case Constants.CONTATENATION:
		case Constants.EXCLUSIVE_OR:
		case Constants.ZIPPER:
		case Constants.NEGATION:
		case Constants.ROTATION:
			return true;
		default:
			return false;
		}
	}

	protected String concatenate(String left, String right) {
		if (isAboveLengthLimit(left.length() + right.length())) {
			return Constants.UNDEFINED;
		}
		StringBuilder concat = new StringBuilder(left.length() + right.length());
		concat.append(left).append(right);
		return concat.toString();
	}

	public boolean isAboveLengthLimit(int length) {
		return length > Constants.MAX_BIT_LENGTH;
	}

	protected String exclusiveOr(String left, String right) {
		int diff = left.length() - right.length();
		int max = diff >= 0 ? left.length() : right.length();
		StringBuilder builder = new StringBuilder(max);
		int leftIdx = 0;
		int rightIdx = 0;
		if (diff > 0) {
			leftIdx = exclusiveOrOffset(builder, left, diff);
		} else if (diff < 0) {
			rightIdx = exclusiveOrOffset(builder, right, Math.abs(diff));
		}

		while (leftIdx != left.length()) {
			if (left.charAt(leftIdx) != right.charAt(rightIdx)) {
				builder.append(Constants.BIT_1);
			} else {
				builder.append(Constants.BIT_0);
			}
			leftIdx++;
			rightIdx++;
		}
		return builder.toString();
	}

	private int exclusiveOrOffset(StringBuilder builder, String bits, int diff) {
		int offset = 0;
		while (diff != 0) {
			if (bits.charAt(offset) != Constants.BIT_0) {
				builder.append(Constants.BIT_1);
			} else {
				builder.append(Constants.BIT_0);
			}
			offset++;
			diff--;
		}
		return offset;
	}

	protected String zipper(String left, String right) {
		if (isAboveLengthLimit(left.length() + right.length())) {
			return Constants.UNDEFINED;
		}

		int diff = left.length() - right.length();
		int max = diff >= 0 ? left.length() : right.length();
		StringBuilder builder = new StringBuilder(max + max);
		int leftIdx = 0;
		int rightIdx = 0;
		if (diff > 0) {
			leftIdx = zipperOffset(builder, left, diff);
		} else if (diff < 0) {
			rightIdx = zipperOffset(builder, right, diff);
		}
		while (leftIdx < left.length()) {
			builder.append(left.charAt(leftIdx));
			builder.append(right.charAt(rightIdx));
			leftIdx++;
			rightIdx++;
		}
		return builder.toString();
	}

	private int zipperOffset(StringBuilder builder, String bits, int diff) {
		int offset = 0;
		if (diff > 0) {
			while (diff != 0) {
				builder.append(bits.charAt(offset));
				builder.append(Constants.BIT_0);
				offset++;
				diff--;
			}
		} else {
			while (diff != 0) {
				builder.append(Constants.BIT_0);
				builder.append(bits.charAt(offset));
				offset++;
				diff++;
			}
		}
		return offset;
	}

	protected String negation(String token) {
		StringBuilder builder = new StringBuilder(token.length());
		for (int i = 0; i < token.length(); ++i) {
			if (token.charAt(i) == Constants.BIT_0) {
				builder.append(Constants.BIT_1);
			} else {
				builder.append(Constants.BIT_0);
			}
		}
		return builder.toString();
	}

	protected String rotational(String token) {
		StringBuilder builder;
		int offset = 0;
		boolean isOdd = token.length() % 2 == 1;
		if (isOdd) {
			++offset;
			builder = new StringBuilder(token.length() + offset);
			builder.append(token.charAt(0)).append(Constants.BIT_0);
		} else {
			builder = new StringBuilder(token.length());
		}
		for (int i = offset; i < token.length() - 1; i += 2) {
			builder.append(token.charAt(i + 1));
			builder.append(token.charAt(i));
		}
		return builder.toString();
	}
}
