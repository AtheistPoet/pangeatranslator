package pangea;

import pangea.config.Loader;
import pangea.plugins.Plugin;
import pangea.xslt.XSLTransformer;

/**
 * Author: Francesco De Nes
 * C: 2009
 */
public class Actions {

    public static String run (String option, String input, String output) {
        //todo caricare il plugin
        //todo avviare la classe pre se necessario
        //todo avviare la trasformazione se necessario
        //todo avviare la classe post se necessario

        pangea.config.utils.Plugin pluginConfig = Loader.getPlugin(option);

        try{
            Plugin pre = null;
            Plugin post = null;

            String preType = null;
            String postType;

            //caricamento del plugin
            boolean hasPre = pluginConfig.getType().indexOf("pre")!=-1;
            boolean hasPost = pluginConfig.getType().indexOf("post")!=-1;
            if (hasPre){
                preType = pluginConfig.getPre().get_package().concat(".").concat(pluginConfig.getPre().get_class());
                Class preClass = Class.forName(preType);
                pre = (Plugin) preClass.newInstance();
            }
            if (hasPost){
                postType = pluginConfig.getPost().get_package().concat(".").concat(pluginConfig.getPost().get_class());
                //se la classe del plugin di pre è uguale alla classe del plugin di post, allora usa lo stesso oggetto
                if(pre!=null && postType.compareTo(preType)==0){
                    post = pre;
                }
                else {
                    Class preClass = Class.forName(postType);
                    post = (Plugin) preClass.newInstance();
                }
            }

            //avvio delle operazioni
            if (hasPre) {
                pre.pre(pluginConfig.getPre().getParameters(), input, output);
            }

            if ((pre!=null && pre.transform()) || (post!=null && post.transform())){
                XSLTransformer.transform(input,pluginConfig.getParameterValue("xsl"),output);
            }

            if (hasPost) {
                post.post(pluginConfig.getPost().getParameters(), input, output);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }


        return null;
    }
}
