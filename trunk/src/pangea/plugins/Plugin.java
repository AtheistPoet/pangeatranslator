package pangea.plugins;

import javax.swing.*;
import java.util.Hashtable;

/**
 * Author: Francesco De Nes
 * Date: 26-may-2009
 */
public interface Plugin {
    public void pre(Hashtable params, String input, String output);
    public void post(Hashtable params, String input, String output);
    public boolean transform();
}
