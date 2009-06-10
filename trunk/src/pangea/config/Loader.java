package pangea.config;

import java.util.Hashtable;
import java.util.List;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;

import pangea.config.type.PangeaConfig;
import pangea.config.utils.Plugin;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class Loader {

    private static final String configFile = "pangea-config.xml";
    private static final String defaultDir = "/pangea/resources/";

    private static Hashtable<String, Plugin> plugins = new Hashtable<String, Plugin>();
    private static String[] logFiles;

    public static final int ERRLOG = 0;
    public static final int WARLOG = 1;
    public static final int MESLOG = 2;

    private static String defaultPlugin;


    public Loader() throws JiBXException, FileNotFoundException {
        reload();
    }

    public void reload() throws JiBXException, FileNotFoundException {
        InputStream is;
        File f = new File(System.getProperty("user.dir"),configFile);

        if (f.exists() && f.isFile()) is = new FileInputStream(f);
        else is = getClass().getResourceAsStream(defaultDir+configFile);

        if (is==null) throw new FileNotFoundException("Configuration file " + configFile + " not found.");

        IBindingFactory bfact = BindingDirectory.getFactory(PangeaConfig.class);
        IUnmarshallingContext umcon = bfact.createUnmarshallingContext();

        PangeaConfig config = (PangeaConfig) umcon.unmarshalDocument(is,null);

        plugins = setPlugins(config.getPlugins());

        try{
            logFiles = setLogFiles(config.getLog());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Il log non funzionerà.");
        }

    }

    public static Plugin getPlugin(String option){
        return plugins.get(option);
    }

    public static String[] getOptionsList() {
        String[] res = new String[plugins.size()];
        int i=0;
        for (String k:plugins.keySet()) res[i++] = k;
        return res;
    }

    public static String getLogPath(int type) {
        if (logFiles==null || type>=logFiles.length || type<0) return null;
        return logFiles[type];
    }

    public static String getDefaultPluginOption() {
        return defaultPlugin;
    }


    //----------------------------------------------------//
    //                 PRIVATE METHODS                    //
    //----------------------------------------------------//

    private Hashtable<String, Plugin> setPlugins(List<pangea.config.type.Plugin> plugins) {
        defaultPlugin = null;
        if (plugins!=null) {
            Hashtable<String, Plugin> res = new Hashtable<String, Plugin>();
            for (pangea.config.type.Plugin plugin:plugins){
                if (isActive(plugin.getActive().toString()))
                res.put(plugin.getOption(),new Plugin(plugin));
                if (defaultPlugin==null) defaultPlugin = plugin.getOption();
            }
            return res;
        }
        return null;
    }

    private String[] setLogFiles(pangea.config.type.Log log) throws Exception{
        String[] res;
        if (!isActive(log.getActive().toString()))
        {
            res = new String[0];
        }
        else{
            res = new String[3];
            res[ERRLOG] = isActive(log.getError().getActive().toString())?log.getError().getPath():null;
            res[WARLOG] = isActive(log.getWarning().getActive().toString())?log.getWarning().getPath():null;
            res[MESLOG] = isActive(log.getMessage().getActive().toString())?log.getMessage().getPath():null;
        }
        return res;
    }


    private boolean isActive(String yn){
        return "YES".compareToIgnoreCase(yn)==0;
    }
}
