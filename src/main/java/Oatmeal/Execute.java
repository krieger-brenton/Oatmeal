/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Brenton
 */
public class Execute {

    public static void main(String[] args) throws AWTException {
        GameLogic gl = new GameLogic();
        ImageControls ic = new ImageControls();
        BufferedImage image;
        
        int[] pixel = new int[4];
        int[] pixel2 = new int[4];
//        long start = 0, end = 0, total = 0;
//        int iterations = 10;
//        for (int i = 0; i < iterations; i++) {
//            start = System.currentTimeMillis();
        gl.start();
        while (true) {

            image = ic.takeScreenShot();
//        image = ic.loadBufferedImage("C:/Users/Brenton/Desktop/CS499/screenShots/screenshot_full22.png");
            image = ic.cropScreenShot(image, 750, 350, image.getWidth() - 1000, image.getHeight() - 440);
            
            pixel[0] = image.getRGB(65, 220);
            pixel[1] = image.getRGB(68, 220);
            pixel[2] = image.getRGB(71, 220);
            pixel[3] = image.getRGB(74, 220);
            
            pixel2[0] = image.getRGB(5, 240);
            pixel2[1] = image.getRGB(10, 240);
            pixel2[2] = image.getRGB(15, 240);
            pixel2[3] = image.getRGB(20, 240);
//            System.out.println(pixel2[0]);
//            System.out.println(pixel2[1]);
//            System.out.println(pixel2[2]);
//            System.out.println(pixel2[3]);
//            System.out.println("pix" + pixel);
//            System.out.println("pix2" + pixel2);
//            System.out.println(pixel);
//            ic.saveScreenShot("edgesA", image);
            ic.filterBackground(image);
            ic.grayscale(image);
            List doodads = ic.detectEdges(image, "edgesB", 60);
            gl.run(doodads, pixel, pixel2);
//            end = System.currentTimeMillis();
//            total += end - start;
        }
//        System.out.println("Mean time with " + iterations + " iterations: " + (total / iterations) / 1000.f);
    }
}
