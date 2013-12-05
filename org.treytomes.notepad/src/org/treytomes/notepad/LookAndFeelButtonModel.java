package org.treytomes.notepad;

import javax.swing.DefaultButtonModel;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelButtonModel extends DefaultButtonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1206429305419429393L;
	
	private JFrame _rootPaneSource;
	private LookAndFeelInfo _info;
	
	public LookAndFeelButtonModel(JFrame rootPaneSource, LookAndFeelInfo info) {
		_rootPaneSource = rootPaneSource;
		_info = info;
	}

	public JRootPane getRootPane() {
		return _rootPaneSource.getRootPane();
	}
	
	public String getName() {
		return _info.getName();
	}
	
	public String getClassName() {
		return _info.getClassName();
	}
	
	public void updateUI() {
		LookAndFeelManager.setLookAndFeelByClassName(getClassName());
		LookAndFeelManager.updateUI(getRootPane());
	}
	
	public boolean isSelected() {
		return LookAndFeelManager.isActive(getName());
	}
}
