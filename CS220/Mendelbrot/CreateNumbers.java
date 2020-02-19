/**
 * This class is designed to literally make test numbers of a wide range, including zero's
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 11/9/2017
 */
import java.util.Random;
public class CreateNumbers
{
    private final Random r = new Random();
    private final int SIZE_ARRAY = 100;
    private final int MAX_ROWS = 2;
    private final int MAX_ITERS = 5;
    private final double maxNum = 5;
    private final double  minNum = -5;
    
    /*
    * Getter method
    * 
    * @return int default array size 
    */
    public int getSizeArray()
    {
        return SIZE_ARRAY;
    }
    
    /*
    * Getter method
    * 
    * @return int default max row size 
    */
    public int getMaxRows()
    {
        return MAX_ROWS;
    }
    
    /*
    * Getter method
    * 
    * @return int default iteration row size 
    */
    public int getMaxIters()
    {
        return MAX_ITERS;
    }
    
    public double[][] createTestNumbers()
    {
        final double testNumbers[][] = new double[SIZE_ARRAY][MAX_ROWS];
        double number;
        double number2;
        double randomValue;
        double randomValue2;
        
        for(int i = 0; i < testNumbers.length; i++)
        {
          number = Math.random();
          number2 = Math.random();
          randomValue = minNum + (number * (maxNum - minNum));
          randomValue2 = minNum + (number2 * (maxNum - minNum));
          testNumbers[i][0] = randomValue;
          testNumbers[i][1] = number2;
        }
        
        //Ensures there are 0 cases
        testNumbers[0][0] = 0;
        testNumbers[0][1] = 0;
        
        testNumbers[1][0] = 1;
        testNumbers[1][1] = 0;
        
        testNumbers[2][0] = 0;
        testNumbers[2][0] = 1;
        
        return testNumbers;  
    }
    
    public int[] createIterations()
    {
        final int[] test = new int[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = r.nextInt(5);
        }
        return test;
    }
    
    public double[] createLimits()
    {
        final double[] test = new double[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = r.nextDouble()*1E9;
        }
        return test;
    }
     
    public Complex[] createComplexNumbers()
    {
        final double[][] numbers = createTestNumbers();
        Complex[] tests = new Complex[SIZE_ARRAY];
        for(int i = 0; i < tests.length; i++)
        {
          tests[i] = Complex.cpl(numbers[i][0], numbers[i][1]);
        }
        return tests;
    }
    
    public MandelbrotBase[] createMandelbrotBaseNumbers()
    {
        final Complex[] tests = createComplexNumbers();
        MandelbrotBase[] testNums = new MandelbrotBase[SIZE_ARRAY];
        for(int i = 0; i < testNums.length; i++)
        {
          testNums[i] = new MandelbrotBase(tests[i]);
        }
        return testNums;
    }
    /*
    These methods can be used for creating the necessary Iteration 
    
    public Function1Iterator[] createFunction1IteratorsWithIterations()
    {
        final Complex[] testCpx = createComplexNumbers();
        final MandelbrotBase[] testMbb = createMandelbrotBaseNumbers();
        final int[] iterations = createIterations(); 
        Function1Iterator[] test = new Function1Iterator[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = new Function1Iterator(testMbb[i], testCpx[i], iterations[i]);
        }
        return test;
    }
    
    
    public Function1Iterator[] createFunction1IteratorsUnlimited()
    {
        final Complex[] testCpx = createComplexNumbers();
        final MandelbrotBase[] testMbb = createMandelbrotBaseNumbers();
        Function1Iterator[] test = new Function1Iterator[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = new Function1Iterator(testMbb[i], testCpx[i]);
        }
        return test;
    }   
    
    public MandelbrotIteratorFactory[] createMandelbrotIteratorFactory()
    {
        final double[] limits = createLimits(); 
        MandelbrotIteratorFactory[] test = new MandelbrotIteratorFactory[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = new MandelbrotIteratorFactory(limits[i]);
        }
        return test;
    }
    
    public IterationCounter[] createIterationCounter()
    {
        IterationCounter[] test = new IterationCounter[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = createMandelbrotIterationCounter()[i];
        }
        return test;
    }
    
    public MandelbrotIterationCounter[] createMandelbrotIterationCounter()
    {
        MandelbrotIterationCounter[] test = new MandelbrotIterationCounter[SIZE_ARRAY];
        for(int i = 0; i < test.length; i++)
        {
          test[i] = new MandelbrotIterationCounter(createComplexNumbers()[i], createIterations()[i]);
        }
        return test;
    }
    */
}
