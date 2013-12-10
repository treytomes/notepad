package org.treytomes.notepad;

import java.awt.Component;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.treytomes.notepad.model.TextFileDocument;

public class FileChooserView implements Observer {

	private static final Logger LOGGER = Logger.getLogger(FileChooserView.class.getName());
	
	private static final String DEFAULT_DIRECTORY = null;
	
	private Component _parent;
	private TextFileDocument _model;
	private JFileChooser _fileChooser;
	
	public FileChooserView(Component parent, TextFileDocument model) {
		_parent = parent;
		setModel(model);
		
		configureFileChooser();
	}
	
	public FileChooserView(TextFileDocument model) {
		this(null, model);
	}
	
	public TextFileDocument getModel() {
		return _model;
	}
	
	public void setModel(TextFileDocument model) {
		if (_model == model) {
			return;
		}
		
		LOGGER.log(Level.INFO, "Assigning a new model.");
		if (model == null) {
			LOGGER.log(Level.WARNING, "Input model is null; creating a new model.");
			model = new TextFileDocument();
		}
		_model = model;
	}

	public boolean openFile() {
		_fileChooser.updateUI();
		int returnValue = _fileChooser.showOpenDialog(_parent);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = _fileChooser.getSelectedFile();
			LOGGER.log(Level.INFO, "Opening: {0}", selectedFile.getName());
			_model.open(selectedFile);
			return true;
		} else {
			LOGGER.log(Level.INFO, "Open command cancelled.");
			return false;
		}
	}
	
	public void saveFileAs() {
		_fileChooser.updateUI();
		int returnValue = _fileChooser.showSaveDialog(_parent);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			_model.save(_fileChooser.getSelectedFile());
		} else {
			LOGGER.log(Level.INFO, "Save command cancelled.");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		_fileChooser.setName(_model.getName());
	}

	private void configureFileChooser() {
		_fileChooser = new JFileChooser();

		_fileChooser.setCurrentDirectory(getDefaultDirectory());
		
		FileFilter fileFilter = new FileNameExtensionFilter("Text Files", new String[] { "txt" });
		_fileChooser.addChoosableFileFilter(fileFilter);
		_fileChooser.setFileFilter(fileFilter);
	}
	
	private File getDefaultDirectory() {
		if ((DEFAULT_DIRECTORY == null) || DEFAULT_DIRECTORY.isEmpty()) {
			return null;
		} else {
			return new File(DEFAULT_DIRECTORY);
		}
	}
}
