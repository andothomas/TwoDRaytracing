/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TwoDRaytracing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Wall {  
    
    boolean horizontal; // the slope of a wall is either 1 or undefined, denoted by true or false respectively
    
    int intercept; // the x or y intercept of the wall (depends on horizontal i.e. the slope)
    
    int red; // stores the red value for shading to simulate depth
    
    int x1, y1, x2, y2; // the coordinates of a given wall. results in two different points
   
    BufferedImage bimage; // the bitmap image input
    
    public Wall (boolean horizontal, int intercept, int red, int x1, int y1, int x2, int y2) {
        this.horizontal = horizontal;
        this.intercept = intercept;
        this.red = red; //new Color(bimage.getRGB(x2, y2)).getRed();
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
}
