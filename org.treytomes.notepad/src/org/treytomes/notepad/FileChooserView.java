package org.treytomes.notepad;

import java.awt.Component;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserView implements Observer {

	private static final String DEFAULT_DIRECTORY = null;
	
	private Component _parent;
	private TextFileModel _model;
	private JFileChooser _fileChooser;
	
	public FileChooserView(Component parent) {
		_parent = parent;
		setModel(null);
		
		configureFileChooser();
	}
	
	public FileChooserView() {
		this(null);
	}
	
	public TextFileModel getModel() {
		return _model;
	}
	
	public void setModel(TextFileModel model) {
		if (model == null) {
			System.err.println("Input model is null; creating a new model.");
			model = new TextFileModel();
		}
		_model = model;
	}

	public boolean openFile() {
		_fileChooser.updateUI();
		int returnValue = _fileChooser.showOpenDialog(_parent);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			_model.open(_fileChooser.getSelectedFile().getPath());
			return true;
		} else {
			System.out.println("Open command cancelled.");
			return false;
		}
	}
	
	public void saveFileAs() {
		_fileChooser.updateUI();
		int returnValue = _fileChooser.showSaveDialog(_parent);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			_model.save(_fileChooser.getSelectedFile().getPath());
		} else {
			System.out.println("Save command cancelled.");
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
