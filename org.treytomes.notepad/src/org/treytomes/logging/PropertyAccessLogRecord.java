package org.treytomes.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.treytomes.util.DateTime;
import org.treytomes.util.IPropertyAccess;
import org.treytomes.util.Property;
import org.treytomes.util.PropertyAccessComponent;
import org.treytomes.util.PropertyNotDefinedException;

/**
 * Access the properties of the LogRecord with simple text names.
 * Works by creating a proxy of the LogRecord class,
 * and adding the PropertyAccessComponent.
 * 
 * @author trey
 *
 */
public class PropertyAccessLogRecord extends LogRecord implements IPropertyAccess {

	private static final long serialVersionUID = 4165009369171268534L;
	
	private LogRecord _baseRecord;
	private IPropertyAccess _propertyAccess;
	
	public PropertyAccessLogRecord(LogRecord baseRecord) {
		super(baseRecord.getLevel(), baseRecord.getMessage());
		
		_baseRecord = baseRecord;
		
		_propertyAccess = new PropertyAccessComponent(this);
	}
	
	public LogRecord getBaseRecord() {
		return _baseRecord;
	}
	
	/**
	 * Get the names of all the defined properties.
	 * 
	 * @return
	 */
	@Override
	public String[] getPropertyNames() {
		return _propertyAccess.getPropertyNames();
	}
	
	@Override
	public boolean isPropertyDefined(String propertyName) {
		return _propertyAccess.isPropertyDefined(propertyName);
	}
	
	/**
	 * Get a property of this class.
	 * Properties are defined by using the @Property annotation on a method.
	 * 
	 * @param propertyName
	 * @return
	 * @throws PropertyNotDefinedException
	 */
	public Object getProperty(String propertyName) throws PropertyNotDefinedException {
		return _propertyAccess.getProperty(propertyName);
	}
	
	@Override
	public int getThreadID() {
		return _baseRecord.getThreadID();
	}
	
	@Override
	public void setThreadID(int threadID) {
		_baseRecord.setThreadID(threadID);
	}
	
	@Override
	public Throwable getThrown() {
		return _baseRecord.getThrown();
	}
	
	@Override
	public void setThrown(Throwable thrown) {
		_baseRecord.setThrown(thrown);
	}
	
	@Property("seq")
	public String getSequenceNumberText() {
		return Long.toString(getSequenceNumber());
	}
	
	@Override
	public long getSequenceNumber() {
		return _baseRecord.getSequenceNumber();
	}
	
	@Override
	public void setSequenceNumber(long seq) {
		_baseRecord.setSequenceNumber(seq);
	}
	
	@Override
	public ResourceBundle getResourceBundle() {
		return _baseRecord.getResourceBundle();
	}
	
	@Override
	public void setResourceBundle(ResourceBundle bundle) {
		_baseRecord.setResourceBundle(bundle);
	}
	
	@Override
	public String getResourceBundleName() {
		return _baseRecord.getResourceBundleName();
	}
	
	@Override
	public void setResourceBundleName(String name) {
		_baseRecord.setResourceBundleName(name);
	}
	
	@Property("logger")
	@Override
	public String getLoggerName() {
		return _baseRecord.getLoggerName();
	}
	
	@Override
	public void setLoggerName(String name) {
		_baseRecord.setLoggerName(name);
	}
	
	@Property("time")
	public String getDateTimeStamp() {
		return DateTime.getDateTimeStamp(getMillis());
	}
	
	@Override
	public long getMillis() {
		return _baseRecord.getMillis();
	}
	
	@Override
	public void setMillis(long millis) {
		_baseRecord.setMillis(millis);
	}
	
	@Property("level")
	public String getLevelText() {
		return getLevel().getLocalizedName();
	}
	
	@Override
	public Level getLevel() {
		return _baseRecord.getLevel();
	}
	
	@Override
	public void setLevel(Level level) {
		_baseRecord.setLevel(level);
	}
	
	@Property("class")
	@Override
	public String getSourceClassName() {
		return _baseRecord.getSourceClassName();
	}
	
	public void setSourceClassName(String sourceClassName) {
		_baseRecord.setSourceClassName(sourceClassName);
	}
	
	@Property("method")
	@Override
	public String getSourceMethodName() {
		return _baseRecord.getSourceMethodName();
	}
	
	@Override
	public void setSourceMethodName(String sourceMethodName) {
		_baseRecord.setSourceMethodName(sourceMethodName);
	}
	
	@Property("message")
	public String getFormattedMessage() {
		return formatMessage(this);
	}
	
	@Override
	public String getMessage() {
		return _baseRecord.getMessage();
	}
	
	@Override
	public void setMessage(String message) {
		_baseRecord.setMessage(message);
	}
	
	@Override
	public Object[] getParameters() {
		return _baseRecord.getParameters();
	}
	
	@Override
	public void setParameters(Object[] parameters) {
		_baseRecord.setParameters(parameters);
	}
	
	private static String formatMessage(LogRecord record) {
		return formatMessage(record.getMessage(), record.getParameters());
	}
	
	private static String formatMessage(String message, Object[] parameters) {
		return MessageFormat.format(message, parameters);
	}
}
