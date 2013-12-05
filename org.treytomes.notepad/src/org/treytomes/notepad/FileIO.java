package org.treytomes.notepad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileIO {
	
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	
	public static String readTextFile(File file) {
		System.out.println("Reading text file: " + file.getName());
		
		StringBuilder sb = new StringBuilder();
		
		if (file.exists()) {
			try (BufferedReader reader = Files.newBufferedReader(file.toPath(), ENCODING)) {
				int nextCh = -1;
				while ((nextCh = reader.read()) != -1) {
					sb.append((char)nextCh);
				}
			} catch (IOException e) {
				System.err.println("Unable to read from file.");
			}
		} else {
			System.err.println("File does not exist.");
		}
		
		System.out.println("Done reading text file.");
		
		return sb.toString();
	}
	
	public static void writeTextFile(File file, String text) {
		System.out.println("Writing text file: " + file.getName());
		
		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), ENCODING)) {
			for (int index = 0; index < text.length(); index++) {
				char ch = text.charAt(index);
				writer.write(ch);
			}
		} catch (IOException e) {
			System.err.println("Unable to write to file.");
		}
		
		System.out.println("Done writing text file.");
	}

}
