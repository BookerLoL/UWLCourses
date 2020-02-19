import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import java.util.NoSuchElementException;
/**
 * The test class Function1Iterator.
 *
 * @author  Ethan Booker
 * @version 1.0
 * @since 10/31/2017
 */
public class Function1IteratorTests
{
    /**
     * Needed for precision testing
     */
    private final static double EPSILON  = 1E-6;
    private final Random r = new Random();
     
    @Test(timeout=1000) public void testHasNext()
    {
        final Complex test1 = new Complex(0,0);
        final Complex test2 = new Complex(-3, 3);
        final MandelbrotBase tester1 = new MandelbrotBase(test1);
        final Function1Iterator<Complex> func = new Function1Iterator(tester1, test2);
        assertTrue("hasNext should always be true with no inputted iteration", func.hasNext());
    }
    
    @Test(timeout=1000) public void testNext()
    {
        final Complex[] test = new CreateNumbers().createComplexNumbers();
        final Complex[] test2 = new CreateNumbers().createComplexNumbers();
        
        /*
         * Iterators through different values of Complex and Mandelbrot base numbers.
         * Checks to see if next on multiple next iterations 
         */
        for(int i = 0; i < test.length; i++)
        {
            final MandelbrotBase b = new MandelbrotBase(test2[i]);
            final Function1Iterator<Complex> func = new Function1Iterator(b, test[i]);
            
            //Get the initial value
            Complex next = func.next();
            
            assertEquals("Next() should be original complex, real component: ", test[i].getReal(), next.getReal(), EPSILON);
            assertEquals("Next() should be original complex, imaginary component: ", test[i].getImaginary(), next.getImaginary(), EPSILON);
            
            //Get the next inital value
            next = func.next();
            
            assertEquals("Next() should be applied, real component: ", b.apply(test[i]).getReal(), next.getReal(), EPSILON);
            assertEquals("Next() should be applied, imaginary component: ", b.apply(test[i]).getImaginary(), next.getImaginary(), EPSILON);
            
            //Get the next next initial value
            next = func.next();
            
            assertEquals("Next() should be 2nd applied complex, real component: ", b.apply(b.apply(test[i])).getReal(), next.getReal(), EPSILON);
            assertEquals("Next() should be 2nd applied complex, imaginary component: ", b.apply(b.apply(test[i])).getImaginary(), next.getImaginary(), EPSILON);    
        }
    }
    
    @Test(timeout=1000) public void testHasNextFalse()
    {
        final int iterations = 0;
        final Complex test1 = new Complex(0,0);
        final Complex test2 = new Complex(-3, 3);
        final MandelbrotBase tester1 = new MandelbrotBase(test1);
        final Function1Iterator<Complex> func = new Function1Iterator(tester1, test2, iterations);
        
        //Ensures that when iterations are exhausted hasNext should be false 
        assertFalse("There are no more allowed next calls", func.hasNext());
    }
    
    @Test(timeout=1000) public void testHasNextTrueCapped()
    {
        final int iterations = 4;
        final Complex test1 = new Complex(0,0);
        final Complex test2 = new Complex(-3, 3);
        final MandelbrotBase tester1 = new MandelbrotBase(test1);
        final Function1Iterator<Complex> func = new Function1Iterator(tester1, test2, iterations);
        
        //Ensures that hasNext calls works when there are iterations available 
        assertTrue("The function should be be able to call next successfully", func.hasNext());
        assertTrue("The function should be be able to call next successfully", func.hasNext());
        assertTrue("The function should be be able to call next successfully", func.hasNext());
        assertTrue("The function should be be able to call next successfully", func.hasNext());
    }
    
    @Test(timeout=1000) public void testNextWithIterations()
    {
        final int iterations = 4;
        final Complex test1 = new Complex(.1,-.1);
        final Complex test2 = new Complex(-3, 3);
        final MandelbrotBase tester1 = new MandelbrotBase(test1);
        final Function1Iterator<Complex> func = new Function1Iterator(tester1, test2, iterations);
        Complex next; 
        
        //Get the initial value
        next = func.next();
             
        assertEquals("Next() should be original complex, real component: ", test2.getReal(), next.getReal(), EPSILON);
        assertEquals("Next() should be original complex, imaginary component: ", test2.getImaginary(), next.getImaginary(), EPSILON);    
            
           
        //Get the next initial value
        next = func.next();
                
        assertEquals("Next() should be the applied, real component: ", tester1.apply(test2).getReal(), next.getReal(), EPSILON);
        assertEquals("Next() should be the applied, imaginary component: ", tester1.apply(test2).getImaginary(), next.getImaginary(), EPSILON);    
           
        //Get the 2nd next initial value
        next = func.next();
                
        assertEquals("Next() should be the 2nd applied, real component: ", tester1.apply(tester1.apply(test2)).getReal(), next.getReal(), EPSILON);
        assertEquals("Next() should be the 2nd applied, imaginary component: ", tester1.apply(tester1.apply(test2)).getImaginary(), next.getImaginary(), EPSILON);    
        
        //Get the 3rd next initial value
        next = func.next(); 
        assertEquals("Next() should be the 3rd applied, real component: ", tester1.apply(tester1.apply(tester1.apply(test2))).getReal(), next.getReal(), EPSILON);
        assertEquals("Next() should be the 3rd applied, imaginary component: ", tester1.apply(tester1.apply(tester1.apply(test2))).getImaginary(), next.getImaginary(), EPSILON); 
        
        //Expects the next next call to throw a NoSuchElementException 
        try
        {
            next = func.next();
            fail("Expected NoSuchElementException");
        }
        catch(NoSuchElementException e)
        {
        }       
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testNextError()
    {
        final Function1Iterator test = new Function1Iterator(new CreateNumbers().createMandelbrotBaseNumbers()[0],
                                     new CreateNumbers().createComplexNumbers()[0], 4);
        
        //Ensures that NoSuchElementException occurs when iterations there are no more iterations 
        test.next();
        test.next();
        test.next();
        test.next();
        test.next();
    }
    
}
