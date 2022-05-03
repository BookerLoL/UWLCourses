package predicates;

import java.util.Collection;

public class AcceptsMostOf<T> implements Predicate<Predicate<T>> {
	private Collection<? extends T> reference;

	public AcceptsMostOf(Collection<? extends T> reference) {
		this.reference = reference;
	}

	@Override
	public boolean accepts(Predicate<T> t) {
		if (t == null) {
			return false;
		}
		final int MIN_NEEDED = reference.size() / 2;
		int count = 0;
		for (T element : reference) {
			if (t.accepts(element)) {
				count++;
			}
		}
		return count >= MIN_NEEDED;
	}
}
