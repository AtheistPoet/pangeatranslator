package pangea.kgml2pnml;

import pangea.plugins.Plugin;
import pangea.pnml.type.Pnml;
import pangea.pnml.type.Arc;
import pangea.pnml.type.Inscription;
import pangea.pnml.type.Transition;
import pangea.kegg.types.Equation;
import pangea.mem.Cache;
import pangea.logging.Log;
import pangea.graphdrawing.Draw;

import java.util.Hashtable;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

import org.jibx.runtime.*;

import javax.swing.*;

/**
 * Author: Francesco De Nes
 */
public class Kgml2Pnml implements Plugin {


    @Override
    public void pre(Hashtable params, String input, String output) {
    }

    @Override
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
                    JFrame frame = Draw.draw(p,true);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                setWeights(p, bfact.createMarshallingContext(), output);




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

    @Override
    public boolean transform() {
        return true;
    }


    //---------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------- PRIVATE METHODS -----------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------//

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
}
