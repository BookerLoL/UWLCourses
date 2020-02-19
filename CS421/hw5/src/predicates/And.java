package predicates;


public class And<T> implements Predicate<T> {
	private Predicate<T>[] referencePredicates;
	
	@SafeVarargs
	public And(Predicate<T> ... referencePredicates) {
		this.referencePredicates = referencePredicates;
	}
	
	@Override
	public boolean accepts(T t) {
		for (Predicate<T> predicate : referencePredicates) {
			if (!predicate.accepts(t)) {
				return false;
			}
		}
		return true;
	}

}
