package org.treytomes.notepad;

import javax.swing.DefaultButtonModel;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
		try {
			// Set the new look-and-feel.
			UIManager.setLookAndFeel(getClassName());
			
			// Update the existing UI components with the new look-and-feel.
			SwingUtilities.updateComponentTreeUI(getRootPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSelected() {
		return UIManager.getLookAndFeel().getName().equals(getName());
	}
}
