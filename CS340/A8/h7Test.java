import java.io.*;
import java.util.*;

public class h7Test {

		DBTable t1;  //stores keys with a first name and last name
		DBTable t2;  //stores keys with 3 additional string versions of ints
		DBTable t3;  //stores keys with 1 addition string version of random ints
		

		int t1Fields[] = {15, 30};
		int t2Fields[] = {5, 10, 30};
		int t3Fields[] = {35};

	private void insert_t1(String filename) throws IOException {
		System.out.println("Inserts into t1");
		BufferedReader b = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = b.readLine()) != null) {
			String fields[] = line.split(",");
			int key = new Integer(fields[0]);
			char f[][] = new char[2][];
			f[0] = Arrays.copyOf(fields[1].toCharArray(), 15);
			f[1] = Arrays.copyOf(fields[2].toCharArray(), 30);
			t1.insert(key, f);
		}
	}
	private void insert_t2_t3(int limit)   throws IOException{
	//used to generate data for t2 and t3
		int i;
		System.out.println("Inserts into t2");
		for (i = 0; i < limit; i = i+5) {
			t2.insert(i, makeFields(t2Fields, i));
		}
		for (i = limit+1; i >= 1 ; i = i-5) {
			t2.insert(i, makeFields(t2Fields, i));
		}

		System.out.println("Random inserts int t3");
		Random r1 = new Random(1000);
		for (i = 0; i < limit; i++) {
			int k = r1.nextInt(100);
			t3.insert(k, makeFields(t3Fields, k));
		}
	}

	private void search(int val)  throws IOException {

		LinkedList<String> fields1;
		LinkedList<String> fields2;
		LinkedList<String> fields3;

		fields1 = t1.search(val);
		print(fields1, val);
		fields2 = t2.search(val);
		print(fields2, val);
		fields3 = t3.search(val);
		print(fields3, val);

	}

	private void rangeSearch1 (DBTable t, int low, int high) throws IOException {
		LinkedList<LinkedList<String>> list1 = t.rangeSearch(low, high);
		while (list1.size() > 0) {
			LinkedList<String> list2 = list1.remove(); 
			while (list2.size() > 0) {
				System.out.print(list2.remove()+" ");
			}
			System.out.println();
		}

	}
    
    private void rangeSearch2(int low, int high) throws IOException  {
        LinkedList<LinkedList<String>> list1;
        LinkedList<LinkedList<String>> list2;
        LinkedList<LinkedList<String>> list3;
        
        System.out.println("Range Search table1");
        list1 = t1.rangeSearch(low, high);
        printRangeList(list1);
        
        System.out.println("Range Search table2");
        list2 = t2.rangeSearch(low, high);
        printRangeList(list2);
        
        System.out.println("Range Search table3");
        list3 = t3.rangeSearch(low, high);
        printRangeList(list3);
    }



	private void print(LinkedList<String> f, int k) {
		if (f.size() == 0) { 
			System.out.println("Not Found "+k);
			return;
		}
		System.out.print(""+k+" ");
		for (int i = 0; i < f.size(); i++)
			System.out.print(f.get(i)+" ");
		System.out.println();
	}
    
    private void printRangeList(LinkedList<LinkedList<String>> list) throws IOException  {
        for (int i = 0; i < list.size(); i++) {
            LinkedList<String> items= list.get(i);
            int k = new Integer(items.removeFirst());
            print(items, k);
        }
    }

	private char[][] makeFields(int fields[], int k) {
		char f[][] = new char[fields.length][];
		for (int i = 0; i < f.length; i++) {
			f[i] = Arrays.copyOf((new Integer(k)).toString().toCharArray(), fields[i]);
		}
		return f;
	}

			
	public h7Test() throws IOException {
		int limit;

		t1 = new DBTable("f1", t1Fields, 60);
		t2 = new DBTable("f2", t2Fields, 132);
		t3 = new DBTable("f3", t3Fields, 72);

		//Insert data into t1
		insert_t1("authors.txt");

		//insert into t2 and t3
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the input limit: ");
		limit = scan.nextInt();

		insert_t2_t3(limit);

		System.out.println("Range Search t1");
		rangeSearch1(t1, 0, 25);

		System.out.println("Range Search t2");
		rangeSearch1(t2, 0, limit);

		System.out.println("Range Search t3");
		rangeSearch1(t3, 0, 100);

		System.out.print("Enter a search value or -1 to quit: ");
		int val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}

		

		t1.close();
		t2.close();
		t3.close();

		t1 = new DBTable("f1");
		t2 = new DBTable("f2");
		t3 = new DBTable("f3");

		System.out.println("insert rows 0 and 30 into t1");

		char f[][] = new char[2][];
		f[0] = Arrays.copyOf("David".toCharArray(), 15);
		f[1] = Arrays.copyOf("Hilbert".toCharArray(), 30);
		t1.insert(30, f);

		f[0] = Arrays.copyOf("Alonzo".toCharArray(), 15);
		f[1] = Arrays.copyOf("Church".toCharArray(), 30);
		t1.insert(0, f);

		System.out.println("Range Search t1");
		rangeSearch1(t1, 0, 32);
        
        System.out.print("Enter a low range value or -1 to quit: ");
        int low = scan.nextInt();
        while (low != -1) {
            System.out.print("Enter a high range value: ");
            int high = scan.nextInt();
            rangeSearch2(low, high);
            System.out.print("\nEnter a low range or -1 to quit: ");
            low = scan.nextInt();
        }


		System.out.print("Enter a search value or -1 to quit: ");
		val = scan.nextInt();
		while (val != -1) {
			search(val);
			System.out.print("\nEnter a search value or -1 to quit: ");
			val = scan.nextInt();
		}

        
	

		t1.close();
		t2.close();
		t3.close();
	}



	public static void main(String args[])  throws IOException  {
		new h7Test();
	}
}
