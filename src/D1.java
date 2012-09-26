import java.io.Serializable;


class D1 extends Base implements Serializable {

	private int a=3;
	private float b=4f;
	public D1(int a,float b,int a1,int b1)
	{
	    super (a1,b1);
	    this.a=a;
	    this.b=b;
	}
	public D1()
	{
	    
	}
	
	@Override
	public int hashCode()
	{
	    final int prime = 31;
	    int result = super.hashCode();
	    result = prime * result + a;
	    result = prime * result + Float.floatToIntBits(b);
	    return result;
	}

	@Override
	public boolean equals(Object obj)
	{
	    if (this == obj)
		return true;
	    if (!super.equals(obj))
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    D1 other = (D1) obj;
	    if (a != other.a)
		return false;
	    if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
		return false;
	    return true;
	}

	public void setA(int a)
	{
	    this.a = a;
	}

	public void setB(float b)
	{
	    this.b = b;
	}
	public int getA()
	{
	    return a;
	}
	public float getB()
	{
	    return b;
	}

}




	


