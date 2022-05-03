import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
/**
 * The test class IterationCounter.
 *
 * @author Ethan Booker
 * @version 11/14/2017
 */
public class IterationCounterTests
{
    @Test(timeout=1000) public void testCountPassingIterations()
    {
      //Initialialization of variables 
      int counter = 0;
      final int maximum = 10;
      final double divergence = 10;
      final Complex seed = new Complex(-3,3);
      final IterationCounter<Complex> tester = new MandelbrotIterationCounter(divergence, maximum);
      final MandelbrotIteratorFactory fact = new MandelbrotIteratorFactory(maximum);
      final Iterator<Complex> test = fact.build(seed);
      
      /*
       * Ensures the iterator is valid to test the item 
       * Ensures that the counter doesn't continue on past the maximum 
       * Ensures that there are ierations left
       * Ensures that the given iterator has a valid next() 
       */
      while(counter < maximum && test.hasNext() && tester.testItem(test.next()))
      {
          counter++;
      } 
      assertEquals("Counter should be the same as maximum: ", counter, tester.countPassingIterations(seed));
    }
    
   
    
}
