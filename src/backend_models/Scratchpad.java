package backend_models;

import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class Scratchpad {

    public static boolean decision;
    public static BufferedImage scratchpad;
    public static BufferedImage comparison;
    public static boolean comparisonOpen = false;
    public String colour = "Black";

    public Scratchpad(String path) throws IOException {
        URL url = new File(path).toURI().toURL();
        scratchpad = ImageIO.read(url);
    }

    public void drawSignature(int x, int y) {
        switch (colour) {
            case "Black":
                for (int w = -1; w < 2; w++) {
                    for (int h = -1; h < 2; h++) {
                        scratchpad.setRGB(x + w, y + h, 0);
                    }
                }
                break;
            case "Blue":
                for (int w = -1; w < 2; w++) {
                    for (int h = -1; h < 2; h++) {
                        scratchpad.setRGB(x + w, y + h, 255);
                    }
                }
                break;
            case "Red":
                for (int w = -1; w < 2; w++) {
                    for (int h = -1; h < 2; h++) {
                        scratchpad.setRGB(x + w, y + h, 16718105);
                    }
                }
                break;
            case "Green":
                for (int w = -1; w < 2; w++) {
                    for (int h = -1; h < 2; h++) {
                        scratchpad.setRGB(x + w, y + h, 57600);
                    }
                }
                break;
            case "ERASER":
                for (int w = -2; w < 3; w++) {
                    for (int h = -2; h < 3; h++) {
                        scratchpad.setRGB(x + w, y + h, 16777215);
                    }
                }
                break;
        }
    }

    public void openSignature(String path) throws IOException {
        URL url = new File(path).toURI().toURL();
        if (comparisonOpen == true) {
            scratchpad = ImageIO.read(url);
        } else {
            comparison = ImageIO.read(url);
        }
    }

    public void saveSignature(File filePath) throws IOException {
        ImageIO.write(scratchpad, "png", filePath);
    }

    /*
      Starts the comparison process - in this method it calls the cropSignature method, then
      it takes the cropped signatures and determines the greatest common factor of the horizontal
      component of the drawn and saved signatures, as well as the vertical component, essentially
      splitting the two signatures into an even grid.  Then it takes the values for the numbers of 
      rows and columns and passes them to the next compareSignatures method.
    */
    public void compareSignatures() {
        int[] croppedDrawn = AnalyzeSignatures.cropSignature(Scratchpad.scratchpad);
        int[] croppedSaved = AnalyzeSignatures.cropSignature(Scratchpad.comparison);
        int rows = 1;
        int columns = 1;
        int counter = 0; //If loop runs through three times, subtract the croppedDrawn or croppedSaved
        while (rows == 1) {
            for (int i = 50; i >= 3; i--) {
                if (croppedDrawn[5] % i == 0 && croppedSaved[5] % i == 0) {
                    rows = i;
                }
            }
            if (rows == 1) {
                if (croppedDrawn[5] % 2 != 0) {
                    croppedDrawn[5] = croppedDrawn[5] - 1;
                } else if (croppedSaved[5] % 2 != 0) {
                    croppedSaved[5] = croppedSaved[5] - 1;
                }
            }
            if (counter > 3) {
                if (croppedDrawn[5] > croppedSaved[5]) {
                    croppedDrawn[5] = croppedDrawn[5] - 1;
                } else if (croppedSaved[5] > croppedDrawn[5]) {
                    croppedSaved[5] = croppedSaved[5] - 1;
                } else if (croppedDrawn[5] == croppedSaved[5]) {
                    croppedDrawn[5] -= 5;
                    croppedSaved[5] += 5;
                }
                counter = 0;
            }
            counter++;
        }
        counter = 0;
        while (columns == 1) {
            for (int i = 50; i >= 3; i--) {
                if (croppedDrawn[4] % i == 0 && croppedSaved[4] % i == 0) {
                    columns = i;
                }
            }
            if (columns == 1) {
                if (croppedDrawn[4] % 2 != 0) {
                    croppedDrawn[4] = croppedDrawn[4] - 1;
                } else if (croppedSaved[4] % 2 != 0) {
                    croppedSaved[4] = croppedSaved[4] - 1;
                }
            }
            if (counter > 3) {
                if (croppedDrawn[4] > croppedSaved[4]) {
                    croppedDrawn[4] = croppedDrawn[4] - 1;
                } else if (croppedSaved[4] > croppedDrawn[4]) {
                    croppedSaved[4] = croppedSaved[4] - 1;
                } else if (croppedDrawn[4] == croppedSaved[4]) {
                    croppedDrawn[4] -= 5;
                    croppedSaved[4] += 5;
                }
                counter = 0;
            }
            counter++;
        }
        decision = CompareSignatures.compareSignatures(rows, columns, croppedDrawn, croppedSaved);
    }
}
