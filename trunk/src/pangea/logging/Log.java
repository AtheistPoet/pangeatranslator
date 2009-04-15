package pangea.logging;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Author: Francesco De Nes
 * Date: 16-feb-2009
 */
public class Log {
    //Livelli di pangea.log nel file globale
    public static final int ERROR_LEVEL = 2;
    public static final int WARNING_LEVEL = 1;
    public static final int MESSAGE_LEVEL = 0;


    private static File errors; //file di pangea.log degli errori
    private static File warnings; //file di pangea.log degli avvisi
    private static File global; //file di pangea.log globale

    private static FileWriter e_writer;
    private static FileWriter w_writer;
    private static FileWriter g_writer;

    private static JTextArea e_area = null;
    private static JTextArea w_area = null;
    private static JTextArea g_area = null;

    private static int global_level; //livello di pangea.log da inserire nel file globale

    private static boolean runLog = false; //variabile di esecuzione del pangea.log


    public static void setupFileLog(String errorsPath, String warningPath, String globalPath, boolean abortOnError) throws Exception{
        setupLog(errorsPath, warningPath, globalPath, null, null, null, abortOnError,true);
    }


    public static void setupLog(String errorsPath, String warningPath, String globalPath, JTextArea errorArea, JTextArea warningArea, JTextArea messageArea, boolean abortOnError, boolean append)
        throws Exception {
        try{
            errors = new File(errorsPath);
            warnings = new File(warningPath);
            global = new File(globalPath);

            if((errors.isDirectory() || (!errors.exists() && !errors.createNewFile()) || !errors.canWrite()) || (warnings.isDirectory() || (!warnings.exists() && !warnings.createNewFile()) || !warnings.canWrite()) || (global.isDirectory() || (!global.exists() && !global.createNewFile()) || !global.canWrite())){
                errors = null;
                warnings = null;
                global = null;
                throw new Exception("Problemi nella configurazione dei files.");
            }
            else{
                runLog = true;

                global_level = MESSAGE_LEVEL;

                e_writer = new FileWriter(errors,append);
                w_writer = new FileWriter(warnings,append);
                g_writer = new FileWriter(global,append);

                e_area = errorArea;
                w_area = warningArea;
                g_area = messageArea;
            }
        }
        catch(Exception ex){
            System.out.println("Non è stato possibile configurare correttamente il pangea.log.");
            if (abortOnError) {
                throw ex;
            }
            else{
                //scrivi output nell'eventuale finestra e nello stdout
                System.out.println("L'applicazione funzionerà comunque anche se il pangea.log non sarà attivo.");
            }
        }
    }


    public static void setErrorArea(JTextArea error_area){
        if (error_area!=null){
            e_area = error_area;
        }
    }

    public static void setWarningArea(JTextArea warning_area){
        if (warning_area!=null){
            w_area = warning_area;
        }
    }

    public static void setGlobalArea(JTextArea global_area){
        if (global_area!=null){
            g_area = global_area;
        }
    }


    public static void newError(String error) throws IOException{
        if (runLog){
            error = format(error);
            e_writer.write(error);
            if (e_area!=null) e_area.append(error);
            if (global_level>=ERROR_LEVEL){
                g_writer.write(error);
                if (g_area!=null) g_area.append(error);
            }
        }

    }

    public static void newWarning(String warning) throws IOException{
        if (runLog){
            warning = format(warning);
            w_writer.write(warning);
            if (w_area!=null) w_area.append(warning);
            if (global_level>=WARNING_LEVEL){
                g_writer.write(warning);
                if (g_area!=null) g_area.append(warning);
            }
        }
    }

    public static void newMessage(String message) throws IOException{
        if (runLog){
            message = format(message);
            g_writer.write(message);
            if (g_area!=null) g_area.append(message);
        }
    }

    public static void setErrorLevel(int error_level){
        if (error_level>ERROR_LEVEL || error_level<MESSAGE_LEVEL){
            global_level = MESSAGE_LEVEL;
        }
        else global_level = error_level;
    }

    public static void close(){
        closeWriter(e_writer);
        closeWriter(w_writer);
        closeWriter(g_writer);

    }

    private static void closeWriter(FileWriter fw){
        try{
            fw.flush();
            fw.close();
        }
        catch(Exception ex){
            //non fare nulla
        }
    }


    private static String format(String message){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = sdf.format(Calendar.getInstance().getTime());
        return date.concat("\t").concat((message==null?"":message)).concat("\r\n");
    }


}
