package org.treytomes.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.treytomes.util.PropertyNotDefinedException;

public class PropertyLogFormatter extends Formatter {
	
	private String _format;
	
	public PropertyLogFormatter(String format) {
		_format = format;
	}
	
	@Override
	public String format(LogRecord record) {
		PropertyReadLogRecord propRecord = new PropertyReadLogRecord(record);
		
		String outputMessage = _format;
		
		for (String propertyName : propRecord.getPropertyNames()) {
			String qualifiedPropertyName = String.format("{%s}", propertyName);
			if (outputMessage.contains(qualifiedPropertyName)) {
				String propertyValue;
				try {
					propertyValue = propRecord.getProperty(propertyName).toString();
					propertyValue = replaceRegexCharacters(propertyValue);
					outputMessage = outputMessage.replaceAll(String.format("\\{%s\\}", propertyName), propertyValue);
				} catch (PropertyNotDefinedException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		}
		
		return outputMessage;
	}
	
	private String replaceRegexCharacters(String text) {
		// Anonymous classes will have a '$' in their name.
		// Because the '$' is a special character in regular expressions,
		// we must replace it with an escaped-'$'.
		text = replaceRegexCharacter(text, '$');
		return text;
	}
	
	private String replaceRegexCharacter(String text, char ch) {
		if (text.contains(Character.toString(ch))) {
			text = text.replaceAll(String.format("\\%c", ch), String.format("\\\\\\%c", ch));
		}
		return text;
	}
}
