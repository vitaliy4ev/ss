import java.io.File;
import java.util.ArrayList;


public class Io 
{
	
	
private static ArrayList<Base> lst =null;

public static ArrayList<Base> getList() {
	return lst;
}
public static void setList(ArrayList<Base> lst) {
	Io.lst = lst;
}

public static String fileName=null;
public static String file="file";

private static String format=null;
private static  String []supportFormats={"CSV","XML","DomXML","JSON","YAML","AutoXML","DB","SaxXML"};

private  int i=0;


	public Io(String f) throws Exception
	{		
		 setFormat(f);
		 
	}
	public  String getFormat() 
	{
	return format;
	}

	public void setFormat(String f) throws Exception
	{
	    format=null;
		for(i=0;i<supportFormats.length;i++)
		{
			if(f.equals(supportFormats[i]))
			{
				format=f;
				fileName=file+"."+format;
				break;
			}
		}
		if(format==null)
		{
		    format=supportFormats[0];
		    throw new Exception("Can'tFindFormat,FormatWasSetToDefault");
		}
		    
	}
	/*
	public void addFormat(String fo)throws Exception
	{
		boolean check=false;
		File myFolder = new File(new File(".").getAbsolutePath()+"\\bin");
		
		   File[] files = myFolder.listFiles();
		   
		   for(File ff:files)
		   {
			   if(ff.toString().indexOf(fo)!=-1)
				   check=true;  
		   }
		if(check)
		{	
		String []m=new String[supportFormats.length+1];
		
		for(i=0;i<supportFormats.length;i++)
		{
			m[i]=supportFormats[i];
		}
		
		m[supportFormats.length]=fo;
				
		supportFormats=m;
		}
		else throw new Exception("Can't find any file with name: "+fo+" in the bin folder, to convert! extend Types.class to realise your type");
	}
	*/
	public String[]  getSupportFormats()
	{
		return supportFormats;
	}
	
	
	public void save(ArrayList<Base> inputList )throws Exception
	{
	    
		if(format==null)throw new Exception("Io:Unsupported type format or format wasn't set");
			
		lst=inputList;
		if(lst.size()==0)throw new Exception("Io:The List is Empty");
		File myFolder = new File(new File(".").getAbsolutePath()+"\\bin");
		
		   File[] files = myFolder.listFiles();
		  
		  boolean search=false;
		   for(File ff:files)
		   {		     
		      
			      if(ff.toString().indexOf("Type"+format)!=-1)
			      {			
				 // System.out.println(format);
				  search=true;
				  break;
			      }
		   }
		if(search)
		{
		    
		   Class<?> t = Class.forName("Type"+format);
		   // System.out.println(t.getCanonicalName());
		    
		    
		 
		    Types typ=(Types)t.newInstance();
		   // System.out.println(typ.getClass().getCanonicalName());
		    if(format.equals("DB"))
		    {
			typ.type="localhost/t";
		    }
		    else
		    {
			typ.type=fileName;
		    }
		    
		    typ.save(lst);
		   
		   
		}
		else throw new Exception("Io:Unsupported type format");
	}
	
	public  ArrayList<Base> load( )throws Exception
	{
		if(format==null)throw new Exception("Unsupported type format or format wasn't set");
		
		File myFolder = new File(new File(".").getAbsolutePath()+"\\bin");
		
		   File[] files = myFolder.listFiles();
		   
		   boolean search=false;
		   for(File ff:files)
		   {		      
			      if(ff.toString().indexOf("Type"+format)!=-1)
			      {			
				 // System.out.println(format);
				  search=true;				
				  break;
			      }
		   }
		if(search)
		{
		    Class<?> t = null;// convertor.getClass();
		    
		    t=Class.forName("Type"+format);
		
		   // lst.clear();
		 
		    Types typ=(Types)t.newInstance();
		    if(format.equals("DB"))
		    {
			typ.type="localhost/t";
		    }
		    else
		    {
			typ.type=fileName;
		    }
		    
		    lst=typ.load();		    
		  
		}else throw new Exception("Io:Unsupported type format");
		
		return lst;
	}
	
	
	
}
