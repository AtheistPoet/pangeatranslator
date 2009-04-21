package pangea.utils;

import pangea.config.utils.Parameter;

import java.util.Vector;

public class StringUtils {

    public static Parameter[] parameterSplitter(String[] args) {
        if (args!=null && args.length>0){
            Vector<Parameter> vec = new Vector<Parameter>();
            for (int i = 0; i<args.length; i++){
                if (i==args.length-1 || args[i+1].startsWith("-") || !args[i].startsWith("-")){
                    vec.add(new Parameter(args[i],null));
                }
                else {
                    vec.add(new Parameter(args[i],args[++i]));
                }
            }
            return vec.toArray(new Parameter[vec.size()]);
        }
        else{
            return null;
        }
    }
}
