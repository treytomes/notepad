package org.treytomes.notepad;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

public class FileSaveAsAction extends AbstractAction implements Observer {

	private static final long serialVersionUID = 8011881956860273769L;
	
	private FileChooserView _fileChooser;
	private TextFileModel _model;
	
	public FileSaveAsAction(FileChooserView fileChooser) {
		_fileChooser = fileChooser;
		_model = _fileChooser.getModel();
		_model.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_fileChooser.saveFileAs();
	}

	@Override
	public void update(Observable o, Object arg) {
		setEnabled(_model.getNeedsSave()); // && _model.isUntitled());
	}

}
