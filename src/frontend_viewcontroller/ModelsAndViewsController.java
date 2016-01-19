package frontend_viewcontroller;

import backend_models.*;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author ckcheng
 */
public class ModelsAndViewsController {

    public BackendModelSetup theBackendModel;
    public MainViewDisplay theMainViewDisplay;

    /*
     * Step 1 of 2 for writing user actions.
     * -------------------------------------
     *
     * User actions are written as private inner classes that implement
     * ActionListener, and override the actionPerformed method.
     *
     * Use the following as a template for writting more user actions.
     */
    private class OpenSignatureAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                String pathToFile = theMainViewDisplay.showOpenDialog();
                if (pathToFile == null) {
                    return;
                }
                theBackendModel.theScratchpad.openSignature(pathToFile);
                if (Scratchpad.comparisonOpen == true) {
                    theMainViewDisplay.comparisonLabel.setIcon(new ImageIcon(Scratchpad.scratchpad));
                    theMainViewDisplay.updateScratchpad();
                } else {
                    theMainViewDisplay.comparisonLabel.setIcon(new ImageIcon(Scratchpad.comparison));
                }
                theMainViewDisplay.comparisonLabel.setIcon(new ImageIcon(Scratchpad.comparison));
                theMainViewDisplay.closeButton.setVisible(true);
                Scratchpad.comparisonOpen = true;
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private class CloseSignatureAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            theMainViewDisplay.comparisonLabel.setIcon(null);
            theMainViewDisplay.closeButton.setVisible(false);
            theMainViewDisplay.decision.setVisible(false);
            Scratchpad.comparisonOpen = false;
        }
    }

    private class SaveSignatureAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                String pathToFile = theMainViewDisplay.showSaveDialog();
                if (pathToFile == null) {
                    return;
                }
                theBackendModel.theScratchpad.saveSignature(new File(pathToFile + ".png"));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private class ClearAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int width = Scratchpad.scratchpad.getWidth();
            int height = Scratchpad.scratchpad.getHeight();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Scratchpad.scratchpad.setRGB(x, y, 16777215);
                }
            }
            theMainViewDisplay.updateScratchpad();
        }
    }

    private class CompareSignatureAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(Scratchpad.comparisonOpen == true){
            theBackendModel.theScratchpad.compareSignatures();
            if (Scratchpad.decision == true) {
                theMainViewDisplay.decision.setText("Match!");
                theMainViewDisplay.decision.setVisible(true);
            }
            if (Scratchpad.decision == false) {
                theMainViewDisplay.decision.setText("No match!");
                theMainViewDisplay.decision.setVisible(true);
            }
            }else{
                JOptionPane.showMessageDialog(theMainViewDisplay, "You must open a comparison image to compare signatures.");
            }
        }
    }

    private class ColourListReader implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JComboBox cb = (JComboBox) ae.getSource();
            String colour = (String) cb.getSelectedItem();
            theBackendModel.theScratchpad.colour = colour;
        }
    }

    private class MouseAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            mouseGetPoint(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseGetPoint(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseGetPoint(e);
        }

        private void mouseGetPoint(MouseEvent e) {
            Point p = e.getPoint();
            theBackendModel.theScratchpad.drawSignature(p.x, p.y);
            theMainViewDisplay.updateScratchpad();
        }
    }

    /*
     * constructor used for wiring user actions to GUI elements
     */
    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;

        /*
         * Step 2 of 2 for writing user actions.
         * -------------------------------------
         *
         * Once a private inner class has been written for a user action, you
         * can wire it to a GUI element (i.e. one of GUI elements you drew in
         * the DrawnView class and which you gave a memorable public variable
         * name!).
         *
         * Use the following as a template for wiring more user actions.
         */
//        getPowButton.addActionListener(new CopyUserText());

        this.theMainViewDisplay.openSignatureButton.addActionListener(new ModelsAndViewsController.OpenSignatureAction());
        this.theMainViewDisplay.saveSignatureButton.addActionListener(new ModelsAndViewsController.SaveSignatureAction());
        this.theMainViewDisplay.clearButton.addActionListener(new ModelsAndViewsController.ClearAction());
        this.theMainViewDisplay.colourList.addActionListener(new ModelsAndViewsController.ColourListReader());
        this.theMainViewDisplay.compareSignatureButton.addActionListener(new ModelsAndViewsController.CompareSignatureAction());
        this.theMainViewDisplay.closeButton.addActionListener(new ModelsAndViewsController.CloseSignatureAction());
        this.theMainViewDisplay.scratchpadLabel.addMouseMotionListener(new MouseAction());
        this.theMainViewDisplay.scratchpadLabel.addMouseListener(new MouseAction());

    }
}
