package myTodoProject.infra.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class OrderedProperties implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private transient Map<String, String> properties;
	private transient boolean suppressDate;

	/**
	 * Creates a new instance that will keep the properties in the order they
	 * have been added. Other than the ordering of the keys, this instance
	 * behaves like an instance of the {@link Properties} class.
	 */
	public OrderedProperties()
	{
		this(new LinkedHashMap<String, String>(), false);
	}

	private OrderedProperties(Map<String, String> properties, boolean suppressDate)
	{
		this.properties = properties;
		this.suppressDate = suppressDate;
	}

	/**
	 * See {@link Properties#getProperty(String)}.
	 */
	public String getProperty(String key)
	{
		String value = properties.get(key);
		return (value == null) ? null : value.trim();
	}

	/**
	 * See {@link Properties#getProperty(String, String)}.
	 */
	public String getProperty(String key, String defaultValue)
	{
		String value = getProperty(key);
		return (value == null) ? defaultValue : value.trim();
	}

	/**
	 * See {@link Properties#setProperty(String, String)}.
	 */
	public String setProperty(String key, String value)
	{
		return properties.put(key, value);
	}

	/**
	 * Removes the property with the specified key, if it is present. Returns
	 * the value of the property, or <tt>null</tt> if there was no property with
	 * the specified key.
	 *
	 * @param key
	 *            the key of the property to remove
	 * @return the previous value of the property, or <tt>null</tt> if there was
	 *         no property with the specified key
	 */
	public String removeProperty(String key)
	{
		return properties.remove(key);
	}

	/**
	 * Returns <tt>true</tt> if there is a property with the specified key.
	 *
	 * @param key
	 *            the key whose presence is to be tested
	 */
	public boolean containsProperty(String key)
	{
		return properties.containsKey(key);
	}

	/**
	 * See {@link Properties#load(InputStream)}.
	 */
	public void load(InputStream stream) throws IOException
	{
		CustomProperties customProperties = new CustomProperties(this.properties);
		customProperties.load(stream);
	}

	/**
	 * See {@link Properties#size()}.
	 */
	public int size()
	{
		return properties.size();
	}

	/**
	 * See {@link Properties#isEmpty()}.
	 */
	public boolean isEmpty()
	{
		return properties.isEmpty();
	}

	/**
	 * See {@link Properties#propertyNames()}.
	 */
	public Enumeration<String> propertyNames()
	{
		return new Vector<String>(properties.keySet()).elements();
	}

	/**
	 * See {@link Properties#stringPropertyNames()}.
	 */
	public Set<String> stringPropertyNames()
	{
		return new LinkedHashSet<String>(properties.keySet());
	}


	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if ((other == null) || (getClass() != other.getClass()))
		{
			return false;
		}

		OrderedProperties that = (OrderedProperties) other;
		return Arrays.equals(properties.entrySet().toArray(), that.properties.entrySet().toArray());
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(properties.entrySet().toArray());
	}

	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		stream.defaultWriteObject();
		stream.writeObject(properties);
		stream.writeBoolean(suppressDate);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		properties = (Map<String, String>) stream.readObject();
		suppressDate = stream.readBoolean();
	}

	/**
	 * See {@link Properties#toString()}.
	 */
	@Override
	public String toString()
	{
		return properties.toString();
	}

	private static final class CustomProperties extends Properties
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1839296558928810201L;
		private final Map<String, String> targetProperties;

		private CustomProperties(Map<String, String> targetProperties)
		{
			this.targetProperties = targetProperties;
		}

		@Override
		public Object get(Object key)
		{
			return targetProperties.get(key);
		}

		@Override
		public Object put(Object key, Object value)
		{
			return targetProperties.put((String) key, (String) value);
		}

		@Override
		public String getProperty(String key)
		{
			return targetProperties.get(key);
		}

		@Override
		public Enumeration<Object> keys()
		{
			return new Vector<Object>(targetProperties.keySet()).elements();
		}

	}

}

