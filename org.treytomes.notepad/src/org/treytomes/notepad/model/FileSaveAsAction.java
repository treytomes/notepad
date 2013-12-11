package org.treytomes.notepad.model;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import org.treytomes.notepad.FileChooserView;

public class FileSaveAsAction extends AbstractAction implements PropertyChangeListener {

	private static final long serialVersionUID = 8011881956860273769L;
	
	private static final Logger LOGGER = Logger.getLogger(FileSaveAsAction.class.getName());
	
	private FileChooserView _fileChooser;
	
	protected FileSaveAsAction(String name, FileChooserView fileChooser) {
		super(name);
		
		_fileChooser = fileChooser;
		getModel().addPropertyChangeListener(this);
		updateEnabled();
	}
	
	public FileSaveAsAction(FileChooserView fileChooser) {
		this("Save As...", fileChooser);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFileAs();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof TextFileDocument) {
			switch (evt.getPropertyName()) {
			case "needsSave":
				LOGGER.log(Level.INFO, "Updating saved state.");
				updateEnabled();
				break;
			}
		}
	}
	
	protected void updateEnabled() {
		setEnabled(getModel().getNeedsSave());
	}
	
	public void saveFileAs() {
		_fileChooser.saveFileAs();
	}
	
	protected TextFileDocument getModel() {
		return _fileChooser.getModel();
	}
}
