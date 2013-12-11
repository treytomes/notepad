package org.treytomes.notepad.model;

import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.treytomes.notepad.FileChooserView;
import org.treytomes.notepad.SaveChangesDialog;

public class FileExitAction extends WindowCloseAction implements PropertyChangeListener {

	private static final long serialVersionUID = -6555200295966128050L;
	
	private static final Logger LOGGER = Logger.getLogger(FileExitAction.class.getName());
	
	private JFrame _parent;
	private FileChooserView _fileChooser;
	
	public FileExitAction(JFrame parent, FileChooserView fileChooser) {
		super(parent);
		
		_parent = parent;
		_fileChooser = fileChooser;
	}
	
	private TextFileDocument getModel() {
		return _fileChooser.getModel();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		if (getModel().getNeedsSave()) {
			askIfUserWantsToSave();
		} else {
			super.windowClosing(e);
		}
	}

	private void askIfUserWantsToSave() {
		SaveChangesDialog dialog = new SaveChangesDialog(_parent, getModel());
		dialog.addPropertyChangeListener(this);
		dialog.setVisible(true);
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
						closeWindow();
					}
					break;
				case DontSave:
					LOGGER.log(Level.INFO, "Will not save file.");
					closeWindow();
					break;
				case Cancel:
					LOGGER.log(Level.INFO, "Canceling the 'Exit' action.");
					break;
				}
				break;
			}
		}
	}
}