/* SparseArray.java
 *
 * An implementation of a generic Sparse 1D array with a generic approach
 *
 * Author: Ethan Booker
 * 
 *
 * Implements multiple methods that would be useful for anyone 
 * who would want to use a Sparse 1D array due to the generic
 * approach.
 *
 */
/**
 * Necessary import for implementation using the Function method
 */
import java.util.function.Function;
/**
 * Necessary import for implementation using the BiFunction method
 */
import java.util.function.BiFunction;

/**
 *  A generic sparse single-dimensional array with configurable
 *  default entry.
 *
 * @param <Content> The type of the elements of the array
 * 
 * @author Ethan Booker
 * @since 12/13/217
 * @version 1.0
 */
public class SparseArray<Content> {
    /**
     * Private linked list class for the SparseArray
     * 
     * @author Ethan Booker
     * @since 12/13/2017
     * @version 1.0
     */
    public class List
    {
        /**
         * Holds unique <Content> data 
         */
         private Content data;
         
        /**
         * Acts as pretend index in the SparseArray
         */
         public long index;
         
        /** 
         * Link to the next List node 
         */
           private List next;
      
        /**
         * Constructor for the Linked List
         * 
         * @param  Content the unique data that the List will hold 
         * @param  long The index of the Content
         */
        public List(Content data, long index)
        {
            this.data = data;
            this.index = index;
            next = null;
        }
      
        /**
         * Getter method that retrieves that current data for the node
         * 
         * @return Content the current Content data
         */
         public Content getData()
         {
             return data;
         }
      
        /**
         * Getter method to retrieve the next linked node
         * 
         * @param List the next linked Node reference
         */
        public List getNext()
        {
            return next;
        }
      
        /**
         * Mutator method to change the next reference link node
         * 
         */
        public void setNext(List next)
        {
            this.next = next;
        }
        
        
        public long getIndex()
        {
            return index;
        }
  }
  
  
  /**
   * The dot product class acting as a BiFucntion is the same for all Sparse Arrays
   * Implemented a dotproduct
   */
  private static class DotProduct implements BiFunction<SparseArray<Double>, SparseArray<Double>, SparseArray<Double>>
  {
      /**
       * The dot product method that creates a SparseArray with unique dot product values
       * 
       * @param SparseArray<Double> one of the two arrays used in the dot product
       * @param SparseArray<Double> the other array used in the dot product
       * @return SparseArray<Double> contains dot product unique values
       */
      public SparseArray<Double> apply(SparseArray<Double> array1, SparseArray<Double> array2)
      {
          //Creates the new default value of the dot product SparseArray
          Double dft = array1.getDefaultContent() * array2.getDefaultContent();
          
          //Initialization of the dotProduct SparseArray
          SparseArray<Double> dotProduct = new SparseArray<Double>(array1.getDeclaredSize(), dft);
          
          //Checks both array until their unique storages are cleared
          while(array1.getStoredEntries() != 0 || array2.getStoredEntries() != 0)
          {
            //Checks to ensure both list aren't empty
            if(array1.getListHolder() != null && array2.getListHolder() != null)
            {
              //If both nodes have the same index, multiply both by each other and set product and value to dotProduct then unset both index
              if(array1.getListHolder().index == array2.getListHolder().index)
              {
                dotProduct.set(array1.getListHolder().index, array1.getListHolder().getData() * array2.getListHolder().getData());
                array1.unset(array1.getListHolder().index);
                array2.unset(array2.getListHolder().index);
              }
              //array1 index is smaller than array2, array1 index data multiply by default of array2 then set index and product to dotProduct then unset array1 index
              else if(array1.getListHolder().index < array2.getListHolder().index)
              {
                dotProduct.set(array1.getListHolder().index, array1.getListHolder().getData() * array2.getDefaultContent());
                array1.unset(array1.getListHolder().index);
              }
              //array1 index is larger than array2, array2 index data multiply by default of array1 then set index and product to dotProduct then unset array2 index
              else
              {
                dotProduct.set(array2.getListHolder().index, array2.getListHolder().getData() * array1.getDefaultContent());
                array2.unset(array2.getListHolder().index);
              }
            }
            //Array2 is out of nodes, just keep setting and multiplying array1 data to array2 default then unset the node 
            else if(array1.getListHolder() != null && array2.getListHolder() == null)
            {
              dotProduct.set(array1.getListHolder().index, array1.getListHolder().getData() * array2.getDefaultContent());
              array1.unset(array1.getListHolder().index);
            }
            //Array1 is out of nodes, just keep setting and multiplying array2 data to array1 default then unset the node 
            else if(array1.getListHolder() == null && array2.getListHolder() != null)
            {
              dotProduct.set(array2.getListHolder().index, array2.getListHolder().getData() * array1.getDefaultContent());
              array2.unset(array2.getListHolder().index);
            }
          }
          
          return dotProduct;
      }
  }
  
