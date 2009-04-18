package pangea.kegg;

import pangea.kegg.types.EqElement;
import pangea.kegg.types.Equation;

import java.util.regex.*;
import java.rmi.RemoteException;

import keggapi.KEGGPortType;
import keggapi.KEGGLocator;

import javax.xml.rpc.ServiceException;

/**
 * Created by IntelliJ IDEA.
 * User: fdenes
 * Date: Apr 15, 2009
 * Time: 1:22:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Retriever {

    public static String getFromKegg(String element) throws RemoteException, ServiceException {
        KEGGLocator locator = new KEGGLocator();
        KEGGPortType port = locator.getKEGGPort();

        return port.bget(element);
    }

    public static String equationRetriever(String data) throws Exception{
        Pattern pattern = Pattern.compile("EQUATION[\\s]*([^\\r\\n]*)");
        Matcher matcher = pattern.matcher(data);

        return matcher.find()?matcher.group(1):null;
    }

    public static Equation getEquation(String id, String eq) throws Exception{

        int type = Equation.TYPE_REVERSIBLE;

        String[] parts = eq.split("<=>");
        if (parts.length!=2){
            parts = eq.split("=>");
            type = Equation.TYPE_IRREVERSIBLE;
        }

        String[] fhalf = parts[0].split ("\\+");
        String[] shalf = parts[1].split ("\\+");

        EqElement[] felements = new EqElement[fhalf.length];
        EqElement[] selements = new EqElement[shalf.length];

        for (int i = 0; i < fhalf.length; i++) felements[i] = EqElement.getElementFromString(fhalf[i]);
        for (int i = 0; i < shalf.length; i++) selements[i] = EqElement.getElementFromString(shalf[i]);

        return new Equation(id, felements,selements,type);
    }
}
