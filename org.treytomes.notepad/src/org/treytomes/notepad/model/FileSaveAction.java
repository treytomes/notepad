package org.treytomes.notepad.model;

import java.awt.event.ActionEvent;

import org.treytomes.notepad.FileChooserView;

public class FileSaveAction extends FileSaveAsAction {

	private static final long serialVersionUID = -7475355022811463514L;

	public FileSaveAction(FileChooserView fileChooser) {
		super(fileChooser);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFile();
	}

	@Override
	public void update() {
		setEnabled(getModel().getNeedsSave() && !getModel().isUntitled());
	}
	
	public void saveFile() {
		if (getModel().isUntitled()) {
			saveFileAs();
		} else {
			getModel().save();
		}
	}
}