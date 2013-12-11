package org.treytomes.notepad.model;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.treytomes.notepad.AboutNotepadDialog;

public class HelpAboutNotepad extends AbstractAction {

	private static final long serialVersionUID = 275695636186200351L;
	
	private Frame _parent;

	public HelpAboutNotepad(Frame parent) {
		super("About Notepad");
		
		_parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		new AboutNotepadDialog(_parent).setVisible(true);
	}
}