package pangea;

import pangea.config.Loader;
import pangea.logging.Log;
import pangea.gui.MainGUI;
import org.jibx.runtime.JiBXException;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.io.FileNotFoundException;

/**
 * Author: Francesco De Nes
 * C: 2009
 */
public class Startup {

    private static Loader loader;
    private static Logger logger = Logger.getLogger(Startup.class);

    public static void main(String[] args) {
        try {
            //caricamento dei parametri di configurazione
            loader = new Loader();

            //caricamento del log
            /*try{
                Log.setupFileLog(Loader.getLogPath(Loader.ERRLOG),Loader.getLogPath(Loader.WARLOG),Loader.getLogPath(Loader.MESLOG),false);
            }
            catch(Exception ex){
                System.out.println("Log non attivo.");
            }*/

            //caricamento log4j
            BasicConfigurator.configure();


            String[] olist = Loader.getOptionsList();
            if (olist==null || olist.length<1) {
                logger.fatal("Plugins non configurati.");
                //System.out.println("Plugins non configurati.");
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
