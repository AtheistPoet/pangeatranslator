package pangea.tester;

import org.jibx.runtime.*;

import net.pangea.type.*;

import java.io.*;

import pangea.xslt.XSLTransformer;
import pangea.mem.Cache;

public class Test {

    public static void main(String args[]){
        String xml, xsl, out, result;

        xml = "C:\\Users\\Francesco De Nes\\Università\\Specialistica\\Tesi\\Kegg\\20090409\\ko00010.xml";
        xsl = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl\\net.xsl";
        out = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\tega.xml";
        result = "C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\xml\\pnml.xml";

        try{
            Cache.loadAllXSL("C:\\Users\\Francesco De Nes\\Progetti\\Java\\PangeaTranslator\\src\\pangea\\xsl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        XSLTransformer.transform(xml,xsl,out);

        try{
            IBindingFactory bfact = BindingDirectory.getFactory(Pnml.class);
            IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

            Pnml p = (Pnml) umcon.unmarshalDocument(new FileInputStream(out),null);

            System.out.println("la rete " + p.getPnml().getId() + " è composta da:");
            System.out.println(p.getPnml().getPlaces().size() + " posti");
            System.out.println(p.getPnml().getTransitions().size() + " transizioni");
            System.out.println(p.getPnml().getArcs().size() + " archi");

            int i = 0;
            for (Transition transition:p.getPnml().getTransitions()){
                try{
                    Cache.getReaction(transition.getId());
                } catch (Exception e) {
                    i++;
                }
            }

            if (i>0) System.out.println(i + " eccezioni");

            Cache.soutEquations();


            //IMarshallingContext mcon = bfact.createMarshallingContext();

            //mcon.marshalDocument(p,"UTF-8",null,new FileOutputStream(""));

        } catch (JiBXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            System.out.println("eccezione nel file xml");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
            System.out.println("\nmerda\n");
            ex.printStackTrace();
        }
        finally {
            Log.close();
        }
    }*/
}
