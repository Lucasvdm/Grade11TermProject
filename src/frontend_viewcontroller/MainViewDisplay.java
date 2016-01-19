package frontend_viewcontroller;

import backend_models.*;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * This class is responsible for displaying the data from the backend, and
 * responsibilities include: 1) Construct graphical user interface (GUI) on the
 * screen 2) Pull data from the backend to display in the GUI
 *
 * There should be no code here for handling user's keyboard or mouse
 * interaction! That belongs in the ModelsAndViewsController.
 *
 * @author ckcheng
 */
public class MainViewDisplay extends JFrame {

    /*
     * MainViewDisplay needs to have a instance variable to reference the
     * Backend's Models. Because the frontend's MainViewDisplay is responsible
     * for displaying data from the backend.
     */
    public BackendModelSetup theBackendModel;

    /*
     *
     * GUI widgets to be added to the MainViewDisplay. The user will see data
     * and can later interact with these widgets.
     */
    public JLabel scratchpadLabel;
    public JPanel scratchpadPanel;
    public JLabel comparisonLabel;
    public JPanel comparisonPanel;
    public JComboBox colourList;
    public JLabel inkColour;
    public JButton openSignatureButton;
    public JButton compareSignatureButton;
    public JButton saveSignatureButton;
    public JButton clearButton;
    public JButton closeButton;
    public JLabel decision;

    /**
     *
     * Constructor. Probably nothing for students to change.
     */
    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    /**
     *
     *
     */
    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //this.setMinimumSize(new Dimension(600, 200));

        /*
         * Create GUI widget components and add into pane
         */
        this.scratchpadLabel = new JLabel();
        this.scratchpadLabel.setIcon(new ImageIcon(Scratchpad.scratchpad));

        this.scratchpadPanel = new JPanel();
        this.scratchpadPanel.add(scratchpadLabel);
        
        this.comparisonLabel = new JLabel();
        
        this.comparisonPanel = new JPanel();
        this.comparisonPanel.add(comparisonLabel);

        String[] colours = {"Black", "Blue", "Red", "Green", "ERASER"};
        this.colourList = new JComboBox(colours);
        this.colourList.setSelectedIndex(0);
        
        this.inkColour = new JLabel();
        this.inkColour.setText("Colour:");

        this.openSignatureButton = new JButton();
        this.openSignatureButton.setText("Open");

        this.saveSignatureButton = new JButton();
        this.saveSignatureButton.setText("Save");

        this.compareSignatureButton = new JButton();
        this.compareSignatureButton.setText("Compare");

        this.clearButton = new JButton();
        this.clearButton.setText("Clear");
        
        this.closeButton = new JButton();
        this.closeButton.setText("Close");
        
        this.decision = new JLabel();
        this.decision.setText("");

        /*
         * choose your LayoutManager. See:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
         *
         * I suggest GridBagLayout. See:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
         */
        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());
        GridBagConstraints c;

        /*
         * Specify GridBagConstraints in c and add GUI component to pane
         */
        c = new GridBagConstraints(); // you should construct a new GridBagConstraints each time you use it, to avoid subtle bugs...
        //c.fill = GridBagConstraints.BOTH;
        //c.ipady = 0;
        //c.weightx = 0;
        //c.weighty = 0;
        //c.anchor = GridBagConstraints.CENTER;
        //c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 8;
        c.gridheight = 11;
        mainDisplayPane.add(this.scratchpadPanel, c);
        
        c = new GridBagConstraints();
        c.gridx = 8;
        c.gridy = 0;
        c.gridheight = 11;
        mainDisplayPane.add(this.comparisonPanel, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 12;
        mainDisplayPane.add(this.openSignatureButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 12;
        mainDisplayPane.add(this.saveSignatureButton, c);

        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 12;
        mainDisplayPane.add(this.clearButton, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 12;
        mainDisplayPane.add(this.compareSignatureButton, c);

        c = new GridBagConstraints();
        c.gridx = 9;
        c.gridy = 6;
        mainDisplayPane.add(this.colourList, c);
        
        c = new GridBagConstraints();
        c.gridx = 9;
        c.gridy = 5;
        c.ipady = 20;
        mainDisplayPane.add(this.inkColour, c);
        
        c = new GridBagConstraints();
        c.gridx = 9;
        c.gridy = 12;
        mainDisplayPane.add(this.closeButton, c);
        
        c = new GridBagConstraints();
        c.gridx = 8;
        c.gridy = 12;
        mainDisplayPane.add(this.decision, c);
        
        this.decision.setVisible(false);
        this.closeButton.setVisible(false);
        
        this.pack();
    }

    /*
     *
     *
     * methods for Controlling the Frontend's View
     */
    public void updateScratchpad() {
        this.scratchpadLabel.setIcon(new ImageIcon(Scratchpad.scratchpad));
        this.scratchpadPanel.add(scratchpadLabel);
    }

    public String showSaveDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }

        return null;
    }

    public String showOpenDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }

        return null;
    }
}
