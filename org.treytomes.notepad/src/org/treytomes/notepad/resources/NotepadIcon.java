package org.treytomes.notepad.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

public class NotepadIcon {
	
	private static final String PATH_RESOURCES = "/org/treytomes/notepad/resources/";
	private static final String WINDOW_ICON_16 = "notepad_16.png";
	private static final String WINDOW_ICON_24 = "notepad_24.png";
	private static final String WINDOW_ICON_32 = "notepad_32.png";
	private static final String WINDOW_ICON_48 = "notepad_48.png";
	private static final String WINDOW_ICON_256 = "notepad_256.png";
	
	public static List<Image> getAll() {
		return Arrays.asList(new Image[] {
			getImage(ImageSize.Size16),
			getImage(ImageSize.Size24),
			getImage(ImageSize.Size32),
			getImage(ImageSize.Size48),
			getImage(ImageSize.Size256)
		});
	}
	
	public static ImageIcon getIcon(ImageSize size) {
		return new ImageIcon(getImage(size));
	}
	
	public static Image getImage(ImageSize size) {
		URL resource = getPathForSize(size);
		if (resource == null) {
			return null;
		} else {
			return Toolkit.getDefaultToolkit().getImage(resource);
		}
	}
	
	private static URL getPathForSize(ImageSize size) {
		String filename = getFilenameForSize(size);
		if ((filename == null) || (filename.length() == 0)) {
			return null;
		} else {
			return NotepadIcon.class.getResource(new File(PATH_RESOURCES, filename).getName());
		}
	}
	
	private static String getFilenameForSize(ImageSize size) {
		switch (size) {
		case Size16:
			return WINDOW_ICON_16;
		case Size24:
			return WINDOW_ICON_24;
		case Size32:
			return WINDOW_ICON_32;
		case Size48:
			return WINDOW_ICON_48;
		case Size256:
			return WINDOW_ICON_256;
		default:
			return null;
		}
	}
}
