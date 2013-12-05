package org.treytomes.notepad;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.JFrame;

public class NotepadView extends JFrame {
	
	private static final String WINDOW_TITLE = "Notepad";
	private static final int WINDOW_WIDTH = 450;
	private static final int WINDOW_HEIGHT = 300;

	private static final String WINDOW_ICON_16 = "/org/treytomes/notepad/notepad_16.png";
	private static final String WINDOW_ICON_24 = "/org/treytomes/notepad/notepad_24.png";
	private static final String WINDOW_ICON_32 = "/org/treytomes/notepad/notepad_32.png";
	private static final String WINDOW_ICON_48 = "/org/treytomes/notepad/notepad_48.png";
	private static final String WINDOW_ICON_256 = "/org/treytomes/notepad/notepad_256.png";

	/**
	 * 
	 */
	private static final long serialVersionUID = 2512504132006442564L;
	
	private static final WindowListener closeWindow = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent evt) {
			evt.getWindow().setVisible(false);
			evt.getWindow().dispose();
		};
	};

	public NotepadView() {
		setTitle(WINDOW_TITLE);
		setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(closeWindow);
		
		setIconImages(Arrays.asList(new Image[] {
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_16)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_24)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_32)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_48)),
			Toolkit.getDefaultToolkit().getImage(Notepad.class.getResource(WINDOW_ICON_256)),
		}));
	}
}
