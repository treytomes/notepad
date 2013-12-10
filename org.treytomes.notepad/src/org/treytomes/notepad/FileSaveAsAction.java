package org.treytomes.notepad;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileSaveAsAction extends AbstractAction implements DocumentListener {

	private static final long serialVersionUID = 8011881956860273769L;
	
	private FileChooserView _fileChooser;
	
	public FileSaveAsAction(FileChooserView fileChooser) {
		_fileChooser = fileChooser;
		getModel().addDocumentListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_fileChooser.saveFileAs();
	}

	@Override
	public void changedUpdate(DocumentEvent evt) {
		update();
	}

	@Override
	public void insertUpdate(DocumentEvent evt) {
		update();
	}

	@Override
	public void removeUpdate(DocumentEvent evt) {
		update();
	}

	public void update() {
		setEnabled(getModel().getNeedsSave()); // && _model.isUntitled());
	}
	
	private TextFileDocument getModel() {
		return _fileChooser.getModel();
	}
}
