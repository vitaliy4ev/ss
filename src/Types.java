
import java.util.ArrayList;


public abstract class Types
{
    public String type=null;
   
abstract void save(ArrayList<Base> inputList) throws Exception;
abstract ArrayList<Base> load() throws Exception ;
}
