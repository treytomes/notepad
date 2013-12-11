package org.treytomes.notepad.resources;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.treytomes.util.FileIO;
import org.w3c.dom.Document;

/**
 * TODO: Move the project into JavaFX, then use a WebView to load the XML/XSLT version of the About Notepad message.
 * See here: http://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm
 * And here: http://docs.oracle.com/javafx/2/swing/swing-fx-interoperability.htm#CHDIEEJE
 * 
 * @author ttomes
 *
 */
public class TextResources {
	
	private static final Logger LOGGER = Logger.getLogger(TextResources.class.getName());
	
	private static final String PATH_RESOURCES = "/org/treytomes/notepad/resources/";
	private static final String HTML_ABOUTNOTEPAD = "aboutNotepad.html";
	private static final String XML_ABOUTNOTEPAD = "aboutNotepad.xml";
	private static final String XSL_ABOUTNOTEPAD = "about.xsl";
	
	public static String getAboutNotepadHtml() {
		return "<html>Trey Tomes<br />Version 1.0<br />Copyright &copy; 2013 Trey Tomes.  All rights reserved.</html>"; // loadText(HTML_ABOUTNOTEPAD);
	}
	
	public static String getAboutNotepadXml() {
		URL xsl = getResourceUrl(XSL_ABOUTNOTEPAD);
		URL xml = getResourceUrl(XML_ABOUTNOTEPAD);
		
		try {
			// Load the data.
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document document = builder.parse(xml.openStream());
			
			// Load the stylesheet.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			StreamSource styleSource = new StreamSource(xsl.openStream());
			Transformer transformer = transformerFactory.newTransformer(styleSource);
			
			DOMSource source = new DOMSource(document);
			StringWriter resultWriter = new StringWriter();
			StreamResult result = new StreamResult(resultWriter);
			transformer.transform(source, result);
			StringBuffer buffer = resultWriter.getBuffer();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Unable to parse the xml.");
			return loadText(XML_ABOUTNOTEPAD);
		}
	}
	
	private static String loadText(String filename) {
		LOGGER.log(Level.INFO, "Loading the {0} resource.", filename);
		URL resourceUrl = getResourceUrl(filename);
		return FileIO.readTextFile(new File(resourceUrl.getPath()));
	}
	
	private static URL getResourceUrl(String filename) {
		return TextResources.class.getResource(new File(PATH_RESOURCES, filename).getName());
	}
}