  /**
   * The determined default value of type Content
   */
  private final Content dft;
  
  /** 
   * Personal linked list to hold unique data
   */
  private List listHolder;
  
  /**
   * The declared size of the SparseArray
   */
  private final long size;
  
  /**
   * The amount of unique values compared to the default value within the SparseArray 
   */
  private int entries;
  
  /**
   * All SparseArrays should have the same dotProduct function 
   */
  private static DotProduct dp = new DotProduct();


     
  /**
   *
   * @param long size the number of entries allowed in the array.
   */
  public SparseArray(final long size) 
  {
      this.size = size;
      dft = null;
      entries = 0;
  }

  /**
   * Constructs a sparse array with given content as default value
   *
   * @param long size the number of entries allowed in the array.
   * @param Content dft the initial contents of every index into the array.
   */
  public SparseArray(final long dimension, final Content dft) 
  {
      size = dimension; 
      this.dft = dft;
      entries = 0; 
  }

  /**
   * Getter method that retrieves the sparse array default value
   * 
   * @return Content the sparse array default content 
   */
  public Content getDefaultContent() 
  {
      return dft; 
  }

  /**
   * Getter method that retrieves the sparse array declared size
   * 
   * @return long the sparse array size 
   */
  public long getDeclaredSize() 
  {
      return size;
  }

  /**
   * Getter method that retrieves the total amount of values 
   * that differ from the default value in the sparse array
   * 
   * @return int entries that differ from the sparsed array default value
   */
  public int getStoredEntries() 
  {
      return entries; 
  }

  /**
   * Returns a value at particular index depending
   * on whether the desired indexed List exists
   * Throw an ArrayIndexOutOfBoundsException if 
   * parameter index is not a valid index 
   * 
   * @param long The index to retrieve the desired value in the SparseArray
   * @throws ArrayIndexOutOfBoundsException An invalid index input
   */
  public Content get(final long index) 
  {
      //Checks for invalid index then throws  error if invalid
      if(index < 0 || index >= getDeclaredSize())
      {
        throw new ArrayIndexOutOfBoundsException("Unable to retrieve content"); 
      }
      
      //Gets the desired indexed List
      List temp = getSpecList(index);
      
      //If desired index isn't in the SparseArray, return default content 
      if(temp == null) //Couldn't find the Node in the list with the given index
      {
        return getDefaultContent(); 
      }
      else
      {
        return temp.getData();
      }
  }

