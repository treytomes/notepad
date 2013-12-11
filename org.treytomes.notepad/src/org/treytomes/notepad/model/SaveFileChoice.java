package org.treytomes.notepad.model;

import javax.swing.JButton;

public enum SaveFileChoice {
	Save("Save", 'S', true),
	DontSave("Don't Save", 'n'),
	Cancel("Cancel", 'C');
	
	private String _displayName;
	private char _mnemonic;
	private boolean _isDefaultChoice;
	
	public String displayName() {
		return _displayName;
	}
	
	public char mnemonic() {
		return _mnemonic;
	}
	
	public boolean isDefaultChoice() {
		return _isDefaultChoice;
	}
	
	public JButton createButton() {
		JButton button = new JButton(displayName());
		button.setActionCommand(name());
		button.setMnemonic(mnemonic());
		return button;
	}
	
	SaveFileChoice(String displayName, char mnemonic, boolean isDefaultChoice) {
		_displayName = displayName;
		_mnemonic = mnemonic;
		_isDefaultChoice = isDefaultChoice;
	}
	
	SaveFileChoice(String displayName, char mnemonic) {
		this(displayName, mnemonic, false);
	}
}
