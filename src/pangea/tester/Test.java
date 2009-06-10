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
import pangea.graphdrawing.Draw;
import pangea.Actions;

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

            String[] olist = Loader.getOptionsList();
            if (olist==null || olist.length<1) {
                System.out.println("Plugins non configurati.");
            }
            else {
                //avvia l'interfaccia grafica
            }


            String res = Actions.run("kgml2pnml",
                    "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\vfi00010.xml",
                    "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_vfi00010.xml");

            System.out.println("res = " + res);



            
            //analisi();





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
        String xml[], xsl, out[], result[];

        xml = new String[5];
        result = new String[5];
        out = new String[5];

        xml[0] = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\dme00010.xml";
        xml[1] = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\ecx00010.xml";
        xml[2] = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\mja00010.xml";
        xml[3] = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\sce00010.xml";
        xml[4] = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\organismi scelti\\vfi00010.xml";

        xsl = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl\\net.xsl";

        out[0] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp1.xml";
        out[1] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp2.xml";
        out[2] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp3.xml";
        out[3] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp4.xml";
        out[4] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_temp5.xml";

        result[0] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_dme00010.xml";
        result[1] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_ecx00010.xml";
        result[2] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_mja00010.xml";
        result[3] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_sce00010.xml";
        result[4] = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml_vfi00010.xml";

        /*try{
            Cache.loadAllXSL("C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        for (int k = 0; k<1; k++) {
            XSLTransformer.transform(xml[k],xsl,out[0]);
            FileInputStream fis=null;

            try{
                IBindingFactory bfact = BindingDirectory.getFactory(Pnml.class);
                IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

                fis = new FileInputStream(out[0]);
                Pnml p = (Pnml) umcon.unmarshalDocument(fis,null);

                String message = "\nla rete " + p.getPnml().getId() + " è composta da:\n" +
                        p.getPnml().getPlaces().size() + " posti\n" +
                        p.getPnml().getTransitions().size() + " transizioni\n" +
                        p.getPnml().getArcs().size() + " archi\n";
                Log.newMessage(message);

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

                Log.newMessage("\n".concat(Cache.soutEquations(false)));

                try {
                    Draw.draw(p,false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                setWeights(p, bfact.createMarshallingContext(), result[k]);




                //IMarshallingContext mcon = bfact.createMarshallingContext();

                //mcon.marshalDocument(p,"UTF-8",null,new FileOutputStream(""));

            } catch (JiBXException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("eccezione nel file xml");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (fis!=null) try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
