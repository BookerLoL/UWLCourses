package bitstring;

public interface Constants {
	int EXPECTED_ARG_AMOUNT = 1;
	String DEFAULT_DELIMITER = " ";
	String UNDEFINED = "undefined";
	String OPEN_PARENTHESIS = "(";
	String CLOSE_PARENTHESIS = ")";
	String CONTATENATION = "<>";
	String EXCLUSIVE_OR = "^";
	String ZIPPER  = "//";
	String NEGATION = "!";
	String ROTATION = "@";
	char BIT_0 = '0';
	char BIT_1 = '1';
	int MAX_BIT_LENGTH = 2019;
	int MAX_VAR_LENGTH = 1;
	char VAR_ALPHABET_START = 'a';
	char VAR_ALPHABET_END = 'z';
	int MININUM_VALUES_IN_ENVIRONMENT = 1;
	int INIT_ENVIRONMENT_PARENTHESIS = -1;
	Environment INIT_ENVIRONMENT_PARENT = null;
	String INVALID_INPUT = "Invalid Input\t";
	String ONE_INPUT_FILE = "Should only have 1 input in the commandline which should be a bit file";
	String ONE_EXPRESSION_LEFT = "Environment should have only one value left at the end";
}
