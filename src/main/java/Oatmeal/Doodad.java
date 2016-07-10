/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import java.util.Objects;

/**
 *
 * @author Brenton
 */
public class Doodad {

    public final int xMax;
    public final int yMax;
    public final int xMin;
    public final int yMin;
    public final Couple center;

    public Doodad(int xMax, int yMax, int xMin, int yMin) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.xMin = xMin;
        this.yMin = yMin;
        this.center = new Couple(xMax - ((xMax - xMin) / 2) , yMax - ((yMax - yMin) / 2));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.xMax;
        hash = 59 * hash + this.yMax;
        hash = 59 * hash + this.xMin;
        hash = 59 * hash + this.yMin;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Doodad other = (Doodad) obj;
        if (!Objects.equals(this.center, other.center)) {
            return false;
        }
        return true;
    }

    
    
    

}
