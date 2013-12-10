package org.treytomes.notepad;

import java.awt.EventQueue;
import java.util.logging.Logger;

public class Notepad {
	
	private static final Logger LOGGER = Logger.getLogger(Notepad.class.getName());
	
	private TextFileModel _fileModel;
	private NotepadView _notepadView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LOGGER.info("Starting application.");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notepad window = new Notepad();
					window._notepadView.setVisible(true);
					LOGGER.info("The application window should now be visible.");
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
		LOGGER.info("Setting the look-and-feel.");
		LookAndFeelManager.setDefaultLookAndFeel();
		
		LOGGER.info("Creating the file model.");
		_fileModel = new TextFileModel();
		
		LOGGER.info("Creating the notepad view.");
		_notepadView = new NotepadView(_fileModel);
	}
}
