package pangea.sbml2tnet;

import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;
import java.util.Hashtable;

import pangea.graphdrawing.Draw;
import pangea.plugins.Plugin;

import timenet.edspn.type.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: Francesco
 * Date: 6-feb-2010
 * Time: 0.01.32
 * To change this template use File | Settings | File Templates.
 */
public class Sbml2TnetEDSPN  implements Plugin {
    @Override
    public Hashtable<String,String> pre(Hashtable params, String input, String output) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc;

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new File(input));

            doc.getAttributes();
            Node node = doc.getElementsByTagName("sbml").item(0);

            try{
                Element el = (Element) node;
                String attval = el.getAttribute("xmlns");
                el.removeAttribute("xmlns");
                if (attval!=null && !attval.equals("")){
                    el.setAttribute("xmlns:sl2",attval);
                }
            }
            catch(Exception ex){
                //donothing
            }

			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
            format.setEncoding("UTF-8");

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(input),"UTF-8");

			XMLSerializer serializer = new XMLSerializer();

            serializer.setOutputFormat(format);
		    serializer.setOutputCharStream(osw);
		    serializer.serialize(doc);

            osw.flush();
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Hashtable<String,String> xslparams = new Hashtable<String, String>(1);
        xslparams.put("scaleval","1000");
        xslparams.put("preemptionpolicy","PRD");
        return xslparams;
    }

    @Override
    public void post(Hashtable params, String input, String output) {
        FileInputStream fis=null;
        try{
            IBindingFactory bfact = BindingDirectory.getFactory(EDSPNNet.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            fis = new FileInputStream(output);
            EDSPNNet net = (EDSPNNet) umcon.unmarshalDocument(fis,null);

            Draw.drawTNetEDSPN(net);

            IMarshallingContext imcon = bfact.createMarshallingContext();
            imcon.marshalDocument(net,"UTF-8",null,new FileOutputStream(output));


        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if (fis!=null) try {
                fis.close();
            } catch (IOException e) {
                //donothing
            }
        }
    }

    @Override
    public boolean transform() {
        return true;
    }
}
