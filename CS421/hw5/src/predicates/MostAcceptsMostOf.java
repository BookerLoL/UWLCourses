package predicates;

import java.util.Collection;

public class MostAcceptsMostOf<T> implements Predicate<Collection<Predicate<T>>> {
	private Collection<? extends T> reference;
	
	public MostAcceptsMostOf(Collection<? extends T> reference) {
		this.reference = reference;
	}

	@Override
	public boolean accepts(Collection<Predicate<T>> t) {
		if (t == null) {
			return false;
		}
		
		final int MIN_PREDICATE_NEEDED = t.size()/2;
		final int MIN_COUNT_NEEDED = reference.size() / 2;
		int accepted = 0, count;
		
		for (Predicate<T> predicate : t) {
			count = 0;
			for (T element : reference) {
				if (predicate.accepts(element)) {
					count++;
				}
			}
			if (MIN_COUNT_NEEDED <= count) {
				accepted++;
			}
		}
		
		return accepted < MIN_PREDICATE_NEEDED ? false : true;
	}
}