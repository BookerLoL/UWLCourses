package predicates;

import java.awt.Color;

public class SimilarColor extends SimilarTo<Color> {

	public SimilarColor(Color referenceObject, double threshold) {
		super(referenceObject, null, threshold);
	}
	
	@Override
	public boolean accepts(Color t) {
		int diffRed = Math.abs(referenceObject.getRed() - t.getRed()),
			diffGreen = Math.abs(referenceObject.getGreen() - t.getGreen()), 
			diffBlue = Math.abs(referenceObject.getBlue() - t.getBlue());
		
		return (diffRed + diffGreen + diffBlue) <= threshold;
	}
}
