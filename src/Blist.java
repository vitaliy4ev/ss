
import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Blist extends ArrayList<Base>
{
    private Io io = null;

    public Blist() throws Exception
    {
	io = new Io("CSV");
    }

    public void setFormat(String s) throws Exception
    {
	io.setFormat(s);
    }

    public String getFormat()
    {
	return io.getFormat();
    }

   

    public void save() throws Exception
    {
	io.save(this);
	this.clear();
    }

    public void load() throws Exception
    {

	this.clear();
	this.addAll(io.load());
    }
}
