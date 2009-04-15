package pangea.io;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * User: Francesco De Nes
 */
public class FileInput {

    public static String getFile(String path){
        String out = null;
        FileReader reader;
        int c;

        try{
            reader = getFileReader(path);
            out = "";
            while ((c=reader.read())!=-1){
                out = out.concat(String.valueOf((char)c));
            }
        }
        catch (IOException ioexc){
            System.out.println("Input file doesn't exist.");
        }

        return out;
    }

    public static FileReader getFileReader(String path){
        FileReader out = null;
        File file;

        try{
            file = new File(path);
            out = new FileReader(file);
        }
        catch (FileNotFoundException fnfexc){
            System.out.println("Input file doesn't exist.");
        }

        return out;
    }


    public static String[][] getMultipleFiles(String multiplePath){
        String[] filesNames = multiplePath.split("\"\\s*\"?");
        String[][] result = new String[filesNames.length][2];
        int pos = 0;

        for (String name:filesNames){
            result[pos][0]=name;
            result[pos][1]=getFile(name);
            pos++;
        }

        return result;
    }

    public static Node getFileAsNode(String path) throws Exception{
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                "net.sf.saxon.om.DocumentBuilderFactoryImpl");
        DocumentBuilderFactory dfactory =
                DocumentBuilderFactory.newInstance();

        dfactory.setNamespaceAware(true);

        DocumentBuilder docBuilder = dfactory.newDocumentBuilder();

        String systemId = new File(path).toURI().toURL().toString();
        return docBuilder.parse(new InputSource(systemId));
    }
}
