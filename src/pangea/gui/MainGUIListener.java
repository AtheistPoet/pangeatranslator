package pangea.gui;

import pangea.Actions;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Author: Francesco De Nes
 * C: 2009
 */
public class MainGUIListener implements ActionListener {

    public static final int INPUT_TEXTFIELD = 0;
    public static final int OUTPUT_TEXTFIELD = 1;
    public static final int RUN_TRANSFORMATION = 2;
    public static final int PLUGIN_OPTION = 3;


    private static JFileChooser fChooser = new JFileChooser();

    private MainGUI parent;
    private int action;

    public MainGUIListener(int action, MainGUI parent) throws Exception{
        if (parent==null || (
                action!= INPUT_TEXTFIELD &&
                action != OUTPUT_TEXTFIELD &&
                action != RUN_TRANSFORMATION
        ))
            throw new Exception ("Azione sconosciuta.");
        this.parent = parent;
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (action) {
            case INPUT_TEXTFIELD:
                setInputTextField();
                break;
            case OUTPUT_TEXTFIELD:
                setOutputTextField();
                break;
            case RUN_TRANSFORMATION:
                runTransformation();
                break;
        }
    }


    private void setInputTextField(){
        int val = fChooser.showOpenDialog(parent);
        if (val == JFileChooser.APPROVE_OPTION){
            File file = fChooser.getSelectedFile();
            parent.setInputTextField(file.getAbsolutePath());
        }
    }

    private void setOutputTextField(){
        int val = fChooser.showOpenDialog(parent);
        if (val == JFileChooser.APPROVE_OPTION){
            File file = fChooser.getSelectedFile();
            parent.setOutputTextField(file.getAbsolutePath());
        }
    }

    private void runTransformation(){
        String res = Actions.run(parent.getSelectedPlugin(), parent.getInputTextField(), parent.getOutputTextField());
        MainGUI.newMessage(res);
    }
}
