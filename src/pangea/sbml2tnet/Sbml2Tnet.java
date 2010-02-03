package pangea.sbml2tnet;

import pangea.plugins.Plugin;
import pangea.graphdrawing.Draw;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Hashtable;
import java.io.*;
import java.net.URL;

import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.IMarshallingContext;
import timenet.scpn.type.*;

/**
 * Created by IntelliJ IDEA.
 * User: Francesco
 * Date: 29-gen-2010
 * Time: 15.02.44
 * To change this template use File | Settings | File Templates.
 */
public class Sbml2Tnet implements Plugin {
    @Override
    public Hashtable<String,String> pre(Hashtable params, String input, String output) {
        //non fare nulla, basta la trasformazione
        Hashtable<String,String> xslparams = new Hashtable<String, String>(1);
        xslparams.put("scaleval","1000");
        xslparams.put("dist","EXP");
        return xslparams;
    }

    @Override
    public void post(Hashtable params, String input, String output) {
        FileInputStream fis=null;
        try{
            IBindingFactory bfact = BindingDirectory.getFactory(SCPNNet.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            fis = new FileInputStream(output);
            SCPNNet net = (SCPNNet) umcon.unmarshalDocument(fis,null);

            if (false && net.getPlaces()!=null){
                for (PlaceType place:net.getPlaces()){
                    String im = place.getInitialMarking();
                    try{
                        int intIm = Integer.valueOf(im);
                        String marking;
                        for (marking="";intIm>0;intIm--){
                            marking=marking.concat("0");
                            if (intIm>1) marking=marking.concat(", ");
                        }
                        im = marking;
                    }catch(Exception e){
                        //donothing
                    }
//                    place.getLabel().setInitialMarking(im);
                    place.setInitialMarking(im);
                }
            }

            Draw.drawTNet(net);

            IMarshallingContext imcon = bfact.createMarshallingContext();
            imcon.marshalDocument(net,"UTF-8",null,new FileOutputStream(output));


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean transform() {
        return true;
    }
}
