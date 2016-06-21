/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import javafx.util.Pair;

/**
 *
 * @author Brenton
 */
public class Doodad {

    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    private Pair center;

    public Doodad(int xMax, int yMax, int xMin, int yMin) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.xMin = xMin;
        this.yMin = yMin;
        this.center = new Pair(Math.floor(xMax - xMin), Math.floor(yMax - yMin));
    }
    
    
    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }

    public int getxMin() {
        return xMin;
    }

    public int getyMin() {
        return yMin;
    }

    public Pair getCenter() {
        return center;
    }
}
