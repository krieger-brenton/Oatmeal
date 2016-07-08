/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.util.Pair;
import javax.imageio.ImageIO;
//import Oatmeal.Doodad;

/**
 *
 * @author Brenton
 */
public class ImageControls {

    public ImageControls() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth();
        this.screenHeight = screenSize.getHeight();
    }

    final Dimension screenSize;
    final double screenWidth;
    final double screenHeight;

    private final int RED = 16711680;
    private final int YELLOW = 16776960;
    private final int BLACK = -16777216;
    private final int GREEN = -15887616;
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

    /*
    http://stackoverflow.com/questions/2386064/how-do-i-crop-an-image-in-java
     */
    public BufferedImage cropScreenShot(BufferedImage bi, int startX, int startY, int endX, int endY) {
        BufferedImage img = bi.getSubimage(startX, startY, endX, endY);
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage;
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
        int x = bi.getWidth();
        int y = bi.getHeight();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int currentPixel = bi.getRGB(i, j);
                switch (currentPixel) {
                    case SKY:
                    case CLOUD:
                    case HILL:
                    case BUSH:
                    case BLACK:
                        bi.setRGB(i, j, WHITE);
                        break;
                }
            }
        }
    }

    public void grayscale(BufferedImage bi) {
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

    public List derive(BufferedImage bi) {
        List derive = new ArrayList();
        int x = bi.getWidth() - 3;
        int y = bi.getHeight() - 1;
        int delta = 3000000;
        int inc = 1;

        for (int i = 0; i < x; i += inc) {
            for (int j = 0; j < y; j += inc) {
                int currentPixel = bi.getRGB(i, j);
                int nextPixel = bi.getRGB((i + 1), j);
                int lowerPixel = bi.getRGB(i, (j + 1));
                int rightDifference = Math.abs(currentPixel - nextPixel);
                int bottomDifference = Math.abs(currentPixel - lowerPixel);
                if (rightDifference > delta) {
                    derive.add(new Couple(i, j));
                }
                if (bottomDifference > delta) {
                    derive.add(new Couple(i, j));
                }
            }
        }
        return derive;
    }

    public List<List<Couple>> sortPoints(List points, int delta) {
        List<List<Couple>> pointGrid = new ArrayList<>();
        if (points.isEmpty()) {
            return null;
        }
        pointGrid.add(new ArrayList() {
            {
                add(points.get(0));
            }
        });
//        int lastX = 0;
        for (Object couple : points) {
            Couple c = (Couple) couple;
            boolean placed = false;
            int size = pointGrid.size();
            
            for (int i = 0; i < size; i++) {
                if (!placed) {
                    ArrayList al = (ArrayList) pointGrid.get(i);
                    Couple c2 = (Couple) al.get(0);
                    if (Math.abs(c2.x - c.x) < delta && Math.abs(c2.y - c.y) < delta) {
                        al.add(c);
                        placed = true;
                    }
                }
            }

            if (!placed) {
                pointGrid.add(new ArrayList() {
                    {
                        add(c);
                    }
                });
                placed = true;
            }
//            lastX = c.x;
        }
        return pointGrid;
    }

    public List createDoodads(List<List<Couple>> pointGrid) {
        if (pointGrid == null) {
            return null;
        }
        List doodads = new ArrayList();
        int xMax, xMin, yMax, yMin;
        xMax = 0;
        xMin = Integer.MAX_VALUE;
        yMax = 0;
        yMin = Integer.MAX_VALUE;
        for (Object list : pointGrid) {
            ArrayList al = (ArrayList) list;

            for (Object couple : al) {
                Couple c = (Couple) couple;
                xMax = Math.max(xMax, c.x);
                xMin = Math.min(xMin, c.x);
                yMax = Math.max(yMax, c.y);
                yMin = Math.min(yMin, c.y);
            }
            doodads.add(new Doodad(xMax, yMax, xMin, yMin));
            xMax = 0;
            xMin = Integer.MAX_VALUE;
            yMax = 0;
            yMin = Integer.MAX_VALUE;
        }
        return doodads;
    }

    public void drawDoodads(List doodads, String name, int height, int width) {
        if (doodads == null) {
            return;
        }
        BufferedImage save = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (Object doodad : doodads) {
            Doodad d = (Doodad) doodad;
            for (int i = d.xMin; i < d.xMax; i++) {
                save.setRGB(i, d.yMax, WHITE);
                save.setRGB(i, d.yMin, WHITE);
            }
            for (int i = d.yMin; i < d.yMax; i++) {
                save.setRGB(d.xMax, i, WHITE);
                save.setRGB(d.xMin, i, WHITE);
            }
        }
        saveScreenShot(name, save);
    }

    public List detectEdges(BufferedImage bi, String name, int delta) {
        int height = bi.getHeight();
        int width = bi.getWidth();
        List points = derive(bi);
        List doodads = createDoodads(sortPoints(points, delta));
//        drawDoodads(doodads, name, height, width);
        return doodads;
    }

    public void edgeDetection(BufferedImage bi) {
        int x = bi.getWidth() - 50;
        int y = bi.getHeight() - 50;
        int objectDelta = 50;
        List Derive = new ArrayList();
        List doodads = new ArrayList();
        List<Pair>[] DeriveY = (ArrayList<Pair>[]) new ArrayList[Math.floorDiv(bi.getHeight(), objectDelta)];
        BufferedImage save = new BufferedImage(x + 50, y + 50, BufferedImage.TYPE_INT_RGB);
        BufferedImage save1 = new BufferedImage(x + 50, y + 50, BufferedImage.TYPE_INT_RGB);
        int delta = 3000000;

        for (int i = 50; i < x; i++) {
            for (int j = 50; j < y; j++) {
                int currentPixel = bi.getRGB(i, j);
                int nextPixel = bi.getRGB((i + 1), j);
                int lowerPixel = bi.getRGB(i, (j + 1));
                int rightDifference = Math.abs(currentPixel - nextPixel);
                int bottomDifference = Math.abs(currentPixel - lowerPixel);
                if (rightDifference > delta) {
                    save1.setRGB(i, j, RED);
                    Derive.add(new Pair(i, j));
                }
                if (bottomDifference > delta) {
                    save1.setRGB(i, j, YELLOW);
                    Derive.add(new Pair(i, j));
                }
            }
        }
        int xMax = 0, xMin = Integer.MAX_VALUE, yMax = 0, yMin = Integer.MAX_VALUE;
        int lastX = 0;
        int lastY = 0;

        for (Object pair : Derive) {
            Pair p = (Pair) pair;
            int xValue = (int) p.getKey();
            int yValue = (int) p.getValue();

            if (Math.abs(xValue - lastX) > objectDelta) {
//                System.out.println("x");
                doodads.add(new Doodad(xMax, yMax, xMin, yMin));
                xMax = 0;
                xMin = Integer.MAX_VALUE;
                yMax = 0;
                yMin = Integer.MAX_VALUE;
            }
            if (Math.abs(yValue - lastY) > objectDelta) {
//                System.out.println("y");
                int index = Math.floorDiv(yValue, objectDelta);
                if (DeriveY[index] == null) {
                    DeriveY[index] = new ArrayList();
                }
                DeriveY[index].add(p);
                lastX = xValue;
                lastY = yValue;
                continue;
            }
            xMax = Math.max(xMax, xValue);
            xMin = Math.min(xMin, xValue);
            yMax = Math.max(yMax, yValue);
            yMin = Math.min(yMin, yValue);

            lastX = xValue;
            lastY = yValue;
        }
        doodads.add(new Doodad(xMax, yMax, xMin, yMin));
        doodads.remove(0);

        for (Object dd : doodads) {
            Doodad d = (Doodad) dd;
            xMax = d.xMax;
            xMin = d.xMin;
            yMax = d.yMax;
            yMin = d.yMin;
            System.out.println("Xmax: " + xMax + " Xmin: " + xMin + " Ymax: " + yMax + " Ymin: " + yMin);
            for (int i = xMin; i < xMax; i++) {
                save.setRGB(i, yMax, WHITE);
                save.setRGB(i, yMin, WHITE);
            }
            for (int i = yMin; i < yMax; i++) {
                save.setRGB(xMax, i, WHITE);
                save.setRGB(xMin, i, WHITE);
            }
        }
//        saveScreenShot("edges0", save);
        saveScreenShot("edges1", save1);

    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public BufferedImage blur(BufferedImage bi, int matrixSize) {
        int kSquared = matrixSize * matrixSize;
        float[] data = new float[kSquared];
        for (int i = 0; i < kSquared; i++) {
            data[i] = 1.0f / kSquared;
        }
        Kernel kernel = new Kernel(matrixSize, matrixSize, data);
        ConvolveOp convolve = new ConvolveOp(kernel);
        bi = convolve.filter(bi, null);
        return bi;
    }
}
