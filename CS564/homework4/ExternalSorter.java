import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ExternalSorter {
	private class Page {
		private final int MAX_SIZE;
		List<Integer> buffer;
		RandomAccessFile inputFile;

		Page(File file, int pageSize) throws IOException {
			MAX_SIZE = pageSize;
			buffer = new LinkedList<>();
			inputFile = new RandomAccessFile(file, "rw");
			file.toPath();
			fill();
		}

		private void fill() throws IOException {
			while (!isFullBuffer() && !isEndOfFile()) {
				buffer.add(inputFile.readInt());
			}
		}

		public boolean isEmptyBuffer() {
			return buffer.isEmpty();
		}

		private boolean isEndOfFile() throws IOException {
			return inputFile.getFilePointer() >= inputFile.length();
		}

		public boolean isFullBuffer() {
			return buffer.size() == MAX_SIZE;
		}

		public int peek() {
			return buffer.get(0);
		}

		public int popAndPush() throws IOException {
			int result = buffer.remove(0);

			if (!isEndOfFile()) {
				buffer.add(inputFile.readInt());
			}

			return result;
		}

		public void push(int i) {
			buffer.add(i);
		}

		public void dump() throws IOException {
			dumpNumbers(inputFile, buffer);
			buffer.clear();
			fill();
		}

		public void clear() throws IOException {
			inputFile.close();
			buffer = null;
		}
	}

	private static int getMinIndex(List<Page> pages) {
		int minIndex = 0;
		int minValue = pages.get(0).peek();
		for (int i = 1; i < pages.size(); i++) {
			Page page = pages.get(i);
			if (page.peek() < minValue) {
				minIndex = i;
				minValue = page.peek();
			}
		}
		return minIndex;
	}

//implements K-Way External Merge Sort

	public ExternalSorter(String in, String out, int numBuffers, int pageSize) throws IOException {
		// in is the name of an unsorted binary file of ints
		// out is the name of the output binary file (the destination of the sorted
		// ints)
		// numBuffers is the number of in memory page buffers available for sorting
		// pageSize is the number of ints in a page
		RandomAccessFile inputFile = new RandomAccessFile(in, "r");
		List<Path> tempFiles = splitFile(inputFile, numBuffers * pageSize);
		Path mergedFilePath = mergeSortedFiles(tempFiles, numBuffers, pageSize, out);
		rename(mergedFilePath, out);
	}

	private List<Path> splitFile(RandomAccessFile inputFile, int maxInts) throws IOException {
		List<Integer> bigBuffer = new LinkedList<>();
		List<Path> splitFiles = new LinkedList<>();

		while (inputFile.getFilePointer() < inputFile.length()) {
			bigBuffer.add(inputFile.readInt());

			if (bigBuffer.size() == maxInts) {
				Path outputFile = splitWriteOut(bigBuffer);
				splitFiles.add(outputFile);
			}
		}

		if (!bigBuffer.isEmpty()) { // clean up leftover
			Path outputFile = splitWriteOut(bigBuffer);
			splitFiles.add(outputFile);
		}

		inputFile.close();
		return splitFiles;
	}

	private void rename(Path path, String newFileName) throws IOException {
		File outputFile = new File(newFileName);
		Files.deleteIfExists(outputFile.toPath());
		path.toFile().renameTo(outputFile);
	}

	private Path mergeSortedFiles(List<Path> sortedFiles, int numBuffers, int pageSize, String outfile)
			throws IOException {
		List<Path> mergedPaths = sortedFiles;
		List<Path> outputPaths = new LinkedList<>();

		while (mergedPaths.size() > 1) {
			while (!mergedPaths.isEmpty()) {
				// 1 buffer used for merging, so at most, numBuffers - 1 is max number of buckets we can work with.
				List<Path> unmergedPaths = mergedPaths.subList(0, Math.min(numBuffers - 1, mergedPaths.size()));
				Path mergedSortedFile = mergeSortedFilesHelper(unmergedPaths, pageSize);
				unmergedPaths.clear();
				outputPaths.add(mergedSortedFile);
			}

			mergedPaths.addAll(outputPaths);
			outputPaths.clear();
		}

		return mergedPaths.get(0);
	}

	private Path mergeSortedFilesHelper(List<Path> sortedFilePaths, int pageSize) throws IOException {
		List<Page> pages = new ArrayList<>(sortedFilePaths.size());
		for (Path p : sortedFilePaths) {
			pages.add(new Page(p.toFile(), pageSize));
		}

		Path outputPath = Files.createTempFile("inputs", ".tmp");
		Page outputPage = new Page(outputPath.toFile(), pageSize);

		while (!pages.isEmpty()) {
			int minIndex = getMinIndex(pages);
			Page minPage = pages.get(minIndex);
			outputPage.push(minPage.popAndPush());

			if (outputPage.isFullBuffer()) {
				outputPage.dump();
			}

			if (minPage.isEmptyBuffer()) { // no more content in this page, remove and clean up
				Page removePage = pages.remove(minIndex);
				removePage.clear();
			}
		}

		if (!outputPage.isEmptyBuffer()) {
			outputPage.dump();
		}
		
		outputPage.clear();
		return outputPath;
	}

	private static Path splitWriteOut(List<Integer> numbers) throws IOException {
		Collections.sort(numbers);
		Path tempPath = Files.createTempFile("inputs", ".tmp");
		RandomAccessFile tempFile = new RandomAccessFile(tempPath.toFile(), "rw");
		dumpNumbers(tempFile, numbers);
		tempFile.close();
		numbers.clear();
		return tempPath;
	}

	private static void dumpNumbers(RandomAccessFile output, List<Integer> numbers) throws IOException {
		output.seek(output.length());
		for (int num : numbers) {
			output.writeInt(num);
		}
	}
}
