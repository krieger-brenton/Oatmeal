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

/**
 *
 * @author Brenton
 */
public class Execute {

    public static void main(String[] args) throws AWTException {
        GameControls gc = new GameControls();
        ImageControls ic = new ImageControls();
        BufferedImage image;

        //image = ic.takeScreenShot();
        image = ic.loadBufferedImage("C:/Users/Brenton/Desktop/CS499/screenShots/screenshot_full1.png");
        image = ic.cropScreenShot(image, 0, 150, (int) ic.screenSize.getWidth(), (int) ic.screenSize.getHeight() - 238);
        ic.filterBackground(image);
        //image = ic.blur(image, 2);
        //ic.saveScreenShot("blured0", image);
//        ic.saveScreenShot("cropped0", image);
        ic.grayscale(image);
//        ic.saveScreenShot("grayscaled0", image);
        //ic.edgeDetection(image);
        ic.detectEdges(image, "test1");
    }
}
