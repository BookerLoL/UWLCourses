/**
 * Acts as an MandelbrotIterator that ensures that iterator next is correctly
 * testing a Mendelbrot function
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 11/14/2017
 */
public class MandelbrotIterationCounter extends IterationCounter<Complex> {
    private final double divergenceMagnitude;

    /**
     * Constructor
     * 
     * @param double The divergence value used for determining the satisfaction of
     *               testItem
     * @param int    maximum The allowed iterations for the function iterator
     */
    public MandelbrotIterationCounter(final double divergenceMagnitude, final int maximum) {
        super(new MandelbrotIteratorFactory(maximum), maximum);
        this.divergenceMagnitude = divergenceMagnitude;
    }

    /**
     * Template method which expresses the condition which each
     * result of next() might satisfy.
     * 
     * @param initial the seed value from the iterator and checks the magnitude
     *                against the divergence
     * @return true if initial magnitute is less than the divergence magnitude
     *         otherwise false
     */
    public boolean testItem(final Complex initial) {
        return initial.getMagnitude() < divergenceMagnitude;
    }
}