  /**
   * Stores a value at a particular index in this array.
   * 
   * @param index the index to be used for storage
   * @param value the value to be stored
   */
  public void set(final long index, final Content valus) 
  {
      //Do nothing if index is not in the SparseArray 
      if(index < 0 || index >= getDeclaredSize())
      {
        //No need to check anything else
        return;
      }
        
      
      //Checks whether the list is empty 
      if(entriesIsZero())
      {
        /* Checks whether the valus is the same data type as the Sparse Array default content value
         * Must check both the reference of .equals due to == failing with conbine/map afterwards and .equals(null) causing a null pointer exception
         */
        if((valus == null && getDefaultContent() == null) || (valus != null && valus.equals(getDefaultContent())))
        {
          return; //There is nothing unique to add to the SparseArray
        }
        else
        {
          //Acts as the head of the LinkedList and start of the SparseArray list
          listHolder = new List(valus, index);
          //Added a unique entry, should increment entries
          entries++;
        }
      }
      else //The list is not empty
      {
        //The list contains the given index 
        if(isSet(index))
        {
          //Checks valus data type is the same as the default value

          if((valus == null && getDefaultContent() == null) || (valus.equals(getDefaultContent())))
          {
            //Must unset due to changing a valid index value to an nonunique value
            unset(index);
          }
          else
          {
            //Get's the desired List Node at the index
            List temp = getSpecList(index);
            //Changes that desired List Node data to the given valus
            temp.data = valus; 
            
            //No incrementing due to changing an already unique value
          }
        }
        else //The list doesn't contain the desired index value 
        {
          //Checks to see if valus the same value as getDefaultContinue;
          
          if((valus == null && getDefaultContent() == null) || (valus != null && valus.equals(getDefaultContent()))) 
          {
            //Nothing new is being added to the SparseArray 
            return;
          }
          else
          {
            //Reference of to the start of the list
            List temp = listHolder;  
            
            //Check if desired index is smaller than the head index then shifts the list over by to create new head node position
            if(temp.index > index)
            {
                List newHead = new List(valus, index);
                newHead.setNext(temp);
                listHolder = newHead;
                entries++;
            }
            else
            {
                //Iterates through list, checks to see if the next node prior to the current is larger thus will stop to get correct position to insert otherwise search till end of list
                while(temp.getNext() != null && temp.getNext().index < index)
                {
                    //Shifting list
                    temp = temp.getNext();
                }
                
                //At the end of the list, just add onto the end of the list
                if(temp.getNext() == null)
                {
                    //Sets the end to a new value 
                    temp.setNext(new List(valus, index));
                    entries++;
                }
                else
                {
                    //Must keep reference to the middle node's next reference, since the middle is being removed
                    List after = temp.getNext();
                    //temp is the previous node to the spot we want to initiate
                    //Initiate the previous node next since thats the spot we want to insert
                    temp.setNext(new List(valus, index));
                    
                    //Ensures we combine the list back together
                    temp.getNext().setNext(after);
                    entries++;
                }
            }
          }
        }
      }
  }

  /**
   * Checks whether the given index has a unique value that differs 
   * from the default value
   * @param long the Desired index position to look at 
   * @return boolean determines whether the given index in the 
   * SparseArray has a unique value
   */
  public boolean isSet(final long index) 
  { 
      //invalid index or isn't contained within the List should return false
      if(index < 0 || index >= getDeclaredSize() || !contains(index))
      {
          return false;
      }
      
      //default value 
      return true;
  }

  /**
   * Remove any setting to a non-default value at the given index.
   * Has no effect if there is no such setting.
   * 
   * @param long The desired index associated value should become 
   * default if it's a unique value
   */
  public void unset(final long index) 
  {
      //Checks if the current index is default value
      if(!isSet(index))
      {
          return;
      }
      
      List temp = getSpecList(index); //Gets the desired indexed List node 
      
      //Checks to see if the desired List node is at the head of the List
      if(temp.getData() == listHolder.getData()) 
      {
          //Shifts head of the list to the next List reference
          listHolder = listHolder.getNext();
      }
      else
      {
          //Retrieves the List node previous to the desired removing List Node
          List temp2 = getSpecListPrevious(index);
          //Set the reference of the previous List node the desired removing next Refernce 
          temp2.setNext(temp.getNext());
      }
      //Since we are removing one unique value
      entries--;
  }
  
  /**
     *  MergeSorting the LinkedList
     *  
     *  @param Node<T> the Linked list desired to be sorted
     */
    public List mergeSortLinkList(final List startNode)
    {
        //Break the list until list is null or only 1 element is present in List.
        if(startNode == null || startNode.getNext() == null)
        {
            return startNode;
        }
             
        //Breaks the LinkedList in half        
        List middle = getMiddle(startNode);
        List nextOfMiddle = middle.getNext();
        
        //Gets rid of Link between the two middle Nodes
        middle.setNext(null);
             
        //Breaks list until there is only 1 Node 
        List left = mergeSortLinkList(startNode);
        List right = mergeSortLinkList(nextOfMiddle);
             
        //Merges the broken down LinkedList containing 1 Node
        List sortedList = mergeTwoListRecursive(left, right);
               
        return sortedList;
    }
 
