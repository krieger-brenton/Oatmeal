/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import Robot.GameControls;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Brenton
 */
public class Execute {

    public static void main(String[] args) throws AWTException {
        GameLogic gl = new GameLogic();
        ImageControls ic = new ImageControls();
        BufferedImage image;
//        long start = 0, end = 0, total = 0;
//        int iterations = 10;
//        for (int i = 0; i < iterations; i++) {
//            start = System.currentTimeMillis();
        gl.start();
        while (true) {

            image = ic.takeScreenShot();
//        image = ic.loadBufferedImage("C:/Users/Brenton/Desktop/CS499/screenShots/screenshot_full11.png");
            image = ic.cropScreenShot(image, 710, 150, image.getWidth() - 710, image.getHeight() - 240);
//            ic.saveScreenShot("edgesA", image);
            ic.filterBackground(image);
            ic.grayscale(image);
            List doodads = ic.detectEdges(image, "edgesB", 50);
            gl.run(doodads);
//            end = System.currentTimeMillis();
//            total += end - start;
        }
//        System.out.println("Mean time with " + iterations + " iterations: " + (total / iterations) / 1000.f);
    }
}
