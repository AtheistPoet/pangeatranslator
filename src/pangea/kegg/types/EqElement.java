package pangea.kegg.types;

/**
 * Created by IntelliJ IDEA.
 * User: fdenes
 * Date: Apr 15, 2009
 * Time: 2:17:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class EqElement {

    private int num;
    private String element;

    public EqElement(String element) throws Exception{
        this(1,element);
    }

    public EqElement(int num, String element) throws Exception{
        if (num<1 || element==null) throw new Exception("Invalid parameters in equation element creation.");
        this.num = num;
        this.element = element;
    }

    public int getNum() {
        return num;
    }

    public String getElement() {
        return element;
    }

    public String getCompoundElement() {
        return "cpd:".concat(element);
    }


    public static EqElement getElementFromString (String element) throws Exception{
        String[] splitted = element.trim().split(" ");
        if (splitted.length==1){
            return new EqElement(splitted[0]);
        }
        else{
            return new EqElement(Integer.parseInt(splitted[0]),splitted[1]);
        }
    }
}
