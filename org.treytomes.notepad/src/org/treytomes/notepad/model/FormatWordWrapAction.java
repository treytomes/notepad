package org.treytomes.notepad.model;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;

public class FormatWordWrapAction extends AbstractAction {

	private static final long serialVersionUID = 836326716787711335L;
	
	private static final Logger LOGGER = Logger.getLogger(FormatWordWrapAction.class.getName());
	
	private JTextArea _textArea;
	
	public FormatWordWrapAction(JTextArea textArea) {
		super("Word Wrap");
		
		_textArea = textArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)evt.getSource();
			_textArea.setLineWrap(menuItem.getState());
			LOGGER.log(Level.INFO, "Setting word wrap: {0}", _textArea.getLineWrap());
		}
	}
}