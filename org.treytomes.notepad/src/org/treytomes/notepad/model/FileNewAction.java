package org.treytomes.notepad.model;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.treytomes.notepad.FileChooserView;
import org.treytomes.notepad.SaveChangesDialog;

public class FileNewAction extends AbstractAction implements PropertyChangeListener {

	private static final long serialVersionUID = -7810744733007468753L;
	
	private static final Logger LOGGER = Logger.getLogger(FileNewAction.class.getName());

	private JFrame _parent;
	private FileChooserView _fileChooser;
	
	public FileNewAction(JFrame parent, FileChooserView fileChooser) {
		_parent = parent;
		_fileChooser = fileChooser;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (getModel().getNeedsSave()) {
			askIfUserWantsToSave();
		} else {
			getModel().newFile();
		}
	}

	private void askIfUserWantsToSave() {
		SaveChangesDialog dialog = new SaveChangesDialog(_parent, getModel());
		dialog.addPropertyChangeListener(this);
		dialog.setVisible(true);
	}
	
	private TextFileDocument getModel() {
		return _fileChooser.getModel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof SaveChangesDialog) {
			switch (evt.getPropertyName()) {
			case SaveChangesDialog.PROPERTY_SAVEFILE:
				SaveFileChoice saveFile = (SaveFileChoice)evt.getNewValue();
				switch (saveFile) {
				case Save:
					LOGGER.log(Level.INFO, "Saving file.");
					new FileSaveAction(_fileChooser).saveFile();
					if (!getModel().getNeedsSave()) { // was the save operation successful?
						getModel().newFile();
					}
					break;
				case DontSave:
					LOGGER.log(Level.INFO, "Will not save file.");
					getModel().newFile();
					break;
				case Cancel:
					LOGGER.log(Level.INFO, "Canceling 'New File' action.");
					break;
				}
				break;
			}
		}
	}
}