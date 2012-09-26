import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;



public class TypeJSON extends Types {
    @Override
    void save(ArrayList<Base> inputList) throws Exception {
	// System.out.println("JSONSave");
	FileWriter out = new FileWriter(super.type);	
		
		for(Base tmp:inputList)
		{	
			out.write(convertToJSON(tmp));
		}


		out.flush();
		out.close();
	
    }
    String convertToJSON(Base base)throws Exception
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
	 res="\"Obj\":[\""+tmp1[0]+"\",";
		
		for(int i=1;i<tmp1.length;i++)
		{
			res+="\""+tmp1[i]+"\",";
			
		}
		
		res+="\"Obj\":[\""+tmp2[0]+",";
		for(int i=1;i<tmp2.length;i++)
		{
			res+="\""+tmp2[i]+"\"";
			if(i!=tmp2.length-1)res+=",";
		}
		res+="]";
		
	res+="]\r\n";
	 
	}
	else
	{
	    String []tmp2=str.split(";");
	    
	    res+="\"Obj\":[\""+tmp2[0]+",";
		for(int i=1;i<tmp2.length;i++)
		{
			res+="\""+tmp2[i]+"\"";
			if(i!=tmp2.length-1)res+=",";
		}
		res+="]\r\n";
	
	    
	}
	
    return res;
		
		
    }
   
    @Override
    ArrayList<Base> load() throws Exception {
	
	ArrayList<Base> p = new ArrayList<Base>();
	    String s;
	    try
	    {
		BufferedReader in = new BufferedReader(new FileReader(super.type));
	        while ((s = in.readLine()) != null)
	        {
	           
	           p.add(convertFromJSON(s));
	        }
	        
	       in.close();
	    } catch (FileNotFoundException e)
	    {
	    }
	    
	    return p;
	
    }
    Base convertFromJSON(String str)throws Exception
    {
	str=str.replace("\"","");
	str=str.replace("Obj:","");
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
