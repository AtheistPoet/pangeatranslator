package pangea.kegg.types;

/**
 * Created by IntelliJ IDEA.
 * User: fdenes
 * Date: Apr 15, 2009
 * Time: 2:21:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Equation {


    //il tipo non è determinabile dall'equazione. guardando la formula risultano tutte reversibili
    public static final int TYPE_IRREVERSIBLE = 0;
    public static final int TYPE_REVERSIBLE = 1;


    private EqElement[] fhalf, shalf;
    private int type;
    private String id;

    public Equation(String id, EqElement[] fhalf, EqElement[] shalf, int type) {
        this.id = id;
        this.fhalf = fhalf;
        this.shalf = shalf;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getSourceComponentValue(String component) {
        component = component.substring(component.indexOf(":")+1,component.length());
        for (EqElement el:fhalf) {
            if (component.compareTo(el.getElement())==0) return el.getNum();
        }
        return 0;
    }

    public int getTargetComponentValue(String component) {
        component = component.substring(component.indexOf(":")+1,component.length());
        for (EqElement el:shalf) {
            if (component.compareTo(el.getElement())==0) return el.getNum();
        }
        return 0;
    }

    public int getComponentValue(String component) {
        component = component.substring(component.indexOf(":")+1,component.length());
        for (EqElement el:fhalf) {
            if (component.compareTo(el.getElement())==0) return el.getNum();
        }
        for (EqElement el:shalf) {
            if (component.compareTo(el.getElement())==0) return el.getNum();
        }
        return 0;
    }

    public EqElement[] getElementsArray() {
        EqElement[] els = new EqElement[fhalf.length+shalf.length];
        int i;

        for (i=0; i<fhalf.length; i++) els[i] = fhalf[i];
        for (int j=0; j<shalf.length; j++, i++) els[i] = shalf[j];

        return els;
    }

    public String getEquationString() {
        String res = id.concat("\t ->\t");
        for (int i = 0; i<fhalf.length; i++){
            res = res.concat(fhalf[i].getNum() + " " + fhalf[i].getElement() + " ").concat(i==fhalf.length-1?"":"+");
        }

        if (type==Equation.TYPE_REVERSIBLE){
            res = res.concat("<");
        }
        res = res.concat("=> ");

        for (int i = 0; i<shalf.length; i++){
            res = res.concat(shalf[i].getNum() + " " + shalf[i].getElement() + " ").concat(i==shalf.length-1?"":"+").concat(" ");
        }

        return res;
    }
}
