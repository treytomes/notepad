package org.treytomes.notepad.resources;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.treytomes.util.FileIO;

public class TextResources {
	
	private static final Logger LOGGER = Logger.getLogger(TextResources.class.getName());
	
	private static final String PATH_RESOURCES = "/org/treytomes/notepad/resources/";
	private static final String HTML_ABOUTNOTEPAD = "aboutNotepad.html";
	
	public static String getAboutNotepadHtml() {
		LOGGER.log(Level.INFO, "Loading About Notepad html file.");
		URL resourceUrl = TextResources.class.getResource(new File(PATH_RESOURCES, HTML_ABOUTNOTEPAD).getName());
		return FileIO.readTextFile(new File(resourceUrl.getPath()));
	}
}