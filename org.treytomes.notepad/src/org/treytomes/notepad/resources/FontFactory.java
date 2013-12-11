package org.treytomes.notepad.resources;

import java.awt.Font;

public class FontFactory {
	
	public static Font getDocumentTextFont() {
		return new Font("Lucida Console", Font.PLAIN, 14);
	}
	
	public static Font getAboutTitleFont() {
		return new Font("Tahoma", Font.PLAIN, 36);
	}
	
	public static Font getMessageTextFont() {
		return new Font("Tahoma", Font.PLAIN, 16);
	}
}
