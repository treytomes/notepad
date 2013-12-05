package org.treytomes.notepad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Observable;

public class TextFileModel extends Observable {

	private static final Charset ENCODING = StandardCharsets.UTF_8;
	private static final String DEFAULT_FILENAME = "Untitled.txt";
	
	private File _file;
	private String _contents;
	
	public TextFileModel() {
		_file = new File(DEFAULT_FILENAME);
	}
	
	private File getFile() {
		return _file;
	}
	
	private void setFile(File file) {
		_file = file;
		setChanged();
		notifyObservers("name");
	}
	
	public String getName() {
		return getFile().getName();
	}
	
	public String getContents() {
		return _contents;
	}
	
	public void setContents(String contents) {
		_contents = contents;
		setChanged();
		notifyObservers("contents");
	}

	public void open(String path) {
		setFile(new File(path));
		setContents(readTextFile(_file));
	}
	
	public void save(String path) {
		setFile(new File(path));
		writeTextFile(getFile(), getContents());
	}
	
	private static String readTextFile(File file) {
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
	
	private static void writeTextFile(File file, String text) {
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
