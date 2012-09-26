import java.util.ArrayList;

import org.junit.Test;


public class TestDBWriteTest
{
    @Test
    public void test() throws Exception
    {
	TypeDB sq=new TypeDB();
	 ArrayList<Base> ar=new ArrayList();
	 ar.add(new Base(2,3));
	 ar.add(new D1(1,2,3,4));
	 ar.add(new D2(1,2,3,4,4,5));
	 
	
	 
	 sq.type="localhost/t";
	 //print (ar);
	sq.save(ar);
	//print (
		sq.load();
		//);
	/*
	342
	234
	 */
    }
    void print(ArrayList<Base> ori)
    {
	for (Base o : ori)
	    System.out.print(o.getA1());
	System.out.println();
    }
}
