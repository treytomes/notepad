package org.treytomes.util.testing;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.treytomes.util.FileIO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This file aggregates all of the unit tests for the FileIO class.
 * 
 * @author ttomes
 *
 */
public class FileIOTests extends TestCase {
	
	private static final Logger LOGGER = Logger.getLogger(FileIOTests.class.getName());
	
	private static final String FILE_TEMP_PREFIX = "temp";
	private static final String FILE_TEMP_EXT = ".txt";
	private static final String FILE_TEXT_TEXT = "This is a test of the emergency broadcast system.";

	/**
	 * Create a test suite containing all unit tests for the FileIO class.
	 * 
	 * @return A test suite containing all unit tests.
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		// Add tests here.
		suite.addTestSuite(FileIOTests.class);
		
		return suite;
	}
	
	/**
	 * Setup the test environment.
	 */
	@Override
	protected void setUp() throws Exception {
	}
	
	/**
	 * Tear down the tets environment.
	 */
	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * Can a file be created and written to?
	 */
	public void test_Can_write_a_file() {
		createTestFile();
	}
	
	/**
	 * Can a file's contents be read and returned?
	 */
	public void test_Can_read_a_file() {
		File testFile = createTestFile();
		String fileText = FileIO.readTextFile(testFile);
		assertNotNull(fileText);
		assertTrue(fileText.length() > 0);
		assertEquals(FILE_TEXT_TEXT, fileText);
	}
	
	private File createTestFile() {
		LOGGER.log(Level.INFO, "Creating the test file.");
		File testFile = getTempFile();
		FileIO.writeTextFile(testFile, FILE_TEXT_TEXT);
		assertTrue(testFile.exists());
		return testFile;
	}
	
	private File getTempFile() {
		try {
			LOGGER.log(Level.INFO, "Creating a temporary filename.");
			File tempFile = File.createTempFile(FILE_TEMP_PREFIX, FILE_TEMP_EXT);
			tempFile.deleteOnExit();
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			return null;
		}
	}
}
