import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TypeYAMLTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception 
    {
	Blist b1 = new Blist();
	b1.add(new Base(6,6));
	b1.add(new Base(5,5));
	
	b1.setFormat("YAML");	
	assertEquals(b1.getFormat(),"YAML");
	
	b1.save();
	//b1.load();
	
//assertEquals(a,b1.get(0).getA());
//assertEquals(b,b1.get(0).getB());
    }

}
