import java.lang.reflect.Field;

final public class Serv
{
    public static Base set(Base b, Field[] fld, String val) throws Exception
    {
	// Base;1;2;   in
	// D1;3;4.0;|Base;1;2;
	// System.out.println(val);
	if (val.indexOf("|") != -1)
	{
	    // System.out.println(val.substring(val.indexOf("|")+1));
	    b.sett(val.substring(val.indexOf("|") + 1));

	    val = val.substring(0, val.indexOf("|"));
	}
	val = val.substring(val.indexOf(";") + 1, val.length());

	String[] values = val.split(";");

	for (int i = 0; i < fld.length; i++)
	{
	    fld[i].setAccessible(true);

	    switch (fld[i].getType().getName())
		{
		    case "int":
			// System.out.println(i);
			Integer.parseInt(values[i]);

			fld[i].setInt(b, Integer.parseInt(values[i]));

			break;

		    default:
			fld[i].setFloat(b, Float.valueOf(values[i].trim())
				.floatValue());

			break;
		}

	    fld[i].setAccessible(false);
	}

	return b;
    }

    public static String ToString(Base base) throws Exception
    {
	String str = "";

	Class<?> cl = base.getClass();

	str += cl.getSimpleName() + ";";

	for (Field field : cl.getDeclaredFields())
	{
	    field.setAccessible(true);

	    switch (field.getType().getName())
		{
		    case "int":
			str += field.getInt(base) + ";";
			break;

		    default:
			str += field.getFloat(base) + ";";
			break;
		}

	    field.setAccessible(false);

	}
	if (base.getClass().getSuperclass().getDeclaredFields().length != 0)
	{
	    str += "|" + base.getClass().getSuperclass().getName() + ";";

	    for (Field field : base.getClass().getSuperclass()
		    .getDeclaredFields())
	    {
		field.setAccessible(true);

		switch (field.getType().getName())
		    {
			case "int":
			    str += field.getInt(base) + ";";
			    break;

			default:
			    str += field.getFloat(base) + ";";
			    break;
		    }

		field.setAccessible(false);

	    }

	}
	return str;

    }

    public static Base fromString(String str) throws Exception
    {
	int j = 2;

	String s[] = str.split(";");

	Class<?> cl = Class.forName(s[0]);
	// System.out.println(s[0]);
	Base b = (Base) cl.newInstance();

	Field[] f = cl.getDeclaredFields();

	String ss = null;
	for (int i = 3; i < f.length; i += 2)
	{
	    ss += s[i] + ";";
	}

	Serv.set(b, f, ss);

	return b;

    }
}
