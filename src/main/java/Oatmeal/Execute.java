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
        BufferedImage image = null;
        
//        image = ic.takeScreenShot();
        image = ic.loadBufferedImage("C:/Users/Brenton/Desktop/CS499/screenShots/screenshot_full1.png");
        ic.filterBackground(image);
        ic.edgeDetection(image);
        ic.saveScreenShot("filteredBackground1", image);
    }
    
}