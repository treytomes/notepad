package org.treytomes.logging;

public class TextLogFormatter extends PropertyLogFormatter {

	private static final String FORMAT = "{time} [{level}] {class}.{method} : {message}\n";

	public TextLogFormatter() {
		super(FORMAT);
	}
}