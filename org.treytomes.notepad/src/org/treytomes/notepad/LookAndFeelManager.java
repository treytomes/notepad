package org.treytomes.notepad;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelManager {
	
	private static final String DEFAULT_LOOKANDFEEL = "Nimbus";
	
	public static LookAndFeelInfo[] getInstalledLookAndFeels() {
		return UIManager.getInstalledLookAndFeels();
	}
	
	public static void setDefaultLookAndFeel() {
		setLookAndFeelByDisplayName(DEFAULT_LOOKANDFEEL);
	}
	
	public static void setLookAndFeelByDisplayName(String name) {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if (name.equals(info.getName())) {
	        	setLookAndFeelByClassName(info.getClassName());
	            break;
	        }
	    }
	}
	
	public static void setLookAndFeelByClassName(String className) {
		try {
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {
		    // If the look-and-feel is not available, you can set the GUI to another look-and-feel.
		}
	}
	
	public static boolean isActive(String name) {
		return UIManager.getLookAndFeel().getName().equals(name);
	}
	
	/**
	 * Update the existing UI components with the new look-and-feel.
	 * 
	 * @param root
	 */
	public static void updateUI(JRootPane root) {
		SwingUtilities.updateComponentTreeUI(root);
	}
}
