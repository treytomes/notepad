package org.treytomes.notepad;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class FileOpenAction extends AbstractAction {
	
	private static final long serialVersionUID = -1950461827719914874L;
	
	private FileChooserView _fileChooser;
	
	public FileOpenAction(FileChooserView fileChooser) {
		_fileChooser = fileChooser;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		_fileChooser.openFile();
	}
}
