package predicates;

import java.util.Collection;

public class SubsetOf<T> implements Predicate<Collection<? extends T>> {
	private Collection<T> reference;

	public SubsetOf(Collection<T> list) {
		this.reference = list;
	}

	@Override
	public boolean accepts(Collection<? extends T> t) {
		if (t == null) {
			return false;
		}
		return reference.containsAll(t);
	}
}
