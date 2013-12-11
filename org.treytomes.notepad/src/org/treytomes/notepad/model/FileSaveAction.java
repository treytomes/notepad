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
		if (getModel().isUntitled()) {
			super.actionPerformed(e);
		} else {
			getModel().save();
		}

	}

	@Override
	public void update() {
		setEnabled(getModel().getNeedsSave() && !getModel().isUntitled());
	}
}