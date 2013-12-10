package org.treytomes.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertyAccessComponent implements IPropertyAccess {
	
	private Map<String, Method> _properties;
	private IPropertyAccess _parent;

	public PropertyAccessComponent(IPropertyAccess parent) {
		_parent = parent;
		locateProperties();
	}
	
	/**
	 * Get the names of all the defined properties.
	 * 
	 * @return
	 */
	@Override
	public String[] getPropertyNames() {
		return _properties.keySet().toArray(new String[0]);
	}
	
	@Override
	public boolean isPropertyDefined(String propertyName) {
		return _properties.containsKey(propertyName);
	}
	
	/**
	 * Get a property of this class.
	 * Properties are defined by using the @Property annotation on a method.
	 * 
	 * @param propertyName
	 * @return
	 * @throws PropertyNotDefinedException
	 */
	@Override
	public Object getProperty(String propertyName) throws PropertyNotDefinedException {
		if (isPropertyDefined(propertyName)) {
			Method method = _properties.get(propertyName);
			try {
				return invokePropertyMethod(method);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new PropertyNotDefinedException(propertyName, e);
			}
		} else {
			throw new PropertyNotDefinedException(propertyName);
		}
	}
	
	/**
	 * Locate all methods defined with the Property annotation.
	 * These properties will later be used to run the {@link #getProperty(String) getProperty} method.
	 */
	private void locateProperties() {
		_properties = new HashMap<String, Method>();
		
		for (Method method : _parent.getClass().getMethods()) {
			Property property = method.getAnnotation(Property.class);
			if (property == null) {
				continue;
			} else {
				_properties.put(property.value(), method);
			}
		}
	}

	private Object invokePropertyMethod(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(_parent);
	}
}
