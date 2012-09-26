import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TypeSaxXML extends Types
{

    @Override
    void save(ArrayList<Base> inputList) throws Exception
    {

	FileOutputStream outputStream = new FileOutputStream(new File(
		super.type));

	XMLStreamWriter out = XMLOutputFactory.newInstance()
		.createXMLStreamWriter(
			new OutputStreamWriter(outputStream, "utf-8"));

	out.writeStartDocument();
	out.writeStartElement("BList");
	String mas = "";
	String str = "";
	String[] s;
	for (int i = 0; i < inputList.size(); i++)
	{
	    str = Serv.ToString(inputList.get(i));

	    if (str.indexOf("|") > 0)
	    {
		mas = str.substring(0, str.indexOf("|"));
		s = mas.split(";");
		// System.out.println("if1 " + mas);
		out.writeStartElement(s[0]);
		for (int j = 1; j < s.length; j++)
		{
		    out.writeStartElement(s[j++]);
		    out.writeCharacters(s[j]);
		    out.writeEndElement();
		}
		out.writeEndElement();

		mas = str.substring(str.indexOf("|"), str.length());
		// System.out.println("if2 "+mas);
		s = mas.split(";");

		out.writeStartElement(s[0]);
		for (int j = 1; j < s.length; j++)
		{
		    out.writeStartElement(s[j++]);
		    out.writeCharacters(s[j]);
		    out.writeEndElement();
		}
		out.writeEndElement();

	    }
	    else
	    {

		s = str.split(";");
		// System.out.println("Else"+ str);
		out.writeStartElement(s[0]);
		for (int j = 1; j < s.length; j++)
		{
		    out.writeStartElement(s[j++]);
		    out.writeCharacters(s[j]);
		    out.writeEndElement();
		}
		out.writeEndElement();
	    }
	    // System.out.println(str);

	}

	out.writeEndElement();
	out.writeEndDocument();

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
		// System.out.println(convertFromXML(s));
		p.add(convertFromSaxXML(s));
	    }

	    in.close();
	} catch (FileNotFoundException e)
	{
	}

	return p;
    }

    Base convertFromSaxXML(String str) throws Exception
    {
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
	Base b = null;
	DefaultHandler handler = new DefaultHandler();

	saxParser.parse(super.type, handler);

	return b;
    }

}

class Handler extends DefaultHandler
{
    private String str = "";
    private ArrayList<Base> base = new ArrayList<Base>();

    public ArrayList<Base> getBase()
    {
	return base;
    }

    public void startElement(String uri, String localName, String NameElement,
	    Attributes attributes) throws SAXException
    {
	
	if (!NameElement.equals("BList"))
	{
	    str += NameElement + ";";
	}
    }

    public void characters(char ch[], int start, int length)
	    throws SAXException
    {
	str += new String(ch, start, length) + ";";
	System.out.println("ssdf");
    }

    public void endElement(String uri, String localName, String NameElement)
	    throws SAXException
    {
	
	if (NameElement.equals("Base") || NameElement.equals("D1")
		|| NameElement.equals("D2"))
	{
	    try
	    {
		//base.add(ConvFactory.getInstance("CSV").fromString(str));
	    } catch (Exception e)
	    {

	    }
	    str = "";
	}
    }

}
