package test;

import java.io.FileNotFoundException;

import spellChecker.SpellChecker;

public class TestClass {
	
	public static void main(String[] args) throws FileNotFoundException {
		SpellChecker spellcheck = new SpellChecker();
		
		spellcheck.loadMisspelledWords();
		spellcheck.loadConcordance();
	}
}
