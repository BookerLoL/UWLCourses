package predicates;

import java.util.Collection;

public class AcceptsSomeOf<T> implements Predicate<Predicate<T>> {
	private Collection<? extends T> reference;
	
	public AcceptsSomeOf(Collection<? extends T> reference) {
		this.reference = reference;
	}

	@Override
	public boolean accepts(Predicate<T> t) {
		if (t == null) {
			return false;
		}
		for (T element : reference) {
			if (t.accepts(element)) {
				return true;
			}
		}
		return false;
	}
}
