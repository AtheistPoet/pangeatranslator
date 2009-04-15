package pangea.xslt;

import pangea.mem.Cache;

import java.io.*;

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
        return tfactory.newTransformer(new StreamSource(new File(file)));
    }

    public static String transform(String xml, String xsl, String out){

        try{
            StreamResult res;
            if (out==null){
                res = new StreamResult(System.out);
            }
            else {
                res = new StreamResult(new File(out));
            }
            Cache.getXSL(xsl).transform(new StreamSource(new File(xml)), res);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}


