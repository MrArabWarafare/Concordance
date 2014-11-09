package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import document.Dictionary;
import document.Document;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SpellChecker {
	
	private Dictionary dictionary;
	private Document document;
	private Map<String, LinkedHashSet<String>> misspelledMap;
	private Map<String, LinkedHashSet<String>> concordanceMap;
	private String pathToDictionary = "main.txt";
	private String pathToDocument = "oliver.txt"; 
	
	/** SpellChecker constructor builds and loads the 2 dictionaries and the document */
	public SpellChecker() throws FileNotFoundException {
		dictionary = new Dictionary(pathToDictionary);
		dictionary.load();
		
		document = new Document(pathToDocument);
		document.load();
		
		loadMisspelledWords();
		loadConcordance();
	}
	
	/** parses each paragraph of the document into individual words and searches that word in the dictionaries.
	 * If the word is not found in either dictionary, it is added to the misspelledSet
	 * */
	public void loadMisspelledWords() {
		long startTime = System.currentTimeMillis();
		misspelledMap = new TreeMap<String, LinkedHashSet<String>>(String.CASE_INSENSITIVE_ORDER);
		for (String paragraph : document.getSet()){ //for every paragraph
			String[] wordList = paragraph.split("[^a-zA-Z_\u00E0-\u00EF']+"); //split the paragraph into individual words
			for (String word : wordList){ //for every word in the paragraph
				if (word.length() > 0 && !dictionary.lookup(word)){ //if its not in dictionary
					LinkedHashSet<String> paragraphsWhereWordResides = misspelledMap.get(word); //declare a reference to the key's value
					if (paragraphsWhereWordResides == null){ //if the value is null
						misspelledMap.put(word.toLowerCase(), new LinkedHashSet<>()); //create a new LinkedHashSet
						misspelledMap.get(word).add(paragraph); //put the key and LinkedHashSet in the Map
					} else {
						paragraphsWhereWordResides.add(paragraph); //else add the paragraph to the existing LinkedHashSet
					}
				}
			}
		}
		double elapseTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		System.out.printf("Collection Type:%s loadMisspelledWords() Word Count:%d Elapse Time:%.3f seconds\n", misspelledMap.getClass().getName(), misspelledMap.size(), elapseTime);
	}
	
	public void loadConcordance() {
		long startTime = System.currentTimeMillis();
		concordanceMap = new TreeMap<String, LinkedHashSet<String>>(String.CASE_INSENSITIVE_ORDER);
		for (String paragraph : document.getSet()){ //for every paragraph
			String[] wordList = paragraph.split("[^a-zA-Z_\u00E0-\u00EF']+"); //split the paragraph into individual words
			for (String word : wordList){ //for every word in the paragraph
				LinkedHashSet<String> paragraphsWhereWordResides = concordanceMap.get(word);
				if (paragraphsWhereWordResides == null) {
					concordanceMap.put(word.toLowerCase(), new LinkedHashSet<>()); //else if not in hashmap
					concordanceMap.get(word).add(paragraph); //put the word in as a new key and add the paragraph
				} else {
					concordanceMap.get(word).add(paragraph); //add paragraph to linked hash set
				}
			}
		}
		double elapseTime = (double)(System.currentTimeMillis() - startTime)/1000.0;
		System.out.printf("Collection Type:%s loadConcordance() Word Count:%d Elapse Time:%.3f seconds\n\n", concordanceMap.getClass().getName(), concordanceMap.size(), elapseTime);
	}
	
	/** returns a map of the misspelled words */
	public Map<String, LinkedHashSet<String>> getMisspelledMap() { return misspelledMap; }
	
	/** returns a map of the Concordance words */
	public Map<String, LinkedHashSet<String>> getConcordanceMap() { return concordanceMap; } 
	
	/** returns the size of the collection of misspelled words */
	public int getSize() { return misspelledMap.size(); }
	
	/** accessor methods */
	public Document getDocument(){ return document; }
	public Dictionary getMainDictionary(){ return dictionary; }
	public String getPathToDocument() { return pathToDocument; };
	
}//end class
