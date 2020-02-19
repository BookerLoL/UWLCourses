/**
 * Necessary imports to have correctly implemented class
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The SorterList that utilizes a Linked List along with specific guidelines directed by the Professor
 * This SorterList uses a singly Linked List with no head / tail sentinel and the mergesort 
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 11/30/2017
 */
public class SorterList<T> implements SorterListInterface<T>
{ 
    /**
     * Fields necessary for the LinkedList  
     */
    private Node<T> start;
    private Comparator<T> comparer;

    /**
     * The counter for LinkedList
     */
    private int size;
    
    /**
     * Necessary constructor 
     * 
     * @param Comparator<T> the current comparator that the sorterList will use to sort content
     */
    SorterList(final Comparator<T> comparator)
    {
        comparer = comparator;
        size = 0;
    }
    
    /**
     *  Shows the content of the Linkedlist
     *  Note: Can remove if needed
     *  
     *  @return String LinkedList contents in order
     */
    public String toString()
    {
        //Reference to the head of the LinkedList
        Node<T> temp = start;
        String data = "";
        //Iterates through the LinkedList 
        while(temp != null)
        {
            //Collects data in LinkedList
            data += "Linked list data: " + temp.getData() + " \n "; 
            //Shifts onto next Node after data has been collected
            temp = temp.getNext();
        }
        return data;
    }
    
    /**
     * Helper method
     * Creates a shallow copy of the LinkedList
     * 
     * @return Node<T> the start of the LinkedList
     */
    protected Node<T> getStart()
    {
        //To copy data over from the current LinkedList 
        Node<T> temp = start;
        int i = 0;
        
        //Copycat LinkedList
        Node<T> shallowCopy = null;
        Node<T> shall = null;
        
        //Iterates through current LinkedList
        while(temp != null)
        {
            //Starts the head of the shallow copy
            if(i == 0)
            {
                shallowCopy = new Node<>(temp.getData());
                i++;
            }
            else 
            {
                //Assigning values after the LinkedList shallow copy head
                Node<T> pos = getNode(i-1, shallowCopy);
                pos.setNext(new Node<T>(temp.getData(), pos.getNext()));
                i++;
            }
            
            //Iterates onto the next Node of the current LinkedList
            temp = temp.getNext();
        }
        return shallowCopy;
    }
          
    /**
     * Helper method
     * Retrieves back the desired Node<T> in the LinkedList 
     * 
     * Note: Perhaps could add thrown exceptions in the future
     * for those planning on possible mess ups on modifications
     * 
     * @param int the index of the desired Node
     * @return Node<T> the desired Node 
     */
    private Node<T> getNode(final int idx, final Node<T> start)
    {
        //References a certain part of a LinkedList Node
        Node<T> pos = start;
        
        //Continously shifts to the desired index Node in the list 
        for(int i = 0; i< idx; i++)
        {
            pos = pos.getNext();
        }
        
        return pos;
    }
    
     
    /**
     * Adds the desired item to the list in the correct 
     * order based off of the current Comparator of the list
     * 
     * Note: Could create another helper add method for 
     * the getStart() method 
     * 
     * @param T the desired item to be added to the list 
     */
    public void add(final T item)
    {
        /*
         * Everytime an item is being added, the LinkedList size should increment by 1
         * 
         * If the LinkedList is empty, place the item at the start of the LinkedList
         */
        if(isEmpty())
        {
            start = (new Node<>(item));
            size++;
        }
        else
        {
          //Finds the correct position for the item to be placed within the LinkedList
          int idx = findComparedIndex(item, start, getComparator());
          
          //Shifts the starting Node so that the item is placed correctly before the rest of the other Nodes
          if(idx == 0)
          {
              start = (new Node<>(item, start));
          }
          else
          {
              //Finds the node previously to the correct location
              Node<T> pos = getNode(idx-1, start);
              
              //Sets the next node with the desired content and refers it previous next node
              pos.setNext(new Node<T>(item, pos.getNext()));
          }
          size++;
        }
    }
    
    /**
     * Checks to see if the desired item to be removed is removable. 
     * The first occurence of the item is removed. 
     * 
     * @param T the desired item that should be removed
     * @boolean true if the item was found and removed from the list
     */
    public boolean remove(final T item)
    {
        //Checks if the list is empty or the item isn't contained
        if(isEmpty() || !contains(item))
        {
            return false;
        }
        
        else
        {
            //Looks for the first occurence of the item
            int idx = findFirstOccurence(item, start);
            
            if(idx == 0)
            {
                start = start.getNext();
            }
            else 
            {
                //Gets the previous Node and next Node that is linked to the desired removed Node containing the item
                Node<T> pos = getNode(idx-1, start);
                Node<T> pos2 = getNode(idx, start);
                
                //The previous Node is linked to the Node after the removed Node
                pos.setNext(pos2.getNext());
            }
            //Decrement the list size
            size--;
            return true;
        }
    }
    
