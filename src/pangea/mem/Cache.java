package pangea.mem;

import pangea.xslt.XSLTransformer;
import pangea.kegg.types.Equation;
import pangea.kegg.Retriever;

import java.util.Hashtable;
import java.util.Enumeration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;


public class Cache {
    private static Hashtable<String, Transformer> xsls = new Hashtable<String, Transformer>();
    private static Hashtable<String,Equation> reactions = new Hashtable<String, Equation>();


    public static Transformer getXSL (String xslPath) throws TransformerConfigurationException {
        Transformer xsl = xsls.get(xslPath);
        if (xsl==null){
            xsl = XSLTransformer.parseXSL(xslPath);
            xsls.put(xslPath,xsl);
        }
        return xsl;
    }



    public static int loadAllXSL (String xslDir) throws FileNotFoundException {
        if (xslDir==null) throw new FileNotFoundException();

        File dir = new File(xslDir);
        if (!dir.exists() || !dir.isDirectory()) throw new FileNotFoundException();
        
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name!=null && name.endsWith(".pangea.xsl"));
            }
        });

        int loaded = 0;
        for (File file:files){
            try{
                getXSL(file.getAbsolutePath());
                loaded++;
            }
            catch (TransformerConfigurationException tcex){
                //todo aggiungere il log di mancata trasformazione del foglio di stile
            }
        }

        return loaded;
    }



    public static Equation getReaction(String reaction) throws Exception{
        Equation res = reactions.get(reaction);

        if (res==null){
            res = Retriever.getEquation(Retriever.equationRetriever(Retriever.getFromKegg(reaction)));
            reactions.put(reaction,res);
        }

        return res;
    }

    public static void soutEquations() {
        int i = 0;
        for (String k:reactions.keySet()){
            System.out.println(++i + "\t: " + reactions.get(k).getEquationString());
        }
    }
}
