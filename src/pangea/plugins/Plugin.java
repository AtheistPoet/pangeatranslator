package pangea.plugins;

import pangea.config.utils.Parameter;

import javax.swing.*;
import java.util.Hashtable;

/**
 * Author: Francesco De Nes
 * Date: 26-may-2009
 */
public interface Plugin {

    /**
     * Operazioni da eseguire prima della trasformazione.
     * @param params parametri d'ingresso
     * @param input path del file xml d'ingresso
     * @param output path del file di uscita
     * @return Hashtable contenente i parametri da passare alla xsl al momento della trasformazione
     */
    public Hashtable<String,String> pre(Hashtable<String, Parameter> params, String input, String output);

    /**
     * Operazioni da eseguire dopo la trasformazione.
     * @param params parametri d'ingresso
     * @param input path del file xml d'ingresso
     * @param output path del file di uscita
     */
    public void post(Hashtable<String, Parameter> params, String input, String output);

    /**
     * Indica se bisogna effettuare la trasformazione.
     * @return true se la trasformazione dev'essere eseguita, false altrimenti.
     */
    public boolean transform();
}
