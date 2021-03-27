import java.io.IOException;
import java.util.Arrays;

public class Tester {
	private static class TestInput {
		int key;
		char[][] values;

		public TestInput(int key, char[]... values) {
			this.key = key;
			this.values = new char[values.length][];
			for (int i = 0; i < values.length; i++) {
				this.values[i] = values[i];
			}
		}
	}

	private static TestInput[] test1Inputs = { new TestInput(1, "Tesing".toCharArray(), "Yo".toCharArray()),
			new TestInput(2, "Ethan".toCharArray(), "Booker".toCharArray()),
			new TestInput(3, "Bobby Jr".toCharArray(), "Jacobsen".toCharArray()),
			new TestInput(4, "Abby".toCharArray(), "Olsen".toCharArray()),
			new TestInput(5, "Sammy".toCharArray(), "Milliren".toCharArray()),
			new TestInput(6, "Alyssa".toCharArray(), "Holmen".toCharArray()),
			new TestInput(7, "Oscar".toCharArray(), "Robertson".toCharArray()),
			new TestInput(8, "Kylo".toCharArray(), "Ren".toCharArray()),
			new TestInput(9, "Jackie".toCharArray(), "Chan".toCharArray()),
			new TestInput(10, "Summer".toCharArray(), "Winter".toCharArray()),
			new TestInput(11, "Felicia".toCharArray(), "Keys".toCharArray())
	};

	private static TestInput[] test2Inputs = { new TestInput(2, "Tesing".toCharArray(), "Yo".toCharArray()),
			new TestInput(6, "Ethan".toCharArray(), "Booker".toCharArray()),
			new TestInput(10, "Bobby Jr".toCharArray(), "Jacobsen".toCharArray()),
			new TestInput(114, "Abby".toCharArray(), "Olsen".toCharArray()),
			new TestInput(242, "Sammy".toCharArray(), "Milliren".toCharArray()),
			new TestInput(498, "Alyssa".toCharArray(), "Holmen".toCharArray()),
			new TestInput(1010, "Oscar".toCharArray(), "Robertson".toCharArray()),
			new TestInput(2034, "Kylo".toCharArray(), "Ren".toCharArray()),
			new TestInput(4082, "Jackie".toCharArray(), "Chan".toCharArray()),
			new TestInput(8178, "Summer".toCharArray(), "Winter".toCharArray()) };

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		test1();
		long finish = System.currentTimeMillis();

		long timeElapsed = finish - start;
		System.out.println(timeElapsed);
	}

	private static void test1() {
		try {
			DBTable dbt = new DBTable("test", new int[] { 20, 20 }, 2);

			//
			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				dbt.insert(input.key, input.values);
			}
			
			
			dbt.close();

			dbt = new DBTable("test");
			for (TestInput input : test1Inputs) {
				//System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}
			dbt.printBuckets();
			// 4, 3, 6, 8, 7 //1-10
			int[] keyOrder = { 5, 4, 3, 6, 8, 7, 2, 10, 1, 9, 11 };
			for (int key : keyOrder) {
				System.out.println(dbt.remove(key));
			}
			dbt.printBuckets();
			dbt.printBuckets();
			System.out.println("AFTER AFTER AFTER AFTER AFTER\n\n\n");
			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				dbt.insert(input.key, input.values);
			}
			dbt.printBuckets();

			for (TestInput input : test1Inputs) {
				//System.out.println(dbt.insert(input.key, input.values));
			}
			
			for (TestInput input : test1Inputs) {
				System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
			}
		

			for (TestInput input : test1Inputs) {
				System.out.println(dbt.insert(input.key, input.values));
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
			}

			for (TestInput input : test1Inputs) {
				System.out.println(dbt.insert(input.key, input.values));
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
			}
			
			dbt.printBuckets();
			dbt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void test2() {
		try {
			DBTable dbt = new DBTable("test", new int[] { 20, 20 }, 1);

			for (int i = 0; i < test2Inputs.length; i++) {
				TestInput input = test2Inputs[i];
				dbt.insert(input.key, input.values);

			}

			// dbt.printDirectory();
			// dbt.printBuckets();

			dbt.close();

			dbt = new DBTable("test");

			for (TestInput input : test2Inputs) {
				// System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}

			for (int i = 0; i < test2Inputs.length; i++) {
				TestInput input = test2Inputs[i];
				System.out.println(dbt.remove(input.key));
				// dbt.printDirectory();
			}

			// dbt.printDBTable();

			for (TestInput input : test2Inputs) {
				// System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}

			for (int i = 0; i < test2Inputs.length; i++) {
				TestInput input = test2Inputs[i];
				dbt.insert(input.key, input.values);
			}

			for (int i = 0; i < test2Inputs.length; i++) {
				TestInput input = test2Inputs[i];
				System.out.println(dbt.remove(input.key));
			}

			// dbt.printBuckets();

			dbt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void test3() {
		try {
			DBTable dbt = new DBTable("test", new int[] { 20, 20 }, 1);

			for (int i = 0; i < test1Inputs.length / 2; i++) {
				TestInput input = test1Inputs[i];
				dbt.insert(input.key, input.values);
			}

			// dbt.printDirectory();
			// dbt.printBuckets();

			dbt.close();

			dbt = new DBTable("test");

			for (TestInput input : test1Inputs) {
				// System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}

			// dbt.printDBTable();
			// dbt.printBuckets();
			for (int i = test1Inputs.length / 4; i < test1Inputs.length / 3; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
				// dbt.printDBTable();
				// dbt.printBuckets();
				// dbt.printDirectory();
			}

			// dbt.printDBTable();

			for (TestInput input : test1Inputs) {
				// System.out.println(dbt.search(input.key));
				// System.out.println(dbt.search(input.key + 100000));
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				dbt.insert(input.key, input.values);
			}

			for (int i = test1Inputs.length / 2; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
				// dbt.printDBTable();
				// dbt.printBuckets();
				// dbt.printDirectory();
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				dbt.insert(input.key, input.values);
			}

			for (int i = 0; i < test1Inputs.length; i++) {
				TestInput input = test1Inputs[i];
				System.out.println(dbt.remove(input.key));
			}

			dbt.printDBTable();
			dbt.printBuckets();
			dbt.printDirectory();

			dbt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
