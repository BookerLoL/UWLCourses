package predicates;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public final class PredicateUtilities {
	public static <T> Collection<T> filter(Collection<? extends T> collection, Predicate<T> predicate) {
		Collection<T> acceptedCollection = new LinkedList<>();
		for (T element : collection) {
			if (predicate.accepts(element)) {
				acceptedCollection.add(element);
			}
		}
		return acceptedCollection;
	}
}
