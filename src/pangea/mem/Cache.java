package pangea.mem;

import pangea.xslt.XSLTransformer;
import pangea.kegg.types.Equation;
import pangea.kegg.Retriever;
import pangea.logging.Log;

import java.util.Hashtable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

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
                return (name!=null && name.endsWith(".xsl"));
            }
        });

        int loaded = 0;
        for (File file:files){
            try{
                getXSL(file.getAbsolutePath());
                loaded++;
            }
            catch (TransformerConfigurationException tcex){
                Log.newWarning("Il foglio di stile " + file.getName() + " non è stato caricato in cache.");
            }
        }

        Log.newMessage("Caricati " + loaded + " fogli di stile (xsl).");

        return loaded;
    }


    /**
     * Retrieves the Equation of the reaction from the KEGG or from the cache if already fetched.
     * @param reaction ID of the reaction
     * @return Equation of the reaction
     * @throws Exception exception
     */
    public static Equation getReaction(String reaction) throws Exception{
        Equation res = reactions.get(reaction);

        if (res==null){
            res = Retriever.getEquation(reaction, Retriever.equationRetriever(Retriever.getFromKegg(reaction)));
            reactions.put(reaction,res);
        }

        return res;
    }

    /**
     * Prints out the cached Equations.
     */
    public static void soutEquations() {
        int i = 0;
        for (String k:reactions.keySet()){
            System.out.println(++i + "\t: " + reactions.get(k).getEquationString());
        }
    }
}
