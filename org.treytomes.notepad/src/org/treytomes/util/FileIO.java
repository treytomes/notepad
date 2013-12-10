package org.treytomes.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIO {
	
	private static final Logger LOGGER = Logger.getLogger(FileIO.class.getName());
	
	private static final Charset ENCODING = Charset.defaultCharset();
	
	public static String readTextFile(File file) {
		LOGGER.log(Level.INFO, "Reading text file: {0}", file.getName());
		
		StringBuilder sb = new StringBuilder();
		
		if (file.exists()) {
			try (BufferedReader reader = Files.newBufferedReader(file.toPath(), ENCODING)) {
				int nextCh = -1;
				while ((nextCh = reader.read()) != -1) {
					sb.append((char)nextCh);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			LOGGER.log(Level.WARNING, "File does not exist.");
		}
		
		LOGGER.log(Level.INFO, "Done reading text file.");
		return sb.toString();
	}
	
	public static void writeTextFile(File file, String text) {
		LOGGER.log(Level.INFO, "Writing text file: {0}", file.getName());
		
		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), ENCODING)) {
			for (int index = 0; index < text.length(); index++) {
				char ch = text.charAt(index);
				writer.write(ch);
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to write to file.");
		}
		
		LOGGER.log(Level.INFO, "Done writing text file.");
	}

}
