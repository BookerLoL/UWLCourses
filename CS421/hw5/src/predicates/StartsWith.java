package predicates;

public class StartsWith<T extends CharSequence> implements Predicate<T> {
	private T prefix;
	
	public StartsWith(T prefix) {
		this.prefix = prefix;
	}
	@Override
	public boolean accepts(T t) {
		if(t == null || t.getClass() != prefix.getClass()) {
			return false;
		}
		return t.toString().toUpperCase().startsWith(prefix.toString().toUpperCase());
	}
}
