package org.treytomes.notepad;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.treytomes.util.FileIO;

/**
 * 2 properties are exposed to the PropertyChangeListener:
 * 	* needsSave
 * 	* filename
 * @author ttomes
 *
 */
public class TextFileDocument extends PlainDocument implements DocumentListener {
	
	private static final Logger LOGGER = Logger.getLogger(TextFileDocument.class.getName());
	
	private static final long serialVersionUID = 3307261840579009310L;

	private static final String DEFAULT_FILENAME = "";
	private static final String PROPERTY_NEEDSSAVE = "needsSave";
	private static final String PROPERTY_FILENAME = "filename";
	
	private PropertyChangeSupport _propertyChangeSupport;
	
	private File _file;
	private boolean _needsSave;

	public TextFileDocument() {
		super();
		
		addDocumentListener(this);
		_propertyChangeSupport = new PropertyChangeSupport(this);
		
		_file = new File(DEFAULT_FILENAME);
		_needsSave = false;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	private void setFile(File file) {
		String oldFilename = _file.getName();
		_file = file;
		
		if (!_file.getName().equals(oldFilename)) {
			_propertyChangeSupport.firePropertyChange(PROPERTY_FILENAME, oldFilename, _file.getName());
		}
	}
	
	public String getName() {
		return _file.getName();
	}
	
	public boolean getNeedsSave() {
		return _needsSave;
	}
	
	public boolean isUntitled() {
		return _file.getName().length() == 0;
	}
	
	private void setNeedsSave(boolean value) {
		if (_needsSave != value) {
			boolean oldValue = _needsSave;
			_needsSave = value;
			_propertyChangeSupport.firePropertyChange(PROPERTY_NEEDSSAVE, oldValue, value);
		}
	}
	
	private String getContents() throws BadLocationException {
		return getText(0, getLength());
	}

	private void setContents(String contents) throws BadLocationException {
		remove(0, getLength());
		insertString(0, contents, null);
	}
	
	public void open(File file) {
		setFile(file);
		
		String contents = FileIO.readTextFile(file);
		
		Charset srcEncoding = Charset.defaultCharset();
		Charset dstEncoding = StandardCharsets.UTF_8;
		byte[] contentBytes = contents.getBytes(srcEncoding);
		contents = new String(contentBytes, dstEncoding);
		
		try {
			setContents(contents);
			setNeedsSave(false);
		} catch (BadLocationException e) {
			LOGGER.log(Level.WARNING, "Unable to set document text.");
		}
	}
	
	public void save(File file) {
		setFile(file);
		
		try {
			String contents = getContents();
			FileIO.writeTextFile(_file, contents);
			setNeedsSave(false);
		} catch (BadLocationException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Unable to save the file: {0}", _file);
		}
	}

	
	@Override
	public void changedUpdate(DocumentEvent e) {
		onContentChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onContentChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onContentChanged();
	}
	
	private void onContentChanged() {
		setNeedsSave(true);
	}
}
