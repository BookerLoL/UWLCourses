package predicates;

import java.util.Collection;

public class AcceptsAllOf<T> implements Predicate<Predicate<T>> {
	private Collection<? extends T> reference;
	
	public AcceptsAllOf(Collection<? extends T> reference) {
		this.reference = reference;
	}

	@Override
	public boolean accepts(Predicate<T> t) {
		if (t == null) {
			return false;
		}
		for (T element : reference) {
			if (!t.accepts(element)) {
				return false;
			}
		}
		return true;
	}
}
