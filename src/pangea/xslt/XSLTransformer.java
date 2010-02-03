package pangea.xslt;

import pangea.mem.Cache;

import java.io.*;
import java.util.Hashtable;

//SAXON

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Author: Francesco De Nes
 */
public class XSLTransformer {

    public static Transformer parseXSL(String file) throws TransformerConfigurationException {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        StreamSource ss = new StreamSource();
        return tfactory.newTransformer(new StreamSource(new File(file)));
    }

    public static String transform(String xml, String xsl, Hashtable<String,String> params, String out) throws Exception{

        StreamResult res=null;
        try{
            Transformer transformer = parseXSL(xsl);
            if (out==null){ //se non specificato, l'output è la console
                res = new StreamResult(System.out);
            }
            else {
                res = new StreamResult(new File(out));
            }
            if (params!=null && params.size()>0){   //imposto tutti i parametri da passare alla xsl
                for (String param:params.keySet()){
                    transformer.setParameter(param,params.get(param));
                }
            }
            transformer.transform(new StreamSource(new File(xml)), res);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return res==null?null:res.toString();
    }

}


