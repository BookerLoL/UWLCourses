/**
 * Write a description of class Node here.
 * 
 * @author Ethan Booker
 * @version 
 */
public class Node<T>
{
    private Node<T> next; 
    private T data;
    
    public Node(T data)
    {
        this.data = data;
        next = null;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
    
    public T getData()
    {
        return this.data;
    }
    
    public void setNext(Node<T> node)
    {
        this.next = node;
    }
    
    public Node<T> getNext()
    {
        return next; 
    }
    
    public String toString()
    {
        return data + "" + "\t";
    }
}
