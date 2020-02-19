package predicates;

public class GreaterThan<T extends Comparable<T>> implements Predicate<T> {
	private T reference;
	
	public GreaterThan(T reference) {
		this.reference = reference;
	}
	
	@Override
	public boolean accepts(T t) {
		if (t == null) {
			return false;
		}
		return reference.compareTo(t) < 0 ? true : false;
	}

}
