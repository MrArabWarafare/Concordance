package document;

import java.io.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CopyOfDictionary {
	
	private String fileName;
	private File file;
	private ObservableList<String> collectionWords;
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public CopyOfDictionary(String fileName) { this.fileName = fileName; }
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public CopyOfDictionary(File file) { this.file = file; }
	
	/**
	 * Uses the predefined filename to open a file and populate the collection created here.
	 * @throws IOException 
	 */
	public void load() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		TreeSet<String> treeSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		try	(Scanner input = new Scanner(new File(fileName))) {
			while (input.hasNext()){
				String word = input.nextLine();
				treeSet.add(word);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		collectionWords = FXCollections.observableList(new ArrayList<String>(treeSet)); //pass the TreeSet into the ArrayList and make it observable
		double elapseTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		System.out.printf("Collection Type:%s Dictionary.load() Word Count:%d Elapse Time:%.3f seconds\n", collectionWords.getClass().getName(), collectionWords.size(), elapseTime);
	}
	
	/** returns the collection of words as an unmodifiable list so the client gets read-only access */
	public ObservableList<String> getList() { return collectionWords; }
	
	/** returns the size of the collection of words. */
	public int getSize() { return collectionWords.size(); }
	
	public String getFileName() { return fileName; }
	public File getFile() { return file; }
		
	/** returns a boolean indicating whether the collection of words contains the word (key) that is being searched for */
	public boolean lookup (String word) {
		return Collections.binarySearch(collectionWords, word, String.CASE_INSENSITIVE_ORDER) >= 0;
	}
	
} // end class Dictionary