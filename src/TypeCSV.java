import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class TypeCSV extends Types
{
    FileWriter out;


    @Override
    void save(ArrayList<Base> inputList) throws Exception
    {

	out = new FileWriter(super.type);

	for (Base tmp : inputList)
	{	   
	    out.write(Serv.ToString(tmp) + "\r\n");
	}

	out.flush();
	out.close();
    }

    ArrayList<Base> load() throws Exception
    {

	ArrayList<Base> p = new ArrayList<Base>();
	String s;
	
	    BufferedReader in = new BufferedReader(new FileReader(super.type));

	    while ((s = in.readLine()) != null)
	    {
		p.add(fromString(s));
		//System.out.println(fromString(s).getA1());
	    }

	    in.close();
	

	return p;

    }

    private Base fromString(String str) throws Exception
    {
	
	Class<?> cl = Class.forName(str.substring(0, str.indexOf(";")));

	Base b = (Base) cl.newInstance();

	Field[] f = cl.getDeclaredFields();

	//str = str.substring(str.indexOf(";") + 1);
	//System.out.println(str);
	
	b=Serv.set(b, f, str);
	
	return b;

    }

}
