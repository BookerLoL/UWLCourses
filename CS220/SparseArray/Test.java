import java.util.function.Function;
import java.util.function.BiFunction;
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test
{
    public static void main(String[]args)
    {  
       /* 
        test1();
        test2();
        
        test3();  //Tests the dot product function
        
        test4();
    
        map1d(); 
       testM1();
       testConbine();
       */
       testM2();
       
       
       
        
    }
    
    public static void testConbine()
    {
        BiFunction<String, Integer, Double> test2 = new BI2();  
        SparseArray<String> temp = new SparseArray<>(10, "A");
        SparseArray<Integer> temp2 = new SparseArray<>(10, 0);
        for(int i = 0, j = 0; i < 4; i++, j+=2)
        {
            temp.set(i, "AAAAAAAAA");
            temp2.set(j, 10);
            
        }
        
        SparseArray<Double> d = temp.combine(test2, temp2);
        System.out.println("Should be 10: " + d.getDeclaredSize());
        System.out.println("Should be 1 dft: " + d.getDefaultContent());
        System.out.println("Should be 4: " + d.getStoredEntries());
        System.out.println("Should get: 1.0, 1.0, 3.0, 4.3588, 1.0, false, false, true, true, false: \t" + d.get(9) + " " + d.get(8) + " " + d.get(3) + " " + d.get(0) + " " + d.get(7) + " " + d.isSet(9) + " " + d.isSet(8) + " " + d.isSet(3) + " " + d.isSet(0) + " " + d.isSet(7));
        d.set(3, d.getDefaultContent());
        d.unset(0);
       
        System.out.println("Should be 2: " + d.getStoredEntries());
        d.set(0, 4.3588);
        d.set(3, 3.0);
         System.out.println("Should be 4: " + d.getStoredEntries());
        
        
    }
    
    public static void test1()
    {
        System.out.println("Test 1: default sparseArray, type Double"); 
        long size = 1000000000000L;
        
        SparseArray<Double> tester = new SparseArray<>(size);
        System.out.println("Default content should be null: \t" + tester.getDefaultContent());
        System.out.println("Declaredsize should be " + size + ":" + "\t" + tester.getDeclaredSize());
        System.out.println("Stored entries should be 0: \t" + tester.getStoredEntries());
        System.out.println("Should be false for isSet 0: \t" + tester.isSet(0));
        System.out.println("Should be false for isSet -1: \t" + tester.isSet(-1));
        System.out.println("Should be false for isSet 1000000000000L;: \t" + tester.isSet(size));
        System.out.println("Using the set method then get");
        try
        {
            tester.set(1, 3.14);
            tester.set(500, 69.69);
            tester.set(220, 225.0);
            tester.set(4, 4.0);
            tester.set(size-3, 0.0);
            tester.set(-1, null);
            tester.set(size, -1.23456);
            System.out.println(tester);
            System.out.println("Stored entries should still be 5: \t" + tester.getStoredEntries());
        }
        catch(Exception e)
        {
            System.out.println("The set method shouldn't really need to throw exeception, list shouldn't change if out of bounds");
        }
   
        try
        {
            System.out.println("Should be error: \t" + tester.get(-1));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        try
        {
             System.out.println("Should be error: \t" + tester.get(size));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        
        System.out.println("isSet(4), should be 4.0 and true: \t" + tester.get(4) + "\t" + tester.isSet(4));
        System.out.println("isSet(size-3), should be 0.0 and true: \t" + tester.get(size-3) + "\t" + tester.isSet(size-3));
        System.out.println("isSet(size), should be false: \t" + tester.isSet(size));
        System.out.println("isSet(-1), should be false: \t" + tester.isSet(-1));
        tester.set(4, 8.0);
        tester.set(size-3, 3.14);
        System.out.println("Setting a value just in case, should get 8.0: \t" + tester.get(4));
        System.out.println("Setting a value just in case, should get 3.14: \t" + tester.get(size-3));
        tester.set(333, null);
        System.out.println("Setting a value null... stored size should still 5: \t" + tester.getStoredEntries());
        System.out.println("Getting set value null, should be null and false: \t" + tester.get(333) + "\t" + tester.isSet(333));
        tester.unset(4);
        System.out.println("Unsetting a value, should be null and false: \t" + tester.get(4) + "\t" + tester.isSet(4));
        System.out.println("Stored Entries should be 4: \t" + tester.getStoredEntries());
        tester.unset(-1);
        System.out.println("Stored Entries should still be 4: \t" + tester.getStoredEntries());
        System.out.println("Setting a different value back to default, not using unset");
        tester.set(1, null);
        System.out.println("Stored Entries should be 3: \t" + tester.getStoredEntries());
        System.out.println("Should be null and false: \t" + tester.get(1) + "\t" + tester.isSet(1));
        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("End of test1");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println();
    }
    
    public static void test2()
    {
        System.out.println("Test 2: 'Q' default sparseArray, type String"); 
        final SparseArray tester2 = new SparseArray<String>(6000000000000L, "Q");
        System.out.println("Default content should be 'Q': \t" + tester2.getDefaultContent());
        System.out.println("Declaredsize should be " + 6000000000000L + ":" + "\t" + tester2.getDeclaredSize());
        System.out.println("Stored entries should be 0: \t" + tester2.getStoredEntries());
        tester2.set(1, null);
        System.out.println("Should have null appear: " + tester2.get(1));
        tester2.unset(1);
        tester2.set(103, "AA");
        tester2.set(34567890, "Z");
        System.out.println("Stored entries should be 2: \t" + tester2.getStoredEntries());
        System.out.println("Setting invalid positions: shouldn't do anything to array");
        tester2.set(-1, null);
        tester2.set(6000000000000L, null);
        System.out.println("Stored entires shoudl still be 2: \t" + tester2.getStoredEntries());
        tester2.set(2, null);
        tester2.set(105, null);
        System.out.println("Stored entries should be 4: \t" + tester2.getStoredEntries());
        System.out.println("get method tested, should be null and true: \t" + tester2.get(105) + "\t" + tester2.isSet(105));
        System.out.println("get method tested, should be Z and true: \t" + tester2.get(34567890) + "\t" + tester2.isSet(34567890));
        System.out.println("get method tested, should be AA and true: \t" + tester2.get(103) + "\t" + tester2.isSet(103));
        System.out.println("get method tested, should be Q and false: \t" + tester2.get(69) + "\t" + tester2.isSet(69));
       try
        {
            System.out.println("Should be error: \t" + tester2.get(-1));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
       try
       {
             System.out.println("Should be error: \t" + tester2.get(6000000000000L));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        tester2.unset(103);
        tester2.set(2, "Q");
        System.out.println("Stored entries should be 2, Q, Q, False, False: \t" + tester2.getStoredEntries() + "\t" + tester2.get(103) + "\t" +  tester2.get(2) +  "\t" + tester2.isSet(103) + "\t" + tester2.isSet(2));
        System.out.println("Getting unset indices, should be Q Q Q false false false: \t" + tester2.get(1000) + "\t" + tester2.get(2000) + "\t" + tester2.get(3000) + "\t" + tester2.isSet(1000) + "\t" + tester2.isSet(2000) + "\t" + tester2.isSet(3000)); 
        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("End of test2");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println();
    }
    
    public static void test3()
    {
        System.out.println("Test 3: sparseArray dot product, type Double"); 
        SparseArray<Double> tester3 = new SparseArray<>(5, 2.0);
        SparseArray<Double> tester4 = new SparseArray<>(5, 2.0);
        tester4.set(0, 0.0);
        tester4.set(1, 0.0);
        tester4.set(2, 0.0);
        tester4.set(3, 0.0);
        tester4.set(4, 0.0);
        SparseArray<Double> tester5 = new SparseArray<>(5, 2.0);
        SparseArray<Double> tester6 = new SparseArray<>(1);
        SparseArray<Double> tester10 = new SparseArray<>(5, 1.0);
        tester10.set(0, 1.0);
        tester10.set(1, 2.0);
        tester10.set(2, 3.0);
        tester10.set(3, 4.0);
        tester10.set(4, 5.0);
        SparseArray<Double> tester11 = new SparseArray<>(5, 1.0);
        tester11.set(0, 1.0);
        tester11.set(1, 2.0);
        tester11.set(2, 1.0);
        tester11.set(3, 2.0);
        tester11.set(4, 1.0);
        
        
        try
        {
            double x = tester3.dotProduct(tester3, tester6);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error is correctly thrown for dotProduct");
        }
        
        double t4 = tester6.dotProduct(tester3, tester4); 
        
        System.out.println("SHould be 0.0: " + t4);
        System.out.println("SHould be 20: " + tester6.dotProduct(tester3, tester5));
        System.out.println("Should be 21: " + tester6.dotProduct(tester10, tester11));
        
        
        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("End of test3");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println();
    }
    
    public static void test4()
    {
        System.out.println("Test 4: empty SparseArray with -1 as default, type Integer"); 
        SparseArray<Integer> tester = new SparseArray<>(0, -1);
        System.out.println("Default content should be -1: \t" + tester.getDefaultContent());
        System.out.println("Declaredsize should be " + 0 + ":" + "\t" + tester.getDeclaredSize());
        System.out.println("Stored entries should be 0: \t" + tester.getStoredEntries());
        System.out.println("Using the set method then get");
        tester.set(1, 0);
        tester.set(0, 0);
        tester.set(-1, 0);
        System.out.println("Stored entries should be 0: \t" + tester.getStoredEntries());
        try
        {
            System.out.println("Should be error: \t" + tester.get(0));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        try
        {
            System.out.println("Should be error: \t" + tester.get(-1));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        System.out.println("Testing isSet, unset, and entries, should be false, and 0: \t" + tester.isSet(0) + "\t" + tester.getStoredEntries());
        tester.unset(0);
        tester.unset(-1);
        System.out.println("Testing isSet, unset, and entries again, should be false, and 0: \t" + tester.isSet(0) + "\t" + tester.getStoredEntries());
    }
    
    
    public static void map1d()
    {
        Function<String, Integer> test = new F1();
        
        SparseArray<String> tester = new SparseArray<>(10, "Q");
        tester.set(0, "QQ");
        tester.set(2, "QQQ");
        tester.set(4, "QQQQ");
        tester.set(6, "QQQQQ");
        tester.set(8, "QQQQQQ");
        SparseArray<Integer> tester2 = tester.map(test);
        System.out.println("Default value should be 1: \t" + tester2.getDefaultContent());
        System.out.println("Declaredsize should be " + 10 + ":" + "\t" + tester2.getDeclaredSize());
        System.out.println("Stored entries should be 5: \t" + tester2.getStoredEntries());
        System.out.println("Should be true for isSet 0: \t" + tester2.isSet(0));
        System.out.println("Should be false for isSet -1: \t" + tester2.isSet(-1));
        System.out.println("Should be false for isSet 10: \t" + tester2.isSet(10));
        System.out.println("Should be false for isSet 1: \t" + tester2.isSet(1));
        try
        {
            System.out.println("Should be error: \t" + tester2.get(-1));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("If you are reading this, the get method works properly");
        }
        catch(Exception e)
        {
            System.out.println("If you are reading this, the get method doesn't throw proper exception");
        }
        System.out.println("Should get 2, 6, 5, 3, 4, 1, 1: \t" + tester2.get(0) + "\t" + tester2.get(8) + "\t" + tester2.get(6) + "\t" + tester2.get(2) + "\t" + tester2.get(4) + "\t" + tester2.get(1) + "\t" + tester2.get(9));
        tester2.unset(2);
        System.out.println("Should get 4, 1, false, 1, false \t" + tester2.getStoredEntries() + "\t" + tester2.get(2) + "\t" + tester2.isSet(2) + "\t" + tester2.get(9) + "\t" + tester2.isSet(9));
        tester2.set(9, 7);
        System.out.println("Should get 5, 1, false, 7, true: \t" + tester2.getStoredEntries() + "\t" + tester2.get(2) + "\t" + tester2.isSet(2) + "\t" + tester2.get(9) + "\t" + tester2.isSet(9));
    }
    
    
    
    
    public static void testM1()
    {
        SparseMatrix<Double> tester = new SparseMatrix<>(10, 10);
        System.out.println("dft should be null: \t" + tester.getDefaultEntry());
        System.out.println("10 rows: \t" + tester.getDeclaredRows());
        System.out.println("10 rows: \t" + tester.getDeclaredColumns());
        System.out.println("0 entries: \t" + tester.getStoredEntries()); 
        System.out.println("isSet(10, 0) should be false: \t" + tester.isSet(10, 0)); 
        System.out.println("isSet(0, 10) should be false: \t" + tester.isSet(0, 10));
        System.out.println("isSet(-1, -1) should be false: \t" + tester.isSet(-1, -1)); 
        System.out.println("isSet(11, 11) should be false: \t" + tester.isSet(11, 11));
        System.out.println("isSet(0, 0) should be false: \t" + tester.isSet(0, 0));
        System.out.println("isSet(9, 9) should be false: \t" + tester.isSet(9, 9));
        tester.set(3, 4, 3.14);
        System.out.println("entry should be 1, true, and 3.14 at (3,4): \t" + tester.getStoredEntries() + "\t" + tester.isSet(3,4) + "\t" + tester.get(3,4));
        tester.set(0, 0, 3.14);
        tester.set(9, 9, 3.14);
        tester.set(4, 4, 3.14);
        tester.set(3, 0, 3.14);
        tester.set(6, 6, 3.14);
        System.out.println("Entry should be 6, true, true, true, and 3.14 at (9,9), (0,0), (3,0): \t" + tester.getStoredEntries() + "\t" + tester.isSet(9,9) + "\t" + tester.isSet(0,0) + "\t" + tester.isSet(3,0) + "\t" + tester.get(9,9) + "\t" + tester.get(0,0) + "\t" + tester.get(3,0)); 
        try
        {
            tester.unset(9, 10);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.unset(10, 9);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.unset(-1, 9);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.unset(9, -1);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.set(-1, 9, null);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.set(9, -1, null);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.set(10, 9, null);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        try
        {
            tester.set(9, 10, null);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Good this error happened");
        }
        
        tester.set(3,0, 5.1);
        tester.set(0,0, null);
        tester.set(3, 9, 3.14);
        System.out.println("6, true, 5.1, false, null, true, 3.14: \t" + tester.getStoredEntries() + "\t" + tester.isSet(3,0) + "\t" + tester.get(3,0) + "\t" + tester.isSet(0,0) + "\t" + tester.get(0,0) + "\t" + tester.isSet(3,9) + "\t" + tester.get(3,9));
        tester.unset(3,4);
        tester.unset(3,9); 
        System.out.println("4, false, null, false, null: \t" + tester.getStoredEntries() + "\t" + tester.isSet(3,4) + "\t" + tester.get(3,4) + "\t" + tester.isSet(3,9) + "\t" + tester.get(3,9));
    }
    
    public static void testM2()
    {
        SparseMatrix<Integer> tester = new SparseMatrix<>(10, 10, 0);
        System.out.println("dft 0: " + tester.getDefaultEntry());
        System.out.println("r size = 3: " + tester.getDeclaredRows());
        System.out.println("c size = 4: " + tester.getDeclaredColumns());
        System.out.println("entry 0: " + tester.getStoredEntries());
        System.out.println("Entries" + + tester.getStoredEntries());
        
		tester.set(5, 0, 3);
		tester.set(2, 0, 69);
		tester.set(3, 0, -2131241);	
		tester.set(9, 4, 500);
		tester.set(1, 8, 6000);
		tester.set(0, 2, -432);
		tester.set(8, 9, 23994);
		tester.set(4, 1, 11);
		
	
		
		
		
		tester.sortColumnOrder();
		System.out.println(tester);
        
		
		
		
    }
    
    
    
   
}
