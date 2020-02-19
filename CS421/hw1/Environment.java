import java.util.*;

public class Environment {
	protected Map<String, String> variables;
	protected Deque<String> bits;
	protected Environment parent;
	protected int endParenthesis;
	protected boolean finishedNamespace;

	public Environment(Environment parent, int endParenthesis) {
		variables = new HashMap<>();
		bits = new LinkedList<>();
		this.parent = parent;
		this.endParenthesis = endParenthesis;
		finishedNamespace = false;
	}

	protected String getVariableValue(String var) {
		String output = Constants.UNDEFINED;
		Environment temp = this;
		while (temp != null) {
			if (temp.finishedNamespace && temp.variables.containsKey(var)) {
				output = temp.variables.get(var);
				break;
			}
			temp = temp.parent;
		}
		return output;
	}
}
