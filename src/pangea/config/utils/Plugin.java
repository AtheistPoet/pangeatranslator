package pangea.config.utils;

import pangea.config.type.Namevalue;

import java.util.Hashtable;

public class Plugin {

    private String name;
    private String option;
    private String type;
    private Stage pre;
    private Stage post;
    private String _class;
    private String _package;
    private Hashtable<String,Parameter> parameters;

    public Plugin(pangea.config.type.Plugin plugin) {
        name = plugin.getName();
        option = plugin.getOption();
        type = plugin.getType().toString();
        _class = plugin.get_Class();
        _package = plugin.getPackage();
        if (plugin.getPre()!=null) pre = new Stage(plugin.getPre());
        if (plugin.getPost()!=null) post = new Stage(plugin.getPost());
        parameters = new Hashtable<String, Parameter>();
        if (plugin.getParameters()!=null && plugin.getParameters().size()>0){
            for (Namevalue param:plugin.getParameters()){
                if (param.getName()!=null && param.getValue()!=null)
                    parameters.put(param.getName(),new Parameter(param.getName(),param.getValue()));
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    public String getType() {
        return type;
    }

    public Stage getPre() {
        return pre;
    }

    public Stage getPost() {
        return post;
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
