package org.treytomes.util;

public class PropertyNotDefinedException extends Exception {
	
	private static final long serialVersionUID = -4578159970190132172L;

	private static final String MESSAGE = "Property is not defined: %s";

	private String _propertyName;
	
	public PropertyNotDefinedException(String propertyName) {
		super(String.format(MESSAGE, propertyName));
		_propertyName = propertyName;
	}
	
	public PropertyNotDefinedException(String propertyName, Throwable cause) {
		super(String.format(MESSAGE, propertyName), cause);
		_propertyName = propertyName;
	}
	
	public String getPropertyName() {
		return _propertyName;
	}
}