    /**
     * Shifts the Nodes based on current desired Comparator 
     * 
     * @param Node<T> Left side of the LinkedList
     * @param Node<T> Right side of the LinkedList
     * @return Node<T> The shifted parts of the LinkedList
     */
    private List mergeTwoListRecursive(final List left, final List right){
        //Avoids null comparisons and base case if run out on one side
        if(left==null)
        {
             return right;
        } 
        if(right==null)
        {
            return left;
        }
        
        //Node that Links the sorted Nodes
        List temp = null;
        
        //Compares the data of the Node then assigns the correct Next node reference 
        if(left.getIndex() < right.getIndex())
        {
            temp = left;
            temp.setNext(mergeTwoListRecursive(left.getNext(), right));
        }
        else
        {
            temp = right;
            temp.setNext(mergeTwoListRecursive(left, right.getNext()));
        }
        
        return temp;
    }
     
    private List getMiddle(final List startNode) 
    {
        if(startNode==null)
        {
            return startNode;
        }
             
        List mid = startNode;
        List end = startNode;
               
        while(end.getNext()!=null && end.getNext().getNext()!=null)
        {
            mid = mid.getNext();
            end = end.getNext().getNext();
             
        }
        
        return mid;
    }

  /**
   *  Transforms the current SparseArray into another SparseArray
   *  using the fuction apply method 
   *  
   *  @param Function<Content, Result> The given function for apply purposes
   *  @return <Result> SparseArray<Result> A SparseArray that has been mapped by apply and current SparseArray
   */
  public <Result> SparseArray<Result> map(final Function<Content,Result> function) 
  {
      //Gets the new applied default value
      Result dftValue = function.apply(getDefaultContent());
      //Creating newly mapped SparseArray 
      SparseArray<Result> appliedArray = new SparseArray<Result>(getDeclaredSize(), dftValue);
      
      //Iterating through the list to apply the function onto the unique values to set onto the new SparseArray
      List temp = listHolder;
      while(temp != null)
      {
          Result data = function.apply(this.get(temp.index));
          appliedArray.set(temp.index, data);
          temp = temp.getNext();
      }
      
      //return sparsearray 
      return appliedArray;
  }

  /**
   *  Using two given arrays along with a BiFunction
   *  in order to transform a new SparseArray due to the 
   *  BiFucntion Apply on the given two arrays
   *  
   *  @param BiFunction<Content, Content2, Result> The given function for apply use
   *  @param SparseArray<Content2> The second Sparse array used to transform into a new Array
   *  @return <Content2, Result> SparseArray<Result> A SparseArray that's been transformed 
   */
  public <Content2,Result> SparseArray<Result>
      combine(final BiFunction<Content,Content2,Result> function,
              final SparseArray<Content2> that) 
  {
      //Gets the new applied default value
      Result dftValue = function.apply(this.getDefaultContent(), that.getDefaultContent());
      //Creating newly combined SparseArray 
      SparseArray<Result> appliedArray = new SparseArray<>(this.getDeclaredSize(), dftValue);
      
      //Iterating through the list to apply the function onto the unique values to set onto the new SparseArray
      List temp = this.getListHolder();
      Result data;
      while(temp != null || that.getListHolder() != null)
      {
          if(temp != null && that.getListHolder() == null)
          {
              data = function.apply(this.get(temp.index), that.get(temp.index));
              appliedArray.set(temp.index, data);
              temp = temp.getNext();
          }
          else if(temp == null && that.getListHolder() != null)
          {
              
          }

          
      }
      
      return appliedArray;
  }

