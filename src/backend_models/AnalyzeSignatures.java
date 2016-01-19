package backend_models;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class AnalyzeSignatures {

    //Crops the image down to the borders of the signature by determining where the outermost
    //coloured pixels are and setting those as the new boundaries
    public static int[] cropSignature(BufferedImage img) {
        int[] sigSize = new int[6];
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        int width;
        int height;
        boolean white = true;
        for (int x = 0; x < img.getWidth() && white == true; x++) {
            for (int y = 0; y < img.getHeight() && white == true; y++) {
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                    left = x;
                    white = false;
                }
            }
        }
        white = true;
        for (int x = img.getWidth() - 1; x > 0 && white == true; x--) {
            for (int y = 0; y < img.getHeight() && white == true; y++) {
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                    right = x;
                    white = false;
                }
            }
        }
        white = true;
        for (int y = 0; y < img.getHeight() && white == true; y++) {
            for (int x = 0; x < img.getWidth() && white == true; x++) {
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                    top = y;
                    white = false;
                }
            }
        }
        white = true;
        for (int y = img.getHeight() - 1; y > 0 && white == true; y--) {
            for (int x = 0; x < img.getWidth() && white == true; x++) {
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                    bottom = y;
                    white = false;
                }
            }
        }
        if (white == true) {
            left = 0;
            top = 0;
            right = img.getWidth() - 1;
            bottom = img.getHeight() - 1;
        }
        height = bottom - top;
        width = right - left;
        sigSize[0] = left;
        sigSize[1] = top;
        sigSize[2] = right;
        sigSize[3] = bottom;
        sigSize[4] = width;
        sigSize[5] = height;
        return sigSize;
    }

    //Calculates how many coloured pixels each horizontal line in the "grid" crosses
    public static int[] calculateRows(BufferedImage img, int rows, int[] size) {
        int[] sigSize = size;
        int rowHeight = sigSize[5] / rows;
        int[] lines = new int[rows];
        int index = 0;
        for(int y = sigSize[3] - rowHeight; y > sigSize[1]; y -= rowHeight){
            int colouredPixels = 0;
            for(int x = sigSize[0]; x <= sigSize[2]; x++){
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                        colouredPixels++;
                }
            }
            lines[index] = colouredPixels / 3;
            index++;
        }
        return lines;
    }

    //Calculates how many coloured pixels each vertical line in the "grid" crosses
    public static int[] calculateColumns(BufferedImage img, int columns, int[] size) {
        int[] sigSize = size;
        int columnWidth = sigSize[4] / columns;
        int[] lines = new int[columns];
        int index = 0;
        for(int x = sigSize[2] - columnWidth; x > sigSize[0]; x -= columnWidth){
            int colouredPixels = 0;
            for(int y = sigSize[1]; y <= sigSize[3]; y++){
                Color c = new Color(img.getRGB(x, y));
                if (c.getRed() < 240 || c.getBlue() < 240 || c.getGreen() < 240) {
                        colouredPixels++;
                }
            }
            lines[index] = colouredPixels / 3;
            index++;
        }
        return lines;
    }

    //Calculates the ratio of the signature's width/height
    public static double calculateRatio(BufferedImage img, int[] size) {
        int[] sigSize = size;
        double ratioWidth = sigSize[2] - sigSize[0];
        double ratioHeight = sigSize[3] - sigSize[1];
        double ratio = ratioWidth/ratioHeight;
        return ratio;
    }
}
