import java.io.Serializable;

class Base implements Serializable {
	
    
	private  int a1=1;
	private int b1=2;
	public Base(int a,int b)
	{
	    a1=a;
	    b1=b;
	}
	public Base()
	{
	    
	}
	public int getA1()
	{
	    return a1;
	}

	public int getB1()
	{
	    return b1;
	}

	public void setA1(int a1)
	{
	    this.a1 = a1;
	}

	public void setB1(int b1)
	{
	    this.b1 = b1;
	}
	
public void sett(String str)
{
   // System.out.println(str);
    String []s=str.split(";");
    
    setA1(Integer.parseInt( s[1]));
    setB1(Integer.parseInt( s[2]));
}

@Override
public int hashCode()
{
    final int prime = 31;
    int result = 1;
    result = prime * result + a1;
    result = prime * result + b1;
    return result;
}

@Override
public boolean equals(Object obj)
{
    if (this == obj)
	return true;
    if (obj == null)
	return false;
    if (getClass() != obj.getClass())
	return false;
    Base other = (Base) obj;
    if (a1 != other.a1)
	return false;
    if (b1 != other.b1)
	return false;
    return true;
}

}





