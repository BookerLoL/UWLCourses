/**
 * Write a description of class TestClass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestClass
{
    public static void main(String[]args)
    {
        /* 
         * works! 
            Node<Integer> test = new Node<>(0);
            test.setData(1);
            System.out.println(test.getData());
            test.setNext(new Node<>(2));
            Node<Integer> test2 = test.getNext();
            System.out.println(test2);
            System.out.println(test2.getNext());
         */
        IntComparator<Node<Integer>> compare = new IntComparator<>(); 
        SorterList<Integer> test = new SorterList<>(compare);
        //System.out.println(test.isEmpty());
        //System.out.println(test.size());
        test.add(1);
        //System.out.println(test);
        test.add(22);
   
        //System.out.println(test.isEmpty());
        //System.out.println(test.size());
        System.out.println(test);
        test.clear();
        System.out.println(test.isEmpty());
        System.out.println(test);
        test.add(1);
        test.add(0);
        test.add(2);
        System.out.println(test);
        //System.out.println(test.contains(1));
        //System.out.println(test.contains(4));
        //System.out.println(test.contains(3));
        //System.out.println(test.contains(null));
       
        
  
        
        
    }
}