  /**
   *  For two arrays of numbers, calculate the dot product of the
   *  numbers, that is, the sum of the products of pairs of numbers at
   *  the same position at the two arrays.
   *  
   * @param array1 the first array of numbers
   * @param array2 the second array of numbers. 
   * @throws IllegalArgumentException If either param arrays have different declared sizes
   * @return Double the dot product summation between both param arrays
   */
  public static Double
      dotProduct(final SparseArray<Double> array1, SparseArray<Double> array2) 
  {
      //Checks the size of both arrays to ensure both are the same size
      if(array1.getDeclaredSize() != array2.getDeclaredSize())
      {
          throw new IllegalArgumentException("List declared size aren't the same");
      }
     
      //Dot product array containing unique dot product entries
      SparseArray<Double> appliedDP = dp.apply(array1, array2);
      
      //Initializing the dotproduct summation
      double dotProduct = 0.0;

      //Takes care of all the default times default dot products
      long x = appliedDP.getDeclaredSize() - appliedDP.getStoredEntries();
      dotProduct = x * appliedDP.getDefaultContent();
      
      //Iterates through entire list of the sparsearray until all the data have been summed then unsets them to shift the list over 
      while(appliedDP.getListHolder() != null)
      {
          dotProduct += appliedDP.getListHolder().getData();
          appliedDP.unset(appliedDP.getListHolder().index);
      }
      
      return dotProduct;
  }
  
  /**
   * Helper method to get the listHead
   * 
   * @List the start of the list 
   */
  private List getListHolder()
  {
      return listHolder;
  }
  
  public List getListHolderCopy()
  { 
      List temp = listHolder; 
      List head = null;
      List tail = null;
      int i = 0;
      while(temp != null)
      {
          if(i == 0)
          {
              head = new List(temp.getData(), temp.getIndex());
              tail = head;
              i++;
          }
          else
          {
              tail.setNext(new List(temp.getData(), temp.getIndex()));
              tail = tail.getNext();
          }
          temp = temp.getNext();
      }
      return head;
  }
  
  public Content getCopyOfData(List node, int x)
  {
      int i = 0; 
      List temp = node;
      while(i < x )
      {
          temp = temp.getNext();
          i++;
      }
      return temp.getData();
  }
 
  
  /**
   * Helper methodthat checks if a certain Index pertains to a unique value
   * 
   * @param long desired index to check
   * @return boolean If the desired index is has a unique value
   */
  private boolean contains(long strIndex)
  {
      //Checks if the SparseArray has a correlating index 
      if(getSpecList(strIndex) != null)
      {
          return true;
      }
      
      //Default value
      return false;
  }
  
  /**
   * Helper method that checks if the size is declared 0
   * 
   * @return boolean true if the declared sparse array size is 0
   */
  private boolean entriesIsZero()
  {
      //Checks the current stored entry size
      if(getStoredEntries() == 0)
      {
          return true;
      }
      
      //Default value
      return false;
  }
  
  /**
   * Helper method to find desired Node with the given index
   * 
   * @param long desired Node with the given index value
   * @return List desired Node 
   */
  private List getSpecList(final long idx)
  {
      //Checks if the list is empty
      if(entriesIsZero())
      {
          return null;
      }
      
      //Initialization of vairables
      List pos = listHolder; 
      List holder = null;
      
      //Iterates through list until end of list or finds desired index
      while(pos != null)
      {
          //Found the index, stop searching 
          if(idx == pos.index)
          {
              holder = pos;
              break;
          }
          
          //Move onto the next Node
          pos = pos.getNext();
      }
      
      return holder;
  }
  
  public long getIndex(List l)
  {
      return l.getIndex();
  }
  
  /**
   * Helper method that finds the previous Node for the given index
   * 
   * @param long the desired index for the Node we want the previous Node to
   * @return List the previous Node 
   */
  private List getSpecListPrevious(final long idx)
  {
      //Checks if list is empty
      if(entriesIsZero())
      {
          return null;
      }
      
      //Initialization of list
      List pos = listHolder; 
      List holder = null;
      
      //Iteraters through list checking the current Node and Next Node whether or not the List ends
      while(pos != null || pos.getNext() != null)
      {
          //Checks when the next Node will be the desired index to get the previous Node 
          if(pos.getNext().index == idx)
          {
              holder = pos;
              break;
          }
          //Containue to iterate through the list
          pos = pos.getNext();
      }
      
      return holder;
  }
}
