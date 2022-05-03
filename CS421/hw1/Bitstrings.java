import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bitstrings {
	public static void main(String[] args) {
		if (isNotExpectedArgsAmount(args)) {
			throw new IllegalArgumentException(Constants.ONE_INPUT_FILE);
		}
		testFile(args[0]);
	}

	private static boolean isNotExpectedArgsAmount(String[] args) {
		if (args.length != Constants.EXPECTED_ARG_AMOUNT) {
			return true;
		}
		return false;
	}

	private static void testFile(String inputFileName) {
		List<String> data = readFile(inputFileName);
		evaluate(data);
		for (String resultData : data) {
			System.out.println(resultData);
		}
	}

	private static List<String> readFile(String filename) {
		List<String> inputData = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				inputData.add(currentLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputData;
	}

	private static void evaluate(List<String> data) {
		if (data == null) {
			return;
		}
		TokenStreamParser parser = new TokenStreamParser();
		for (int i = 0; i < data.size(); i++) {
			parser.setInput(data.get(i));
			data.set(i, parser.evaluate());
		}
	}
}
