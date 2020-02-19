package predicates;

public class SimilarTo<T> implements Predicate<T> {
	protected T referenceObject;
	protected Metric<T> metric;
	protected double threshold;
	
	public SimilarTo(T referenceObject, Metric<T> metric, double threshold) {
		this.referenceObject = referenceObject;
		this.metric = metric;
		this.threshold = threshold;
	}
	
	@Override
	public boolean accepts(T t) {
		return metric.distance(referenceObject, t) <= threshold;
	}
}
