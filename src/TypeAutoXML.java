import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TypeAutoXML extends Types
{

    @Override
   public void save(ArrayList<Base> inputList) throws Exception
    {
	FileOutputStream os = new FileOutputStream(super.type);

	XMLEncoder encoder = new XMLEncoder(os);	
	
	encoder.writeObject(inputList );

	encoder.close();
    }

    @Override
    public   ArrayList<Base> load() throws Exception
    {
	ArrayList<Base> res = new ArrayList();

	FileInputStream os = new FileInputStream(super.type);
	XMLDecoder decoder = new XMLDecoder(os);

	res = (ArrayList<Base>) decoder.readObject();
	decoder.close();

	return res;
    }

}
