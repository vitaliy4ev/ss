import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class TypeYAML extends Types 
{

    private Base b=null;
	FileWriter out;
	
    @Override
    void save(ArrayList<Base> inputList) throws Exception 
    {
	

	out = new FileWriter(super.type);	
	
	for(Base tmp:inputList)
	{	
	    
		out.write(convertToYAML(tmp)+"\r\n");
		
	}


	out.flush();
	out.close();
    }
    String convertToYAML(Base base)throws Exception
	{
	//- {name: John Smith, age: 33}
		String str=Serv.ToString(base);
		
		String res="";
		
		if(str.indexOf("|")!=-1)
		{
		    String s1= str.substring(0, str.indexOf("|"));
		    String s2= str.substring(str.indexOf("|")+1, str.length());
		    String tmp1[]=s1.split(";");
		    String tmp2[]=s2.split(";");
		    
		    
		    res="["+tmp1[0]+",";
			//D1 a 2 b 3
			for(int i=1;i<tmp1.length;i++)
			{
				res+=tmp1[i]+",";
				
			}
			res+="[|"+tmp2[0]+",";
			for(int i=1;i<tmp2.length;i++)
			{
				res+=tmp2[i];
				if(i!=tmp2.length-1)res+=",";
			}
			res+="]";
			
			res+="]";
		   
		    
		}
		
		else 
		{
		    String tmp2[]=str.split(";");
		    
		    res+="["+tmp2[0]+",";
			for(int i=1;i<tmp2.length;i++)
			{
				res+=tmp2[i];
				if(i!=tmp2.length-1)res+=",";
			}
			res+="]";
		}
		
		
		
		
		
		
		return res;
		
	}
    @Override
    ArrayList<Base> load() throws Exception 
    {
	ArrayList<Base> p = new ArrayList<Base>();
	    String s;
	    
		BufferedReader in = new BufferedReader(new FileReader(super.type));
	        while ((s = in.readLine()) != null)
	        {
	           p.add(convertFromYAML(s));
	        }
	        
	       in.close();
	    return p;
    }
    Base convertFromYAML(String str)throws Exception
    {		
	
	str=str.replace("[","");
	str=str.replace("]","");
	str=str.replace(",",";");
	str+=";";
		
	Class<?> cl = Class.forName(str.substring(0, str.indexOf(";")));

	Base b = (Base) cl.newInstance();
	Field[] f = cl.getDeclaredFields();

	
	b=Serv.set(b, f, str);
	
	return b;

    }
}
