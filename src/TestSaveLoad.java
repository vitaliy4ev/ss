import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class TestSaveLoad
{
    Types ds = null;
    ArrayList<Base> l0, l1, ln;

    public TestSaveLoad(Types type, String format)
    {
	ds = type;

	if (ds.getClass().getName().equals("TypeDB"))
	{
	    ds.type = format;
	}
	else
	    ds.type = "file." + format;
    }

    @Parameters
    public static Collection<Object[]> data() throws Exception
    {
	Object[][] data = new Object[][] {
	// { new TypeCSV(),"CSV" },
	//{ new TypeXML(),"XML" },
	// { new TypeAutoXML(),"AutoXML" },
	//{ new TypeDomXML(),"DomXML" },
	// { new TypeSaxXML(),"SaxXML" },
	// { new TypeYAML(),"YAML" },
	//{ new TypeJSON(),"JSON" },
	//{ new TypeDB(), "localhost/t" }
	
	};

	return Arrays.asList(data);
    }

    @Test
    public void testSaveLoadZero() throws Exception
    {

	ArrayList<Base> orig = new ArrayList();	
	ds.save(orig);
	ArrayList<Base> exp =  ds.load();

	assertTrue(exp.equals(orig));

    }

    @Test
    public void testSaveLoadOne() throws Exception
    {
	ArrayList<Base> orig = new ArrayList();

	orig.add(new Base(1, 2));
	
	ds.save(orig);
	ArrayList<Base> exp = ds.load();
	
	assertTrue(exp.equals(orig));
    }

    @Test
    public void testSaveLoadN() throws Exception
    {
	ArrayList<Base> orig = new ArrayList();
	orig.add(new D1(3, 4, 1, 2));
	orig.add(new Base(1, 2));
	
	orig.add(new D2(3, 4, 5, 6, 1, 2));
		
	//print (orig);
	ds.save(orig);
	
	ArrayList<Base> exp = ds.load();
	//print (exp);
	assertTrue(exp.equals(orig));
    }

    void print(ArrayList<Base> ori)
    {
	for (Base o : ori)
	    System.out.print(o.getA1());
	System.out.println();
    }
}
