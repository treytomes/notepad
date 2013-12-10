package org.treytomes.notepad;

import java.io.File;
import java.util.Observable;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.treytomes.util.FileIO;

public class TextFileModel extends Observable implements DocumentListener {

	private static final String DEFAULT_FILENAME = "Untitled.txt";
	
	private File _file;
	private String _contents;
	private boolean _needsSave;
	
	public TextFileModel() {
		_file = new File(DEFAULT_FILENAME);
		_contents = "";
		_needsSave = false;
	}
	
	public String getName() {
		return _file.getName();
	}
	
	public String getContents() {
		return _contents;
	}
	
	public boolean getNeedsSave() {
		return _needsSave;
	}
	
	private void setNeedsSave(boolean value) {
		_needsSave = value;
	}

	public void open(String path) {
		_file = new File(path);
		_contents = FileIO.readTextFile(_file);
		setNeedsSave(false);
		setChanged();
		notifyObservers();
	}
	
	public void save(String path) {
		_file = new File(path);
		FileIO.writeTextFile(_file, _contents);
		setNeedsSave(false);
		setChanged();
		notifyObservers();
	}

	@Override
	public void changedUpdate(DocumentEvent evt) {
		updateContents(evt);
	}

	@Override
	public void insertUpdate(DocumentEvent evt) {
		updateContents(evt);
	}

	@Override
	public void removeUpdate(DocumentEvent evt) {
		updateContents(evt);
	}
	
	private void updateContents(DocumentEvent evt) {
		Document doc = evt.getDocument();
		try {
			_contents = doc.getText(0, doc.getLength());
			setNeedsSave(true);
			setChanged();
			notifyObservers();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
