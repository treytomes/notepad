package org.treytomes.util;

public interface IPropertyRead {
	
	/**
	 * Get the names of all the defined properties.
	 * 
	 * @return
	 */
	String[] getPropertyNames();
	
	boolean isPropertyDefined(String propertyName);
	
	/**
	 * Get a property of this class.
	 * Properties are defined by using the @Property annotation on a method.
	 * 
	 * @param propertyName
	 * @return
	 * @throws PropertyNotDefinedException
	 */
	Object getProperty(String propertyName) throws PropertyNotDefinedException;
}
