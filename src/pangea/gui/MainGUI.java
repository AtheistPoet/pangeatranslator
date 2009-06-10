package pangea.gui;

import pangea.config.Loader;
import pangea.config.utils.Plugin;
import pangea.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Author: Francesco De Nes
 * C: 2009
 */
public class MainGUI extends JFrame {

    private static Log log;


    private JTextField txtInput;
    private JTextField txtOutput;
    private static JTextArea logArea;
    private ButtonGroup pluginGroup;

    private Hashtable<String,String> pluginValue;

    public MainGUI() throws Exception {

        log = new Log(); 

        pluginValue = new Hashtable<String, String>();

        this.setLayout(new GridLayout(3,1));

        this.setJMenuBar(setupMenuBar());
        this.add(setupTextFieldsPanel());
        this.add(setupButtonsPanel());
        this.add(setupLogAreaPanel());


        this.setSize(600,450);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void newMessage(String message) {
        if (message!=null){
            logArea.append(message + "\n");
        }
    }

    public void setInputTextField (String text) {
        if (text!=null) txtInput.setText(text);
    }

    public void setOutputTextField (String text) {
        if (text!=null) txtOutput.setText(text);
    }

    public String getInputTextField () {
        return txtInput.getText();
    }

    public String getOutputTextField () {
        return txtOutput.getText();
    }

    public String getSelectedPlugin() {
        Enumeration<AbstractButton> e = pluginGroup.getElements();

        while (e.hasMoreElements()){
            AbstractButton b = e.nextElement();
            if (b.isSelected()) return pluginValue.get(b.getText());
        }
        return null;
    }


    private JMenuBar setupMenuBar() {
        JMenuBar menu = new JMenuBar();
        menu.add(setupPluginsMenu());
        return menu;
    }

    private JMenu setupPluginsMenu() {
        JMenu plugin = new JMenu("Plugin");
        String[] options = Loader.getOptionsList();
        if (options!=null) {
            pluginGroup = new ButtonGroup();
            boolean first = true;
            for (String option:options) {
                Plugin p = Loader.getPlugin(option);
                JMenuItem item = new JRadioButtonMenuItem(p.getName());
                if (first) {
                    item.setSelected(true);
                    first = false;
                }
                plugin.add(item);
                pluginGroup.add(item);
                pluginValue.put(p.getName(),option);
            }
        }
        return plugin;
    }

    private JPanel setupTextFieldsPanel() throws Exception{
        JPanel panel = new JPanel(new GridBagLayout());
        JButton btnIn, btnOut;
        btnIn = new JButton("...");
        btnOut = new JButton("...");
        btnIn.addActionListener(new MainGUIListener(MainGUIListener.INPUT_TEXTFIELD, this));
        btnOut.addActionListener(new MainGUIListener(MainGUIListener.OUTPUT_TEXTFIELD, this));
        txtInput = new JTextField(30);
        txtOutput = new JTextField(30);

        GridBagConstraints c = new GridBagConstraints();

        //(etichetta, casella di testo, pulsante) X2
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=0;
        panel.add(new JLabel("Input:"),c);

        c.gridwidth=6;
        c.gridx=4;
        c.gridy=0;
        panel.add(txtInput,c);

        c.gridwidth=1;
        c.gridx=10;
        c.gridy=0;
        panel.add(btnIn,c);

        c.gridwidth=3;
        c.gridx=0;
        c.gridy=1;
        panel.add(new JLabel("Output:"),c);

        c.gridwidth=6;
        c.gridx=4;
        c.gridy=1;
        panel.add(txtOutput,c);

        c.gridwidth=1;
        c.gridx=10;
        c.gridy=1;
        panel.add(btnOut,c);

        return panel;
    }

    private JPanel setupButtonsPanel() throws Exception{
        JPanel panel = new JPanel(new FlowLayout());
        JButton run = new JButton("Esegui");
        run.addActionListener(new MainGUIListener(MainGUIListener.RUN_TRANSFORMATION, this));
        panel.add(run);
        return panel;
    }

    private JScrollPane setupLogAreaPanel() {
        logArea = new JTextArea();
        JScrollPane pane = new JScrollPane(logArea);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return pane;
    }
}
