package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.*;

import spellChecker.SpellChecker;
import javafx.collections.ObservableList;

/**
 * jUnit class to support testing of classes Dictionary and SpellChecker
 */
public class JUnitDictionaryLoadTest {
	private static SpellChecker spellcheck;
	private static long originalMainDictionaryChecksum; // Numeric representation of the cumulative data. Used to compare with parallel, sorted data sets.

	public JUnitDictionaryLoadTest() { }

	@BeforeClass
	public static void setUpClass() throws FileNotFoundException {
		spellcheck = new SpellChecker();
		spellcheck.loadMisspelledWords();
		spellcheck.loadConcordance();
		originalMainDictionaryChecksum = calculateChecksum(spellcheck.getMainDictionary().getCollection()); // Capture checksum of original UNsorted data.
	}

	@Ignore @AfterClass
	// performed once-and-only once after the test class is instantiated and after all test routines have terminated
	public static void tearDownClass() { }

	@Ignore @Before
	// performed before each test
	public void setUp() { }

	@Ignore @After
	// performed after each test
	public void tearDown() { }

	@Test
	public void sortMisspelledMapOrderTest() {
		String previous = null; // on the first iteration, there will be NO previous item
		for (Entry<String, LinkedHashSet<String>> current : spellcheck.getMisspelledMap().entrySet()) {
			if (previous == null) { // first iteration
				previous = current.getKey(); // capture current as the previous
				continue; // skip the rest of the loop to get the next current . . . then you will have a legitimate previous and current
			} // end case of first record where we capture previous
			assertTrue("Previous must be less than or equal to current", previous.compareTo(current.getKey()) < 0);
			previous = current.getKey();
		}
		long checksum = calculateChecksum(spellcheck.getMainDictionary().getCollection());
		assertEquals("The checksums should match", originalMainDictionaryChecksum, checksum);
	} // end sortMisspelledMapOrderTest()
	
	public void sortMisspelledMapOrderTest() {
		String previous = null; // on the first iteration, there will be NO previous item
		for (Entry<String, LinkedHashSet<String>> current : spellcheck.getMisspelledMap().entrySet()) {
			if (previous == null) { // first iteration
				previous = current.getKey(); // capture current as the previous
				continue; // skip the rest of the loop to get the next current . . . then you will have a legitimate previous and current
			} // end case of first record where we capture previous
			assertTrue("Previous must be less than or equal to current", previous.compareTo(current.getKey()) < 0);
			previous = current.getKey();
		}
		long checksum = calculateChecksum(spellcheck.getMainDictionary().getCollection());
		assertEquals("The checksums should match", originalMainDictionaryChecksum, checksum);
	} // end sortMisspelledMapOrderTest()

	/**
	 * A static method that sweeps through the supplied list, performing a summation of the hashcode value for each item
	 * in the list. The final long-value captures a unique value for all the aggregated data. It can be used to compare
	 * with checksums for other lists. If the checksums match, then the data sets are deemed to contain the same
	 * collection of items (though in a different order). NOTE: This is NOT a jUnit @Test case. It is a helper method
	 * that is used by other parts of the jUnit class.
	 *
	 * @param collection
	 *            The List can be a reference to any of the lists.
	 * @return
	 */
	private static long calculateChecksum(Collection<String> collection) {
		long accumulator = 0;
		for (String p : collection) {
			accumulator += p.hashCode();
		}
		return accumulator;
	} // end calculateChecksum()
} // end class JUnitPostalCodeLoadSortTest