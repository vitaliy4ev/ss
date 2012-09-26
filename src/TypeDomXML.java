import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TypeDomXML  extends Types 
{
    
    @Override
    void save(ArrayList<Base> inputList) throws Exception 
    {
	
	XStream xstream = new XStream();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(super.type));
            out.write(xstream.toXML(inputList));
            out.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
	
    }

    @Override
    ArrayList<Base> load() throws Exception 
    {
	
	ArrayList<Base> p = null;
        XStream xstream = new XStream(new DomDriver());
        FileInputStream fis = new FileInputStream(super.type);
        
        p = (ArrayList<Base>) xstream.fromXML(fis);
        
        fis.close();
        return p;
    }

}
