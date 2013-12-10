package org.treytomes.notepad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonModel;
import javax.swing.JRadioButtonMenuItem;

import org.treytomes.notepad.model.LookAndFeelButtonModel;

public class LookAndFeelMenuItem extends JRadioButtonMenuItem implements ActionListener {

	private static final long serialVersionUID = -2821679071825616158L;
	
	private LookAndFeelButtonModel _lookAndFeelModel;
	
	public LookAndFeelMenuItem() {
		addActionListener(this);
		_lookAndFeelModel = null;
	}
	
	@Override
	public boolean isSelected() {
		if (_lookAndFeelModel != null) {
			return _lookAndFeelModel.isSelected();
		} else {
			return super.isSelected();
		}
	}
	
	@Override
	public void setSelected(boolean b) {
		if (_lookAndFeelModel != null) {
			System.err.println("Button selected status cannot be assigned.");
		} else {
			super.setSelected(b);
		}
	}
	
	@Override
	public void setModel(ButtonModel newModel) {
		super.setModel(newModel);
		
		if (newModel instanceof LookAndFeelButtonModel) {
			_lookAndFeelModel = (LookAndFeelButtonModel)model;
			super.setText(_lookAndFeelModel.getName());
		} else {
			_lookAndFeelModel = null;
		}
	}
	
	@Override
	public void setText(String text) {
		if (_lookAndFeelModel != null) {
			System.err.println("Button text cannot be assigned.");
		} else {
			super.setText(text);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (_lookAndFeelModel != null) {
			_lookAndFeelModel.updateUI();
		}
	}
}
