package pangea;

import pangea.config.Loader;
import pangea.logging.Log;
import pangea.gui.MainGUI;
import org.jibx.runtime.JiBXException;

import java.io.FileNotFoundException;

/**
 * Author: Francesco De Nes
 * C: 2009
 */
public class Startup {

    private static Loader loader;

    public static void main(String[] args) {
        try {
            //caricamento dei parametri di configurazione
            loader = new Loader();

            //caricamento del log
            try{
                Log.setupFileLog(Loader.getLogPath(Loader.ERRLOG),Loader.getLogPath(Loader.WARLOG),Loader.getLogPath(Loader.MESLOG),false);
            }
            catch(Exception ex){
                System.out.println("Log non attivo.");
            }

            String[] olist = Loader.getOptionsList();
            if (olist==null || olist.length<1) {
                System.out.println("Plugins non configurati.");
            }
            else {
                new MainGUI();
            }

        } catch (JiBXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.close();
        }
    }
}
