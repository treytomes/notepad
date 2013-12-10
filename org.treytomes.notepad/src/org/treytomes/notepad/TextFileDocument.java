package org.treytomes.notepad;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.treytomes.util.FileIO;

public class TextFileDocument extends PlainDocument {
	
	private static final Logger LOGGER = Logger.getLogger(TextFileDocument.class.getName());
	
	private static final long serialVersionUID = 3307261840579009310L;

	private static final String DEFAULT_FILENAME = "Untitled.txt";
	
	private File _file;
	private boolean _needsSave;

	public TextFileDocument() {
		super();
		
		_file = new File(DEFAULT_FILENAME);
		_needsSave = false;
		
		// TODO: When the filename changes, update NotepadView.
	}
	
	public String getName() {
		return _file.getName();
	}
	
	public boolean getNeedsSave() {
		return _needsSave;
	}
	
	private void setNeedsSave(boolean value) {
		_needsSave = value;
	}

	public void open(File file) {
		_file = file;
		
		String contents = FileIO.readTextFile(file);
		
		Charset srcEncoding = Charset.defaultCharset();
		Charset dstEncoding = StandardCharsets.UTF_8;
		byte[] contentBytes = contents.getBytes(srcEncoding);
		contents = new String(contentBytes, dstEncoding);
		
		try {
			remove(0, getLength());
			insertString(0, contents, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Unable to set document text from file: {0}", file);
		}
		
		setNeedsSave(false);
	}
	
	public void save(File file) {
		_file = file;
		
		try {
			String contents = getText(0, getLength());
			FileIO.writeTextFile(_file, contents);
			setNeedsSave(false);
		} catch (BadLocationException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Unable to save the file: {0}", _file);
		}
	}
}
