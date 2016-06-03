/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Brenton
 */
public class ImageControls {

    public ImageControls() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.getWidth();
        this.height = screenSize.getHeight();
    }

    private final Dimension screenSize;
    private final double width;
    private final double height;

    private final int RED = 16711680;
    private final int YELLOW = 16776960;
    private final int BLACK = -16777216;
    private final int WHITE = -1;
    private final int SKY = -7171841;
    private final int CLOUD = -10178305;
    private final int HILL = -15887616;
    private final int BUSH = -7809024;

    public BufferedImage takeScreenShot() throws AWTException {
        Rectangle r = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage bufferedImage;
        bufferedImage = new Robot().createScreenCapture(r);
        return bufferedImage;
    }

    public void saveScreenShot(String name, BufferedImage image) {
        try {
            // Save as PNG
            File file = new File("C:/Users/Brenton/Desktop/CS499/screenShots/" + name + ".png");
            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            System.out.println("Could not save full screenshot " + e.getMessage());
        }
    }

    //http://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    //reference ^^^^
    public void pixelManipTest(BufferedImage bi) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        BufferedImage save = deepCopy(bi);
        int partitions = 6;
        int delta = (int) Math.floor(255 / partitions);
        int range = delta;

        Color target = new Color(255, 255, 255); // Color white
        int targetRGB = target.getRGB();

        Color myBlack = new Color(0, 0, 0); // Color black
        int RGB = myBlack.getRGB();

        for (int x = 0; x < partitions; x++) {

            int midRGB = new Color(range, range, range).getRGB();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (bi.getRGB(i, j) < midRGB) {
                        bi.setRGB(i, j, targetRGB);
                    } else {
                        bi.setRGB(i, j, RGB);
                    }
                }
            }
            saveScreenShot("BW" + x, bi);
            bi = deepCopy(save);
            range += delta;
        }

        //Convert to grayscale
//        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
//                op.filter(bi, bi);
        // Replace colors
//        int goalRGB = new Color(220, 20, 60).getRGB(); // Color Crimson
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if(bi.getRGB(i,j) == targetRGB)
//                bi.setRGB(i, j, goalRGB);
//            }
//        }
        // Colors the top left quarter of the screen black, for fun.
//        for (int i = 0; i < width/2; i++) {
//            for (int j = 0; j < height/2; j++) {
//                bi.setRGB(i, j, RGB);
//            }
//        }
    }

    public void filterBackground(BufferedImage bi) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int currentPixel = bi.getRGB(i, j);
                switch (currentPixel) {
                    case SKY:
                    case CLOUD:
                    case BLACK:
                    case HILL:
                    case BUSH:
                        bi.setRGB(i, j, WHITE);
                        break;
                }
            }
        }
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(bi, bi);
    }

    public BufferedImage loadBufferedImage(String path) {
        try {
            File img = new File(path);
            BufferedImage image = ImageIO.read(img);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edgeDetection(BufferedImage bi) {

        BufferedImage save = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        int delta = 100000;
        double x = width - 1;
        double y = height - 1;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int currentPixel = bi.getRGB(i, j);
                int nextPixel = bi.getRGB((i + 1), j);
                int lowerPixel = bi.getRGB(i, (j + 1));
                int rightDifference = Math.abs(currentPixel - nextPixel);
                int bottomDifference = Math.abs(currentPixel - lowerPixel);
                if (rightDifference > delta) {
                    save.setRGB(i, j, RED);
                }
                if (bottomDifference > delta) {
                    save.setRGB(i, j, YELLOW);
                }
            }
        }
        saveScreenShot("edges0", save);

    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
