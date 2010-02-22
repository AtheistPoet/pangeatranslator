package pangea.tester;

import org.jibx.runtime.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.xerces.dom.AttrImpl;

import pangea.pnml.type.*;
import pangea.config.type.*;
import pangea.config.Loader;

import java.io.*;

import pangea.mem.Cache;
import pangea.kegg.types.Equation;
import pangea.logging.Log;
import pangea.graphdrawing.Draw;
import pangea.Actions;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import sun.plugin.dom.core.Attr;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/*
todo implementazione del caricamento dei plugin e dell'avvio in base alle opzioni della linea di comando
todo modificare Cache in modo che sia consistente (caricamento dei valori dalle giuste classi e non accentramento in Cache)
todo implementare l'interfaccia grafica
*/

public class Test {

    private static Loader loader;

    public static Loader getLoader() {
        return loader;
    }

    public static void main(String[] args) {
        try{
            loader = new Loader();
            System.out.println(Actions.run("sbml2tnetedspn","C:\\Users\\Francesco\\Desktop\\BIOMD0000000023.xml","D:\\BIOMD0000000023.edspn.new.xml"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    //LOG SETUP
    /*public static void main(String args[]){
        //XSLTransformer.transform(null,null);

        try{
            Log.setupFileLog("c:\\Temp\\error.pangea.log","c:\\Temp\\warning.pangea.log","c:\\Temp\\pangea.log.pangea.log",true);

            Log.setErrorLevel(Log.MESSAGE_LEVEL);

            Log.newError("errooooore");
            Log.newWarning("waaaaarning");
            Log.newMessage("messaaaaggggiooooo");
        }
        catch(Exception ex){
            System.out.println("\nahi ahi\n");
            ex.printStackTrace();
        }
        finally {
            Log.close();
        }
    }*/
}
