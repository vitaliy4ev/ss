import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class TypeXML extends Types 
{
   
   	FileWriter out;
   	
   	
   	String convertToXML(Base base)throws Exception
	{
		
		String str=Serv.ToString(base);
		String s1="";
		String s2="";
		String res="";
		
		if(str.indexOf("|")!=-1)
		{
		 s1=str.substring(0, str.indexOf("|"));
		 s2=str.substring(str.indexOf("|"),str.length() );
		 
		 String tmp1[]=s1.split(";");
		 String tmp2[]=s2.split(";");
		 res="<"+tmp1[0]+">";
			//D1 a 2 b 3
			for(int i=1;i<tmp1.length;i++)
			{
			    res+="<"+tmp1[i]+">";
			}
			
			res+="<"+tmp2[0]+">";
				for(int i=1;i<tmp2.length;i++)
				{
				    res+="<"+tmp2[i]+">";
				}
			res+="</"+tmp2[0]+">";
			
			
		res+="</"+tmp1[0]+">";
		 
		 
		 
		
		}
		else
		{
		    String []tmp2=str.split(";");
		    
		    res="<"+tmp2[0]+">";
			for(int i=1;i<tmp2.length;i++)
			{
			    res+="<"+tmp2[i]+">";
			}
		res+="</"+tmp2[0]+">";
		
		    
		}
		
		 
		
		 
		return res;
	}
   	
    @Override
    void save(ArrayList<Base> inputList) throws Exception 
    {
	// System.out.println("XMLSave");
	 
	 out = new FileWriter(super.type);	
		
		for(Base tmp:inputList)
		{	
			out.write(convertToXML(tmp)+"\r\n");	
			//System.out.println(convertToXML(tmp));
		}


		out.flush();
		out.close();	
	
    }

    @Override
    ArrayList<Base> load() throws Exception 
    {
	ArrayList<Base> p = new ArrayList<Base>();
	    String s;
	    try
	    {
		BufferedReader in = new BufferedReader(new FileReader(super.type));
	        while ((s = in.readLine()) != null)
	        {
	            //System.out.println(convertFromXML(s));
	           p.add(convertFromXML(s));
	        }
	        
	       in.close();
	    } catch (FileNotFoundException e)
	    {
	    }
	    
	    return p;
    }
    
    Base convertFromXML(String str)throws Exception
    {		
	
	str=str.replace("<","");
	str=str.replace("/","");
	str=str.replace(">",";");
	
	StringBuffer sb=new StringBuffer(str);
	sb= sb.reverse();
	String rev=sb.toString();
	
	if(str.indexOf("|")!=-1)
	{
	sb.replace(0, rev.indexOf("|")+1, "");
	}
	else 
	{
	    sb.replace(0, 5, "");
	}
sb=sb.reverse();
	
	str= sb.toString();
	
	Class<?> cl = Class.forName(str.substring(0, str.indexOf(";")));

	Base b = (Base) cl.newInstance();

	
	
	Field[] f = cl.getDeclaredFields();

	//str = str.substring(str.indexOf(";") + 1);
	//System.out.println(str);
	
	b=Serv.set(b, f, str);
		
		
   
 
    return b;		
    }

	
}
