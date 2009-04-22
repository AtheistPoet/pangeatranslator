package pangea.tester;

import org.jibx.runtime.*;

import pangea.pnml.type.*;
import pangea.config.type.*;
import pangea.config.Loader;

import java.io.*;

import pangea.xslt.XSLTransformer;
import pangea.mem.Cache;
import pangea.kegg.types.Equation;
import pangea.logging.Log;

/*
todo implementazione del caricamento dei plugin e dell'avvio in base alle opzioni della linea di comando
todo modificare Cache in modo che sia consistente (caricamento dei valori dalle giuste classi e non accentramento in Cache)
todo vedere come gestire il graph-drawing
todo implementare l'interfaccia grafica
*/

public class Test {

    private static Loader loader;

    public static Loader getLoader() {
        return loader;
    }

    public static void main(String[] args) {

        try {
            //caricamento dei parametri di configurazione
            loader = new Loader();

            //caricamento del log
            try{
                Log.setupFileLog(Loader.getLogPath(Loader.ERRLOG),Loader.getLogPath(Loader.WARLOG),Loader.getLogPath(Loader.MESLOG),false);
            }
            catch(Exception ex){
                //log disabled
            }




            
            analisi();





        } catch (JiBXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            Log.close();
        }

    }

    /**
     * metodo temporaneo per la verifica della trasformazione
     */
    public static void analisi(){
        String xml, xsl, out, result;

        xml = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\20090409\\ko00472.xml";
        xsl = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl\\net.xsl";
        out = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp.xml";
        result = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml00472.xml";

        try{
            Cache.loadAllXSL("C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XSLTransformer.transform(xml,xsl,out);

        try{
            IBindingFactory bfact = BindingDirectory.getFactory(Pnml.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            Pnml p = (Pnml) umcon.unmarshalDocument(new FileInputStream(out),null);

            String message = "la rete " + p.getPnml().getId() + " è composta da:\n" +
                    p.getPnml().getPlaces().size() + " posti\n" +
                    p.getPnml().getTransitions().size() + " transizioni\n" +
                    p.getPnml().getArcs().size() + " archi\n";

            int i = 0;
            for (Transition transition:p.getPnml().getTransitions()){
                if (!transition.getId().endsWith("#rev")){
                    try{
                        Cache.getReaction(transition.getId());
                    } catch (Exception e) {
                        i++;
                    }
                }
            }

            if (i>0) Log.newError(i + " equazioni non sono state recuperate.");

            Log.newMessage(Cache.soutEquations(false));

            setWeights(p, bfact.createMarshallingContext(), result);

            
            //IMarshallingContext mcon = bfact.createMarshallingContext();

            //mcon.marshalDocument(p,"UTF-8",null,new FileOutputStream(""));

        } catch (JiBXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("eccezione nel file xml");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void setWeights(Pnml rete, IMarshallingContext imcon, String out) {

        for (Arc arc:rete.getPnml().getArcs()){
            boolean isSrc = arc.getSource().startsWith("rn:");
            String reaction = (isSrc)?arc.getSource():arc.getTarget();
            String component = (!isSrc)?arc.getSource():arc.getTarget();
            int sharp = reaction.indexOf("#");
            if (sharp!=-1) reaction = reaction.substring(0,sharp);

            try {
                Equation eq = Cache.getReaction(reaction);
                Inscription weight = new Inscription();
                /*if ((isSrc && sharp!=-1) || (!isSrc && sharp==-1)){
                    weight.setValue(String.valueOf(eq.getSourceComponentValue(component)));
                }
                else {
                    weight.setValue(String.valueOf(eq.getTargetComponentValue(component)));
                }*/
                weight.setValue(String.valueOf(eq.getComponentValue(component)));
                arc.setInscription(weight);
            } catch (Exception ex) {
                System.out.println("eccezione");
            }
        }

        try {
            imcon.marshalDocument(rete,"UTF-8",null,new FileOutputStream(out));
        } catch (JiBXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void config() {
        String xml = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\config.xml";

        IBindingFactory bfact;
        try {
            bfact = BindingDirectory.getFactory(PangeaConfig.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            PangeaConfig pconf = (PangeaConfig) umcon.unmarshalDocument(new FileInputStream(xml),null);

            for (Plugin p:pconf.getPlugins()){
                System.out.println(
                        "name:\t" + p.getName() +
                        "\noption:\t" + p.getOption() +
                        "\ntype:\t" + p.getType() +
                        "\nactive:\t" + p.getActive() +
                        "\npackage:\t" + p.getPackage() +
                        "\nclass:\t" + p.get_Class()
                );
            }
        } catch (JiBXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("file di configurazione non trovato");
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
