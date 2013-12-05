package org.treytomes.notepad;

import java.awt.EventQueue;

public class Notepad {
	
	private TextFileModel _fileModel;
	private NotepadView _notepadView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notepad window = new Notepad();
					window._notepadView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Notepad() {
		LookAndFeelManager.setDefaultLookAndFeel();
		
		_fileModel = new TextFileModel();
		_notepadView = new NotepadView();
		_notepadView.setModel(_fileModel);
	}
}
