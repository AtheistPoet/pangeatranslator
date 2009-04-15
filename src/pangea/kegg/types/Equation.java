package pangea.kegg.types;

/**
 * Created by IntelliJ IDEA.
 * User: fdenes
 * Date: Apr 15, 2009
 * Time: 2:21:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Equation {

    public static final int TYPE_IRREVERSIBLE = 0;
    public static final int TYPE_REVERSIBLE = 1;


    private EqElement[] fhalf, shalf;
    private int type;

    public Equation(EqElement[] fhalf, EqElement[] shalf, int type) {
        this.fhalf = fhalf;
        this.shalf = shalf;
        this.type = type;
    }

    public EqElement[] getFhalf() {
        return fhalf;
    }

    public EqElement[] getShalf() {
        return shalf;
    }

    public int getType() {
        return type;
    }

    public String getEquationString() {
        String res = new String("");
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
