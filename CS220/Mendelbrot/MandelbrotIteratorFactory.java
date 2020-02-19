import java.util.Iterator;
/** 
 *  A factory that creates MandelbrotIterators using the factory template
 *  
 *  @author Ethan Booker
 *  @version 1.0
 *  @since 11/14/2017
 */
public class MandelbrotIteratorFactory implements Factory<Iterator<Complex>,Complex>

{
    private final int maximumIterations;
    
    /**
     *  Constructor
     *  
     *  @param int The maximum iterations of the function that is inclusive from 0 to maxiumum iterations
     */
    MandelbrotIteratorFactory(final int maximumIterations)
    {
        this.maximumIterations = maximumIterations + 1;
    }
    
    /**
     * A factory method that creates Function1Iterators 
     * 
     * @param Complex a number used as a MandelbrotBase value
     * @return Iterator<Complex> The given Complex and iterations are used to create an iterator of type Complex
     */
    public Iterator<Complex> build(final Complex c)
    {
        return new Function1Iterator(new MandelbrotBase(c), new Complex(0,0), maximumIterations);
    }
}
