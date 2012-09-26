import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeDB extends Types
{
    Connection con = null;
    java.sql.Statement stmt = null;
    ResultSet rs = null;
    ArrayList<Integer> index;
    int counter=0;
    
    void doCreateRequest(Base b, PreparedStatement ps) throws Exception
    {
	String res = "";

	Field[] fldCLILD = b.getClass().getDeclaredFields();

	res += "create table IF NOT EXISTS " + b.getClass().getSimpleName()
		+ "(id int not null AUTO_INCREMENT," + "NIL int not null,";

	for (int i = 0; i < fldCLILD.length; i++)
	{
	    fldCLILD[i].setAccessible(true);
	    res += fldCLILD[i].getName() + " "
		    + fldCLILD[i].getType().getName() + ",";
	    fldCLILD[i].setAccessible(false);
	}

	if (b.getClass().getSuperclass().getDeclaredFields().length != 0)
	{
	    Field[] fldPARENT = b.getClass().getSuperclass()
		    .getDeclaredFields();

	    for (int i = 0; i < fldPARENT.length; i++)
	    {
		fldPARENT[i].setAccessible(true);

		res += b.getClass().getSuperclass().getSimpleName() + "_"
			+ fldPARENT[i].getName() + " "
			+ fldPARENT[i].getType().getName() + ",";

		fldPARENT[i].setAccessible(false);
	    }
	}

	res += "primary key (id));";
	ps = con.prepareStatement(res);
	ps.executeUpdate();

    }

    void doInsertRequest(Base b, PreparedStatement ps, int numbInList)
	    throws IllegalArgumentException, IllegalAccessException,
	    SQLException
    {
	String res = "";
	Field[] fldCLILD = b.getClass().getDeclaredFields();
	Field[] fldPARENT = {};

	if (b.getClass().getSuperclass().getDeclaredFields().length > 0)
	{
	    fldPARENT = b.getClass().getSuperclass().getDeclaredFields();
	}
	if (fldCLILD.length > 0)
	{
	    res += "INSERT INTO " + b.getClass().getSimpleName() + "(NIL,";

	    for (int i = 0; i < fldCLILD.length; i++)
	    {
		fldCLILD[i].setAccessible(true);

		res += fldCLILD[i].getName();
		if (i != fldCLILD.length - 1)
		    res += ",";

		fldCLILD[i].setAccessible(false);
	    }

	    if (fldPARENT.length != 0)
	    {
		res += ",";
		for (int i = 0; i < fldPARENT.length; i++)
		{
		    fldPARENT[i].setAccessible(true);

		    res += b.getClass().getSuperclass().getSimpleName() + "_"
			    + fldPARENT[i].getName();
		    if (i != fldPARENT.length - 1)
			res += ",";

		    fldPARENT[i].setAccessible(false);
		}

	    }

	    res += ")VALUES(" + numbInList + ",";

	    for (int i = 0; i < fldCLILD.length; i++)
	    {
		fldCLILD[i].setAccessible(true);

		switch (fldCLILD[i].getType().getName())
		    {
			case "int":
			    res += fldCLILD[i].getInt(b);
			    if (i != fldCLILD.length - 1)
				res += ",";
			    break;

			default:
			    res += fldCLILD[i].getFloat(b);
			    if (i != fldCLILD.length - 1)
				res += ",";
			    break;
		    }
		fldCLILD[i].setAccessible(false);
	    }

	    if (fldPARENT.length > 0)
	    {
		res += ",";
		for (int i = 0; i < fldPARENT.length; i++)
		{
		    fldPARENT[i].setAccessible(true);
		    switch (fldPARENT[i].getType().getName())
			{
			    case "int":
				res += fldPARENT[i].getInt(b);
				if (i != fldPARENT.length - 1)
				    res += ",";
				break;

			    default:
				res += fldPARENT[i].getFloat(b);
				if (i != fldPARENT.length - 1)
				    res += ",";
				break;
			}
		    fldPARENT[i].setAccessible(false);
		}

	    }
	}

	res += ");";
	// System.out.println(res);
	ps = con.prepareStatement(res);
	ps.executeUpdate();

    }

    @Override
    void save(ArrayList<Base> Lst) throws Exception
    {
	Class.forName("com.mysql.jdbc.Driver").newInstance();

	String url = "jdbc:mysql://localhost/t";

	con = DriverManager.getConnection(url, "root", "");

	// Field[] f = null;

	PreparedStatement ps = null;
	
	for (Base tmp : Lst)
	{
	    doCreateRequest(tmp, ps);
	    doInsertRequest(tmp, ps, counter);
	    counter++;
	}

	if (ps != null)
	{
	    ps.close();
	}
	if (rs != null)
	{
	    rs.close();
	}
	if (stmt != null)
	{
	    stmt.close();
	}
	if (con != null)
	{
	    con.close();
	}
    }

    private ArrayList<Base> fromResult(String clsName) throws Exception
    {
	// clsName=
	clsName = clsName.substring(0, 1).toUpperCase()
		+ clsName.substring(1, clsName.length());
	// System.out.println(clsName);
	ArrayList<Base> ar = new ArrayList();
	Class<?> cl = Class.forName(clsName);

	Base b = (Base) cl.newInstance();

	Field[] f = cl.getDeclaredFields();

	int n = f.length
		+ b.getClass().getSuperclass().getDeclaredFields().length;
	String tmp = "";

	int i;
	// System.out.println(clsName);
	while (rs.next())
	{
	    i = 3;

	    index.add(Integer.parseInt(rs.getString(i - 1)));

	    if (clsName.equals("Base"))
	    {
		tmp = clsName + ";";
		for (; i <= n + 2; i++)
		{
		    tmp += rs.getString(i) + ";";
		}
	    }

	    else
	    {
		tmp = clsName + ";";
		for (; i <= n + 2; i++)
		{
		    if (i == n + 1)
		    {
			tmp += "|" + b.getClass().getSuperclass().getName()
				+ ";";
		    }

		    tmp += rs.getString(i) + ";";

		}

	    }

	    ar.add(Serv.set(b, f, tmp));
	    tmp = "";
	}

	return ar;
    }

    @Override
    ArrayList<Base> load() throws Exception
    {
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	String url = "jdbc:mysql://localhost/t";
	con = DriverManager.getConnection(url, "root", "");
	Statement st = con.createStatement();
	Statement st2 = con.createStatement();
	Statement drop = con.createStatement();
	ArrayList<Base> ar = new ArrayList();

	ResultSet getTables = st.executeQuery("SHOW  TABLES from t");
	String tmp = "";
	
	index = new ArrayList();
	
	while (getTables.next())
	{
	    tmp = getTables.getString(1);
	    // System.out.println(tmp);
	    rs = st2.executeQuery("SELECT * FROM " + tmp);

	    ar.addAll(fromResult(tmp));
	    
	    drop.executeUpdate("DELETE FROM " + tmp);
	}
	ar = sort(ar);

	if (st != null)
	{
	    st.close();
	}
	if (st2 != null)
	{
	    st2.close();
	}
	if (drop != null)
	{
	    drop.close();
	}
	if (stmt != null)
	{
	    stmt.close();
	}
	if (con != null)
	{
	    con.close();
	}
	return ar;
    }

    ArrayList<Base> sort(ArrayList<Base> lst)
    {
	int tmp;
	Base tBase;
	for (int i = 0; i < index.size() - 1; ++i)
	{
	    for (int j = 0; j < index.size() - 1; ++j)
	    {
		if (index.get(j + 1) < index.get(j))
		{
		    tmp = index.get(j + 1);
		    index.set(j + 1, index.get(j));
		    index.set(j, tmp);

		    tBase = lst.get(j + 1);
		    lst.set(j + 1, lst.get(j));
		    lst.set(j, tBase);

		}
	    }
	}

	return lst;

    }
}
