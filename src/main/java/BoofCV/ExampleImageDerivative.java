/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BoofCV;

import boofcv.abst.filter.derivative.AnyImageDerivative;
import boofcv.alg.filter.derivative.DerivativeType;
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.core.image.border.BorderType;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayF32;
import java.awt.image.BufferedImage;

/**
 *
 * @author Brenton
 */
public class ExampleImageDerivative {
    public static void main(String[] args) {
		BufferedImage input = UtilImageIO.loadImage(UtilIO.pathExample("C:/Users/Brenton/Desktop/CS499/screenShots/BW5.png"));
 
		// We will use floating point images here, but GrayU8 with GrayS16 for derivatives also works
		GrayF32 grey = new GrayF32(input.getWidth(),input.getHeight());
		ConvertBufferedImage.convertFrom(input, grey);
 
		// First order derivative, also known as the gradient
		GrayF32 derivX = new GrayF32(grey.width,grey.height);
		GrayF32 derivY = new GrayF32(grey.width,grey.height);
 
		GImageDerivativeOps.gradient(DerivativeType.SOBEL, grey, derivX, derivY, BorderType.EXTENDED);
 
		// Second order derivative, also known as the Hessian
		GrayF32 derivXX = new GrayF32(grey.width,grey.height);
		GrayF32 derivXY = new GrayF32(grey.width,grey.height);
		GrayF32 derivYY = new GrayF32(grey.width,grey.height);
 
		GImageDerivativeOps.hessian(DerivativeType.SOBEL, derivX, derivY, derivXX, derivXY, derivYY, BorderType.EXTENDED);
 
		// There's also a built in function for computing arbitrary derivatives
		AnyImageDerivative<GrayF32,GrayF32> derivative =
				GImageDerivativeOps.createAnyDerivatives(DerivativeType.SOBEL, GrayF32.class, GrayF32.class);
 
		// the boolean sequence indicates if its an X or Y derivative
		derivative.setInput(grey);
		GrayF32 derivXYX = derivative.getDerivative(true, false, true);
 
		// Visualize the results
		ListDisplayPanel gui = new ListDisplayPanel();
		gui.addImage(ConvertBufferedImage.convertTo(grey,null),"Input Grey");
		gui.addImage(VisualizeImageData.colorizeSign(derivX, null, -1),"Sobel X");
		gui.addImage(VisualizeImageData.colorizeSign(derivY, null, -1),"Sobel Y");
		// Use colors to show X and Y derivatives in one image.  Looks pretty.
		gui.addImage(VisualizeImageData.colorizeGradient(derivX, derivY, -1),"Sobel X and Y");
		gui.addImage(VisualizeImageData.colorizeSign(derivXX, null,-1),"Sobel XX");
		gui.addImage(VisualizeImageData.colorizeSign(derivXY, null,-1),"Sobel XY");
		gui.addImage(VisualizeImageData.colorizeSign(derivYY, null,-1),"Sobel YY");
		gui.addImage(VisualizeImageData.colorizeSign(derivXYX, null,-1),"Sobel XYX");
 
		ShowImages.showWindow(gui,"Image Derivatives",true);
	}
}
