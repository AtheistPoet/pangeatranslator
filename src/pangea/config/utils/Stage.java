package pangea.config.utils;

import pangea.config.type.Prepost;
import pangea.config.type.Namevalue;

import java.util.Hashtable;

/**
 * Author: Francesco De Nes
 * Date: 21-abr-2009
 */
public class Stage implements Cloneable{
    private String _class;
    private String _package;
    private Hashtable<String,Parameter> parameters;

    public Stage(Prepost stage) {
        _class=stage.get_Class();
        _package=stage.getPackage();
        parameters = new Hashtable<String, Parameter>();
        if (stage.getParameters()!=null && stage.getParameters().size()>0){
            for (Namevalue param:stage.getParameters()){
                if (param.getName()!=null && param.getValue()!=null)
                    parameters.put(param.getName(),new Parameter(param.getName(),param.getValue()));
            }
        }
    }

    public String get_class() {
        return _class;
    }

    public String get_package() {
        return _package;
    }

    public Hashtable<String, Parameter> getParameters() {
        return parameters;
    }

    public Parameter getParameter(String name) {
        return parameters.get(name);
    }

    public String getParameterValue(String name) {
        Parameter p = parameters.get(name);
        return p==null?null:p.getValue();
    }

}
