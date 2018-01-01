package myTodoProject.infra.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtils {
	
public static final String PROPERTIES_ARRAY_DELIMITER=";";
	
	public static String getNow()
	{
		return getNow("yyyy-MM-dd");
	}

	public static String getNow(String format)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateFormat = sdf.format(cal.getTime());
		return dateFormat;
	}
	public static void loadParametersFromPropertiesFile(Class<?> cls, String propertiesFileName)
	{

		OrderedProperties prop = new OrderedProperties();
		InputStream input = null;

		try
		{
			input = new FileInputStream(propertiesFileName);

			// load a properties file
			prop.load(input);

			Field[] allFields = cls.getDeclaredFields();
			for (Field field : allFields)
			{
				// Because all members are private - need to change the accessibility of them in order to access them
				field.setAccessible(true);

				// Check if type of field is array
				if (field.getType().isArray())
				{
					// Check if type of array element is int
					if (field.getType().getComponentType() == int.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							int[] arr = convertStringArrayToIntArray(values);

							field.set(null, arr);
						}
					}

					// Check if type of array element is String
					if (field.getType().getComponentType() == String.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));

							field.set(null, values);
						}
					}

					// Check if type of array element is char
					if (field.getType().getComponentType() == char.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							char[] arr = convertStringArrayToCharArray(values);

							field.set(null, arr);
						}
					}

					// Check if type of array element is boolean
					if (field.getType().getComponentType() == boolean.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							boolean[] arr = convertStringArrayToBooleanArray(values);

							field.set(null, arr);
						}
					}

					// Check if type of array element is long
					if (field.getType().getComponentType() == long.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							long[] arr = convertStringArrayToLongArray(values);

							field.set(null, arr);
						}
					}

					// Check if type of array element is float
					if (field.getType().getComponentType() == float.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							float[] arr = convertStringArrayToFloatArray(values);

							field.set(null, arr);
						}
					}

					// Check if type of array element is double
					if (field.getType().getComponentType() == double.class)
					{
						if (prop.getProperty(field.getName()) != null)
						{
							String[] values = getValuesAsStringArray(prop.getProperty(field.getName()));
							double[] arr = convertStringArrayToDoubleArray(values);

							field.set(null, arr);
						}
					}

				}

				if (field.getType().isAssignableFrom(String.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, prop.getProperty(field.getName()));
					}
				}

				if (field.getType().isAssignableFrom(char.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, prop.getProperty(field.getName()).charAt(0));
					}
				}

				if (field.getType().isAssignableFrom(int.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, Integer.valueOf(prop.getProperty(field.getName())));
					}
				}

				if (field.getType().isAssignableFrom(boolean.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, Boolean.valueOf(prop.getProperty(field.getName())));
					}
				}

				if (field.getType().isAssignableFrom(long.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, Long.valueOf(prop.getProperty(field.getName())));
					}
				}

				if (field.getType().isAssignableFrom(float.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, Float.valueOf(prop.getProperty(field.getName())));
					}
				}

				if (field.getType().isAssignableFrom(double.class))
				{
					if (prop.getProperty(field.getName()) != null)
					{
						field.set(null, Double.valueOf(prop.getProperty(field.getName())));
					}
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}
	/**
	 * This method converts an array property in String array
	 */
	private static String[] getValuesAsStringArray(String property)
	{
		return property.split(PROPERTIES_ARRAY_DELIMITER);

	}
	private static int[] convertStringArrayToIntArray(String[] values)
	{
		int[] res = new int[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = Integer.parseInt(values[i]);
		}
		return res;
	}
	private static boolean[] convertStringArrayToBooleanArray(String[] values)
	{
		boolean[] res = new boolean[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = Boolean.parseBoolean(values[i]);
		}
		return res;
	}
	private static long[] convertStringArrayToLongArray(String[] values)
	{
		long[] res = new long[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = Long.parseLong(values[i]);
		}
		return res;
	}
	private static char[] convertStringArrayToCharArray(String[] values)
	{
		char[] res = new char[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = values[i].charAt(0);
		}
		return res;
	}
	private static float[] convertStringArrayToFloatArray(String[] values)
	{
		float[] res = new float[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = Float.parseFloat(values[i]);
		}
		return res;
	}
	private static double[] convertStringArrayToDoubleArray(String[] values)
	{
		double[] res = new double[values.length];

		for (int i = 0; i < res.length; i++)
		{
			res[i] = Double.parseDouble(values[i]);
		}
		return res;
	}

}

