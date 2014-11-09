package document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Document {
	
	private File file;
	private String fileName;
	private LinkedHashSet<String> collectionParagraphs;
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public Document(String fileName) { this.fileName = fileName; }
	
	/** Constructor establishes the association with a disk-based file, so that all tests are performed on the same set of data. */
	public Document(File file) { this.file = file; }
	
	/** loads the paragraphs from the document into an observable ArrayList. Each line is considered to be a separate paragraph */
	public void load() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		try	(Scanner input = new Scanner(new File(fileName))) {
			collectionParagraphs = new LinkedHashSet<String>();
			while (input.hasNext()){
				String paragraph = input.nextLine().intern();
				collectionParagraphs.add(paragraph);
			}
		} catch (FileNotFoundException e){ e.printStackTrace(); }
		
		double elapseTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		System.out.printf("Collection Type:%s Document.load() Word Count:%d Elapse Time:%.3f seconds\n", collectionParagraphs.getClass().getName(), collectionParagraphs.size(), elapseTime);
	}
	
	/** returns the size of the collection of paragraphs */
	public int getSize() { return collectionParagraphs.size(); }
	
	/** returns the file name of the document */
	public String getFileName() { return fileName; }
	
	/** returns an abstract representation of the document */
	public File getFile(){ return file; }
	
	/** returns the collection of paragraphs as an unmodifiable list so the client gets read-only access */
	public LinkedHashSet<String> getSet(){ return collectionParagraphs; }
	
}//end class
