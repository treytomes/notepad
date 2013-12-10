package org.treytomes.notepad;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class WindowCloseAction extends AbstractAction implements WindowListener {
	
	private static final long serialVersionUID = -8933472110337262946L;
	private static final Logger LOGGER = Logger.getLogger(WindowCloseAction.class.getName());
	private static final KeyStroke KEY_ESCAPE = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
	private static final String ACTION_CLOSEWINDOW = "closeWindow";
	
	private Window _window;
	private String _actionNameCloseWindow;
	private boolean _closeOnEscape;
	
	public WindowCloseAction(Window window) {
		_window = window;
		_actionNameCloseWindow = String.format("%s.%s", _window.getClass().getName(), ACTION_CLOSEWINDOW);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_window.dispatchEvent(new WindowEvent(_window, WindowEvent.WINDOW_CLOSING));
	}
	
	public boolean getCloseOnEscape() {
		return _closeOnEscape;
	}
	
	public void setCloseOnEscape(boolean closeOnEscape) {
		_closeOnEscape = closeOnEscape;
		if (_closeOnEscape) {
			assignActionMap();
		} else {
			removeActionMap();
		}
	}

	
	@Override
	public void windowClosing(WindowEvent e) {		
		if (getCloseOnEscape()) {
			removeActionMap();
		}
		
		String windowName = _window.getClass().getSimpleName();
		
		LOGGER.log(Level.INFO, "Closing the {0} window...", windowName);
		_window.setVisible(false);
		_window.dispose();

		LOGGER.log(Level.INFO, "The {0} window is now closed.", windowName);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}
	

	@Override
	public void windowIconified(WindowEvent e) {
	}
	

	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	

	@Override
	public void windowActivated(WindowEvent e) {
	}
	

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	private void assignActionMap() {
		LOGGER.info("Assigning action mappings...");
		JRootPane root = SwingUtilities.getRootPane(_window);
		root.getActionMap().put(_actionNameCloseWindow, new WindowCloseAction(_window));
		root.getInputMap().put(KEY_ESCAPE, _actionNameCloseWindow);
		LOGGER.info("Action mappings assigned.");
	}
	
	private void removeActionMap() {
		LOGGER.info("Removing action mappings...");
		JRootPane root = SwingUtilities.getRootPane(_window);
		root.getInputMap().remove(KEY_ESCAPE);
		root.getActionMap().remove(_actionNameCloseWindow);
		LOGGER.info("Action mappings assigned.");
	}
}