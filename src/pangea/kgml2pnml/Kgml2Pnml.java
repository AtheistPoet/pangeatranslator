package pangea.kgml2pnml;

import pangea.plugins.Plugin;
import pangea.pnml.type.Pnml;
import pangea.pnml.type.Arc;
import pangea.pnml.type.Inscription;
import pangea.pnml.type.Transition;
import pangea.kegg.types.Equation;
import pangea.kegg.types.EqElement;
import pangea.mem.Cache;
import pangea.logging.Log;
import pangea.graphdrawing.Draw;
import pangea.gui.MainGUI;

import java.util.Hashtable;
import java.util.List;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

import org.jibx.runtime.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.xml.rpc.ServiceException;

import jp.genome.kegg.type.*;

/**
 * Author: Francesco De Nes
 */
public class Kgml2Pnml implements Plugin {

    private static Logger logger = Logger.getLogger(Kgml2Pnml.class);

    @Override
    public Hashtable<String,String> pre(Hashtable params, String input, String output) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try{
            IBindingFactory bfact = BindingDirectory.getFactory(Pathway.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();
            IMarshallingContext mcon = bfact.createMarshallingContext();

            fis = new FileInputStream(input);

            Pathway path = (Pathway) umcon.unmarshalDocument(fis,null);

            Hashtable<String,Object> components = new Hashtable<String, Object>();

            List<Reaction> reactions = path.getReactions();

            /*
            recupero lista componenti effettivamente utilizzati e aggiornamento reazioni nel KGML
             */
            MainGUI.newMessage("recupero lista componenti effettivamente utilizzati e aggiornamento reazioni nel KGML");
            for (Reaction reaction:reactions){
                Equation eq = Cache.getReaction(reaction.getName());

                EqElement[] fh = eq.getSourceElementsArray();
                EqElement[] sh = eq.getTargetElementsArray();

                boolean isfh = false;

                for (EqElement e:fh){
                    if (components.get(e.getCompoundElement())==null) components.put(e.getCompoundElement(),e.getCompoundElement());
                    for (int i = 0;!isfh && i<reaction.getSubstrates().size(); i++){
                        Substrate s = reaction.getSubstrates().get(i);
                        isfh = e.getCompoundElement().equals(s.getName());
                    }
                }

                for (EqElement e:sh){
                    if (components.get(e.getCompoundElement())==null) components.put(e.getCompoundElement(),e.getCompoundElement());
                }

                if (!isfh) {
                    EqElement[] teea = fh;
                    fh = sh;
                    sh = teea;
                }


                reaction.getSubstrates().clear();
                for (EqElement e:fh){
                    Substrate ss = new Substrate();
                    ss.setName(e.getCompoundElement());
                    reaction.getSubstrates().add(ss);
                }
                reaction.getProducts().clear();
                for (EqElement e:sh){
                    Product pr = new Product();
                    pr.setName(e.getCompoundElement());
                    reaction.getProducts().add(pr);
                }

            }

            //memorizzazione composti utilizzati e riscrittura nel KGML
            MainGUI.newMessage("memorizzazione composti utilizzati e riscrittura nel KGML");
            for (Entry e:path.getEntries()){
                if (components.get(e.getName())!=null){
                    components.put(e.getName(),e);
                }
            }

            path.getEntries().clear();

            for (Object e:components.values()){

                if (e instanceof Entry){
                    path.getEntries().add((Entry) e);
                }
                else {
                    path.getEntries().add(buildEntry((String)e));
                }
            }


            fis.close();

            fos = new FileOutputStream(input);

            mcon.marshalDocument(path,"UTF-8",null,fos);

        } catch (JiBXException e) {
            logger.error("Marshalling error processing " + input);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.error("Input file not found: " + input);
            e.printStackTrace();
        } catch (ServiceException e) {
            logger.error("Error accessing web service");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error preprocessing KGML file " + input!=null?input:"");
            e.printStackTrace();
        } finally {
            try{
                fis.close();
            } catch (Exception e) {
                //do nothing
            }

            try{
                fos.close();
            } catch (Exception e) {
                //do nothing
            }
        }

        return null;

    }



    @Override
    /**
     * input->kgml
     * output->pnml
     */
    public void post(Hashtable params, String input, String output) {
        FileInputStream fis=null;

            try{
                IBindingFactory bfact = BindingDirectory.getFactory(Pnml.class);
                IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

                fis = new FileInputStream(output);
                Pnml p = (Pnml) umcon.unmarshalDocument(fis,null);

                String message = "\nla rete " + p.getPnml().getId() + " è composta da:\n" +
                        p.getPnml().getPlaces().size() + " posti\n" +
                        p.getPnml().getTransitions().size() + " transizioni\n" +
                        p.getPnml().getArcs().size() + " archi\n";
                MainGUI.newMessage(message);
                logger.info(message);

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

                if (i>0){
                    MainGUI.newMessage(i + " equazioni non sono state recuperate.");
                    logger.error(i + " equazioni non sono state recuperate.");
                }

                logger.info("\n".concat(Cache.soutEquations(false)));


                try {
                    JFrame frame = Draw.draw(p,true);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                setWeights(p, bfact.createMarshallingContext(), output);


            } catch (JiBXException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("eccezione nel file xml");
                e.printStackTrace();
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

    @Override
    public boolean transform() {
        return true;
    }


    //---------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------- PRIVATE METHODS -----------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------//

    private Entry buildEntry(String s) {
        Entry e = new Entry();
        Graphics g = new Graphics();

        g.setName(s);
        g.setFgcolor("#000000");
        g.setBgcolor("#BFFFBF");
        g.setType(Graphics.Type.RECTANGLE);
        g.setX("100");
        g.setY("100");
        g.setWidth("100");
        g.setHeight("100");

        e.setId("1");
        e.setName(s);
        e.setType(Entry.Type.COMPOUND);
        e.setLink("");
        e.setGraphics(g);

        return e;
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
                MainGUI.newMessage("eccezione nell'impostazione dei pesi degli archi");
                logger.error("eccezione nell'impostazione dei pesi degli archi");
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
}
