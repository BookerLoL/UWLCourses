import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;
import java.util.Iterator;
/**
 * The test class MandelbrotIteratorFactory.
 *
 * @author  Ethan Booker
 * @version 1.0
 * @since 11/14/2017
 */
public class MandelbrotIteratorFactoryTests
{
    private final static double EPSILON = 1E-6;
    
    @Test(timeout=1000) public void testBuildComplexInitial()
    {
        //Initialization of test variables 
        final MandelbrotIteratorFactory test = new MandelbrotIteratorFactory(2);
        final Complex base = new Complex(5, 5);
        final MandelbrotBase base2 = new MandelbrotBase(base); 
        final Iterator<Complex> func = test.build(base);
        final Complex testCase = new Complex(0, 0);
        Complex next;
        
        /*
         * Ensures that there are iterations available 
         * Tests the next return values of real and imaginary 
         * Both components of the next Complex must be equal to 0.0 
         */ 
        assertTrue("Assuring that there is still an iteration", func.hasNext());
        next = func.next();
        assertEquals("Initial should be have zero real", testCase.getReal(), next.getReal(), EPSILON);
        assertEquals("Initial should be have zero imaginary", testCase.getImaginary(), next.getImaginary(), EPSILON);
        
        /*
         * Ensures that there are iterations available 
         * Tests the next return values of real and imaginary 
         * Both components of the next Complex must be equal to the given mandelbrot base components 
         */ 
        assertTrue("Assuring that there is still an iteration", func.hasNext());
        next = func.next(); 
        assertEquals("Initial should be have the input constructor complex real", base.getReal(), next.getReal(), EPSILON);
        assertEquals("Initial should be have input constructor complex imaginary", base.getImaginary(), next.getImaginary(), EPSILON);
        
        /*
         * Ensures that there are iterations available 
         * Tests the next return values of real and imaginary 
         * Both components of the next Complex must be equal to the given given applied x^2 + c function
         */ 
        assertTrue("Assuring that there is still an iteration", func.hasNext());
        next = func.next(); 
        assertEquals("Initial should be the applied complex real", base2.apply(base).getReal(), next.getReal(), EPSILON);
        assertEquals("Initial should be the applied complex imaginary", base2.apply(base).getImaginary(), next.getImaginary(), EPSILON);
        
        /*
         * Ensures that there are no more iterations
         * Tests the next method for a NoSuchElementException 
         */ 
        assertFalse("There should not be any iterations left", func.hasNext());
        try
        {
            next = func.next();
            fail("A NoSuchElementException should occur when there are no more iterations left and hasHext is false.");
        }
        catch(NoSuchElementException e)
        {
            System.out.println("The error has been successfully thrown!");
        }
    }
}
