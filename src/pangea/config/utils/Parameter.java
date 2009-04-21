package pangea.config.utils;

/**
 * Author: Francesco De Nes
 * Date: 21-abr-2009
 */
public class Parameter {
    private String name;
    private String value;

    public Parameter(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
