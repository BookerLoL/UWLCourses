import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator class to simulate a specific function being iterated
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 10/31/2017
 */
public class Function1Iterator<X> implements Iterator<X> {
    /**
     * Components necessary to iterate
     */
    private X initVal;
    private final Function1 function;
    private int maxIterations;

    /**
     * Constructor
     * 
     * @param Function1<X> Need a function to use
     * @param X            can be a variety of different numeric inputs
     */
    public Function1Iterator(Function1<X> f, X initial) {
        this(f, initial, -1);
    }

    /**
     * Constructor
     * 
     * @param Function1<X> represents a useable function to use
     * @param X            can be a variety of different numeric data type inputs
     * @param int          the number of allowed next iterations for the function
     */
    public Function1Iterator(final Function1<X> f, final X initial, final int maxIterations) {
        this.function = f;
        this.initVal = initial;

        this.maxIterations = maxIterations;
    }

    /**
     * Private helper method that ensures the next initVal will have the next X type
     * value.
     * 
     * @param Function1<X> Need a function to use
     * @param X            can be a variety of different numeric inputs
     */
    private void FunctionIterate(final Function1<X> f, final X initial) {
        initVal = f.apply(initial);
    }

    /**
     * Checks to see if the function can be iterated again
     * 
     * @return Boolean tests to see if the function can be applied another time.
     */
    public boolean hasNext() {
        return maxIterations != 0;
    }

    /**
     * Allows the function to simulate using the initial value
     * 
     * @return X inital value prior to inputted into the function
     */
    public X next() {
        /*
         * Checks if the function has another next then iterattes through the function.
         * Decrements the amount of iterations left whenever function is iterated
         * Should throw an exception whenever there is no more iterations left
         */
        if (hasNext()) {
            X init2 = initVal;
            initVal = function.apply(initVal);
            maxIterations--;
            return init2;
        } else {
            throw new NoSuchElementException();
        }
    }
}
