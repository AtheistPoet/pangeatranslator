package pangea.sbml2tnet;

import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Hashtable;

import pangea.graphdrawing.Draw;
import pangea.plugins.Plugin;

import timenet.edspn.type.*;

/**
 * Created by IntelliJ IDEA.
 * User: Francesco
 * Date: 6-feb-2010
 * Time: 0.01.32
 * To change this template use File | Settings | File Templates.
 */
public class Sbml2TnetEDSPN  implements Plugin {
    @Override
    public Hashtable<String,String> pre(Hashtable params, String input, String output) {
        //non fare nulla, basta la trasformazione
        Hashtable<String,String> xslparams = new Hashtable<String, String>(1);
        xslparams.put("scaleval","1000");
        xslparams.put("preemptionpolicy","PRD");
        return xslparams;
    }

    @Override
    public void post(Hashtable params, String input, String output) {
        FileInputStream fis=null;
        try{
            IBindingFactory bfact = BindingDirectory.getFactory(EDSPNNet.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            fis = new FileInputStream(output);
            EDSPNNet net = (EDSPNNet) umcon.unmarshalDocument(fis,null);

            Draw.drawTNetEDSPN(net);

            IMarshallingContext imcon = bfact.createMarshallingContext();
            imcon.marshalDocument(net,"UTF-8",null,new FileOutputStream(output));


        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if (fis!=null) try {
                fis.close();
            } catch (IOException e) {
                //donothing
            }
        }
    }

    @Override
    public boolean transform() {
        return true;
    }
}
