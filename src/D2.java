import java.io.Serializable;



class D2 extends Base implements Serializable {
	
	private int q=5,w=6,r=7,x=8;
	public D2(int q1,int w1,int r1,int x1,int a,int b)
	{
	    super(a,b);
	    q=q1;
	    w=w1;
	    r=r1;
	    x=x1;
	}
	public D2()
	{
	    
	}
	public int getW() {return w;}
	public int getQ() {return q;}
	public int getR() {return r;}
	public int getX() {return x;}
	
	public void setQ(int q) {this.q = q;}
	public void setW(int w) {this.w = w;}
	public void setR(int r) {this.r = r;}
	public void setX(int x) {this.x = x;}
	@Override
	public int hashCode()
	{
	    final int prime = 31;
	    int result = super.hashCode();
	    result = prime * result + q;
	    result = prime * result + r;
	    result = prime * result + w;
	    result = prime * result + x;
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
	    D2 other = (D2) obj;
	    if (q != other.q)
		return false;
	    if (r != other.r)
		return false;
	    if (w != other.w)
		return false;
	    if (x != other.x)
		return false;
	    return true;
	}
	

	
}
