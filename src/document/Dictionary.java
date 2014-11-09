package document;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private String fileName;
	private File file;
	private Set<String> collectionWords;
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public Dictionary(String fileName) { this.fileName = fileName; }
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public Dictionary(File file) { this.file = file; }
	
	/**
	 * Uses the predefined filename to open a file and populate the collection created here.
	 * @throws IOException 
	 */
	public void load() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		collectionWords = new HashSet<String>(); //pass the TreeSet into the ArrayList and make it observable
		try	(Scanner input = new Scanner(new File(fileName))) {
			while (input.hasNext()){
				String word = input.nextLine();
				collectionWords.add(word.toLowerCase());
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		double elapseTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		System.out.printf("Collection Type:%s Dictionary.load() Word Count:%d Elapse Time:%.3f seconds\n", collectionWords.getClass().getName(), collectionWords.size(), elapseTime);
	}
	
	/** returns the collection of words as an unmodifiable list so the client gets read-only access */
	public Collection<String> getCollection() { return collectionWords; }
	
	/** returns the size of the collection of words. */
	public int getSize() { return collectionWords.size(); }
	
	public String getFileName() { return fileName; }
	public File getFile() { return file; }
		
	/** returns a boolean indicating whether the collection of words contains the word (key) that is being searched for */
	public boolean lookup (String word) {
		return collectionWords.contains(word);
	}
	
} // end class Dictionary