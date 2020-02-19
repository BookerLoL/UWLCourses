import java.util.Iterator;
/** 
 *  Counts the number of times the result of an iterator's =next=
 *  method satisfies the method testItem.
 *  
 *  @author Ethan Booker
 *  @version 1.0
 *  @since 11/14/2017
 */
public abstract class IterationCounter<X>{
  private final int maximum;
  private final Factory<Iterator<X>, X> f;
    
  /** Initialize the counter for a particular way of building Iterators
   *
   * @param Factory<Iterator<X>, X> A factory that provides an iterator of type X 
   * @param maximum The maximum number of calls to next() made on
   * behalf of one call to countPassing().
   */
  public IterationCounter(final Factory<Iterator<X>, X> factory,
                          final int maximum) 
  {
      f = factory;
      this.maximum = maximum;
  }
  
  /** Template method which expresses the condition which each
   * result of next() might satisfy.
   */
  public abstract boolean testItem(final X item);
  
  /** 
   * For an Iterator received from the factory for the given value
   * seed, count the number of times its next() method returns a value
   * which satisfies testItem().
   *
   * @param X, used for determining number of next() that will satisfy testItem()  
   */
  public int countPassingIterations(final X seed) 
  {
      //Must create an Iterator for the given X seed
      int counter = 0;
      Iterator<X> test = f.build(seed);
    
      //Ensures that iterating should be valid to increment 
      while(counter < maximum && test.hasNext() && testItem(test.next()))
      {
          counter++;
      } 
      return counter;
  }
}