    /**
     * Removes all contents from this list
     */
    public void clear()
    {
        //Ends method if list is already empty
        if(isEmpty())
        {
            return;
        }
        
        //Resets the current Node reference and size of the list
        size = 0;
        start = null;
    }
    
    /**
     * Accessor method for obtaining the current size of the list
     * 
     * @return int size of list
     */
    public int size()
    {
        return size;
    }
    
    /**
     *  Checks to ensure the list empty
     *  
     *  @return boolean true if the list is really empty
     */
    public boolean isEmpty()
    {
        if(size() == 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Checks the list to see if the desired value is within the list 
     * 
     * @param T the desired value the is being searched throughout the list 
     * @boolean  true if the list does contain the desired value 
     */
    public boolean contains(final T value) 
    {
        if(isEmpty())
        {
            return false;
        }
        
        //Reference to the head of the LinkedList
        Node<T> temp = start;
        
        //Iterates through the LinkedList
        while(temp != null)
        {
            //When the desired value is found, return true 
            if(temp.getData() == value)
            {
                return true;
            }
            
            //Moves onto the next Node
            temp = temp.getNext();
        }
        
        //Desired value wasn't found within the LinkedList
        return false;
    }
    
    /**
     *  Accessor method to return the current Comparator for the LinkedList
     *  
     *  @return Comparator<T> the current Comparator
     */
    public Comparator<T> getComparator()
    {
        return comparer;
    }
    
    
    /**
     * Changes the list's current Comparator to the desired Comparator
     * The list is then reorganized to the desired order based on the desired Comparator
     * 
     * @param Comparator<T> the new comparator for the list 
     */
    public void setComparator(final Comparator<T> comparator)
    {
        comparer = comparator;
        
        //retrieves modified LinkedList 
        SorterListIterator<T> iterator = getSorterList(getComparator());
        start = iterator.getNewNode();
    }
    
    /**
     *  Creates an iterator based on the current LinkedList and Comparator
     *  Note: The current LinkedList should still remain the same
     *  
     *  @return Iterator<T> iterator containing the current LinkedList contents 
     */
    public Iterator<T> iterator()
    {
        return new SorterListIterator<>(this, getComparator());
    }
    
    /**
     * Creates an iterator based on the current LinkedList and desired Comparator
     * Note: The current LinkedList should still remain the same
     * 
     * @return Iteartor<T> iterator containing current LinkedList contents changed by the desired comparator
     */
    public Iterator<T> iterator(final Comparator<T> comparator)
    {
        return new SorterListIterator<>(this, comparator);
    }
    
    /**
     * Helper method Used to retrieve the modified LinkedList by the desired comparator
     * 
     * @return SorterListIterator<T> modified Iterator with current list contents
     */
    private SorterListIterator<T> getSorterList(final Comparator<T> comparator)
    {
        return new SorterListIterator<>(this, comparator);
    }
    
     
    /**
     * Helper method
     * Finds the first occurence of the desired item 
     * -1 for unsuccessfully at fidning the item
     * 
     * @param T the item that is being looked for
     * @return int the position of the first occurence of the item
     */
    private int findFirstOccurence(final T item, final Node<T> start)
    {
        //Intialization of the position counter and starting node
        int pos = 0;
        Node<T> temp = start;
        
        //Ensures that the desired item actually excists in the list
        if(contains(item))
        {
          //Continously checks the list until the first occurence is found 
          while(temp!=null)
          {
            //Stops the loop when item is found
            if(temp.getData() == item)
            {
              break;
            }
            
            //Moves onto the next Node
            temp = temp.getNext();
            
            //increment counter for position of Node 
            pos++;
          }
          return pos;
        }
          
        //If there is no first occurence
        return -1;
    }
    
    /**
     * Finds the first position of when the desired item is not greater than the list contents 
     * 
     * @param T the item that will be compared to the list contents
     * @return int the position of correct placement for the item
     */
    private int findComparedIndex(final T item, final Node<T> start, final Comparator<T> com)
    {
         //Intialization of the position counter and starting node
        int pos = 0; 
        Node<T> temp = start;
        
        //Iterates through linked list
        while(temp!=null)
        {
          //The item compared to the current Node's content isn't greater, found the correct position for placement
          if(com.compare(item, temp.getData()) <= 0)
          {
              break;
          }
          
          //Moves onto the next Node
          temp = temp.getNext();
          
          //Increments the position
          pos++;
        }
        
        //The item is the greatest in the list
        if(temp == null) 
        {
          return size();
        }
        else
        {
          //Returns correct position for placement
          return pos; 
        }
    }
}

/**
 * LinkedList class
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 11/22/2017
 */
class Node<T>
{
    /**
     * Declaration of Node fields
     */
    private Node<T> next; 
    private T data;
        
    /**
     * Constructor only accessible within the SorterList class
     * Sets up the next Node as null 
     * 
     * @param T assigns type of data to data field 
     */
    protected Node(final T data)
    {
        this.data = data;
        next = null;
    }
        
    /**
     * Constructor only accessible within the SorterList class
     * Useful for reassigning Node links
     * 
     * @param T assigns type of data to data field 
     * @param Node<T> allows for current node to link with specific Node 
     */
    protected Node(final T data, final Node<T> next)
    {
        this.data = data;
        this.next = next;
    }
        
        
    /** 
     * Accessor method that retrieves the current Node data
     * 
     * @return T the current Node data of type T 
     */
    protected T getData()
    {
        return this.data;
    }
        
    /**
     * Mutator method that changes the current Node's next Node reference
     * 
     * @param Node<T> the node that will be the current Node's next Node
     */
    protected void setNext(final Node<T> node)
    {
        this.next = node;
    }
        
    /**
     * Accessor method that retrieves the current nodes next Node
     * 
     * @return Node<T> the current Node's next Node is returned 
     */
    protected Node<T> getNext()
    {
        return this.next; 
    }
    
    /**
     * Displays the current Node data
     * Note: can remove if needed
     * 
     * @String The data from the current Node
     */
    public String toString()
    {
        return this.getData() + " " + "\n";
    }
}
    
/**
 * The Iterator class that implements the Iterator methods
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 11/29/2017
 */    
class SorterListIterator<T> implements Iterator<T> 
{           
    /**
     * Used to modify and iterate through
     */
    private SorterList<T> list;
    private Comparator<T> comparator;
    private Node<T> lead; 
    private Node<T> copy;
    
    /**
     * Constructor 
     * 
     * @param SorterList<T> The list that is needed to Iterate through
     * @param Comaprator<T> The desired Comparator for sorting the LinkedList
     */
    SorterListIterator(final SorterList<T> list, final Comparator<T> compare)
    {
        this.list = list;
        comparator = compare;
        
        //Obtains a shallow copy of the Linked List
        lead = list.getStart();
        lead = mergeSortLinkList(lead);
        
        //Reference to copied LinkedList 
        copy = lead;
    }
        
    /**
     * Returns the modified LinkedList head node
     * 
     * @Node<T> Head node of sorted LinkedList
     */
    protected Node<T> getNewNode()
    {
        return lead;
    }
    
    /**
     *  MergeSorting the LinkedList
     *  
     *  @param Node<T> the Linked list desired to be sorted
     */
    private Node<T> mergeSortLinkList(final Node<T> startNode)
    {
        //Break the list until list is null or only 1 element is present in List.
        if(startNode == null || startNode.getNext() == null)
        {
            return startNode;
        }
             
        //Breaks the LinkedList in half        
        Node<T> middle = getMiddle(startNode);
        Node<T> nextOfMiddle = middle.getNext();
        
        //Gets rid of Link between the two middle Nodes
        middle.setNext(null);
             
        //Breaks list until there is only 1 Node 
        Node<T> left = mergeSortLinkList(startNode);
        Node<T> right = mergeSortLinkList(nextOfMiddle);
             
        //Merges the broken down LinkedList containing 1 Node
        Node<T> sortedList = mergeTwoListRecursive(left, right);
               
        return sortedList;
    }
 
    /**
     * Shifts the Nodes based on current desired Comparator 
     * 
     * @param Node<T> Left side of the LinkedList
     * @param Node<T> Right side of the LinkedList
     * @return Node<T> The shifted parts of the LinkedList
     */
    private Node<T> mergeTwoListRecursive(final Node<T> left, final Node<T> right){
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
        Node temp = null;
        
        //Compares the data of the Node then assigns the correct Next node reference 
        if(comparator.compare(left.getData(), right.getData()) < 0){
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
     
    private Node<T> getMiddle(final Node<T> startNode) 
    {
        if(startNode==null)
        {
            return startNode;
        }
             
        Node<T> mid = startNode;
        Node<T> end = startNode;
               
        while(end.getNext()!=null && end.getNext().getNext()!=null)
        {
            mid = mid.getNext();
            end = end.getNext().getNext();
             
        }
        
        return mid;
    }
      
    /**
     * Returns the LinkedList content of one Node per call
     * 
     * @return T the data from the current Node 
     */
    public T next()
    {
        if(hasNext())
        {
            T data =  copy.getData();
            copy = copy.getNext();
            return data;
        }
        else 
        {
            throw new NoSuchElementException();
        }
    }
        
    /**
     *  Checks to ensure Iterator still has another next
     *  
     *  @boolean true if there is another iteration left
     */
    public boolean hasNext()
    {
        if(copy != null)
        {
            return true;
        }
        return false;
    }
}
