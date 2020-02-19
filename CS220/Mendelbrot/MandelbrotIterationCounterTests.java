import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class MandelbrotIterationCounter.
 *
 * @author  Ethan Booker
 * @version 1.0
 * @since 11/14/2017
 */
public class MandelbrotIterationCounterTests
{
    @Test(timeout=1000) public void testTestItem()
    {
        //Initializing test variables 
        final double divergenceMagnitude = 4;
        final MandelbrotIterationCounter iter = new MandelbrotIterationCounter(divergenceMagnitude, 5);
        final Complex seed = new Complex(2,2);
        
        //Compares the seed magntiude to see if it exceeds the divergence magnitude
        if(seed.getMagnitude() >= divergenceMagnitude)
        {
            assertFalse(iter.testItem(seed));
        }
        else 
        {
            assertTrue(iter.testItem(seed));
        }
     
    }
}
