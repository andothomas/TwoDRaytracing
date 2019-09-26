package TwoDRaytracing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.awt.image.BufferedImage;



public class Initializer {

    int cpx = 1;
    int cpy = 1;

    int x1,x2;
    int y1,y2;
    
    Driver driver;

    //import the bitmap image and convert to BufferedImage
    BufferedImage miniMap;

    //change the array size to length and width of bitmap image .png
    boolean[][] checkedPixels;

    //runs this before anything else
    public Initializer (Driver driver){
        try {
            String home = System.getProperty("user.home");
            miniMap = ImageIO.read(new File(home + "/Dropbox/2DRaytracing/src/Images/1.png"));//
            //= ImageIO.read(getClass().getResource("/Users/ajthomas/ianmahoney/Dropbox/2DRaytracing/src/Images/3.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        this.driver = driver;
        checkedPixels = new boolean[miniMap.getWidth()-1][miniMap.getHeight()-1];
        //System.out.println("Constructor ran.");
        }






    //current pixel (x,y) is (cpX,cpY)
    //Assuming the outermost border pixels of the bitmap are always walls

    //Also assuming (1,1) is always a black pixel (because the player is always a black pixel...or else...oof.)
    //otherwise we'd have to start at (1,1) and search for the first black pixel

    //send x1 x2 y1 y2 to a method that saves a wall to ArrayList.........
    //that method will take the coords and determine whether horizontal
    //if horiz, what is the intercept (repeated value)
    //and what is the red value of that pixel

    //ArrayList<TwoDRaytracing.Wall> walls = new ArrayList<>();



    //ends when all four faces of the first pixel are checked
    public void findWalls(int cpx, int cpy){
        //System.out.printf("New black pixel found at %d %d.\n", cpx, cpy);
        this.cpx = cpx;
        this.cpy = cpy;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        int red;
        int neighboringWallCount = 4;



        //set currentPixel = checked;
        checkedPixels[cpx][cpy] = true;

        //if (pixelColor(cpx,cpy-1) == red){
        //store (cpx,cpy-1) in arraylist; ----- more complicated. store the endpoints of this pixel face.

        //new Color(bimage.getRGB(x2, y2)).getRed();

        //if North pixel is a wall
        if (new Color(miniMap.getRGB(cpx, cpy - 1)).getRed() != 0){
            //store the endpoints of the wall face in the walls arraylist
            //throw x1 x2 y1 y2 into method that then throws the output (coordinates + other info) into the Wall object
            //eventually use this:
            //walls.add (new TwoDRaytracing.Wall(horizontal, intercept, red));

            //now find the boundary coordinates (x1,y1) (x2,y2) to pipe into the wallEdgePrepper method
            //(x1,y1) is the point left of (x2,y2)
            x1 = cpx;
            y1 = cpy;
            x2 = cpx + 1;
            y2 = cpy;
            red = new Color(miniMap.getRGB(cpx, cpy - 1)).getRed();

            wallEdgePrepper(x1,x2,y1,y2,red,neighboringWallCount);
            
        } else { //if this is running, it means the given pixel was black
            if(!checkedPixels[cpx][cpy - 1]){ //is this right? [cpy - 1] it changes the index.
                //if the pixel was not checked in a separate method call, call findWalls with the new pixel
                findWalls(cpx, cpy - 1);
                neighboringWallCount --;
            }
        }


        //if East pixel is a wall
        if (new Color(miniMap.getRGB(cpx + 1, cpy)).getRed() != 0){

            //now find the boundary coordinates (x1,y1) (x2,y2) to pipe into the wallEdgePrepper method
            //(x1,y1) is the point above (x2,y2)
            x1 = cpx + 1;
            y1 = cpy;
            x2 = cpx + 1;
            y2 = cpy +1;
            red = new Color(miniMap.getRGB(cpx + 1, cpy)).getRed();
            neighboringWallCount ++;

            wallEdgePrepper(x1,x2,y1,y2,red,neighboringWallCount);

        } else { //the given pixel was black (i.e. a path)
            if(!checkedPixels[cpx + 1][cpy]){
                //if the pixel was not checked, call this method with the new pixel
                findWalls(cpx + 1, cpy);
            }
        }


        //if South pixel is a wall
        if (new Color(miniMap.getRGB(cpx, cpy + 1)).getRed() != 0){

            //now find the boundary coordinates (x1,y1) (x2,y2) to pipe into the wallEdgePrepper method
            //(x1,y1) is the point right of (x2,y2)
            x1 = cpx + 1;
            y1 = cpy + 1;
            x2 = cpx;
            y2 = cpy +1;
            red = new Color(miniMap.getRGB(cpx, cpy + 1)).getRed();
            neighboringWallCount ++;

            wallEdgePrepper(x1,x2,y1,y2,red,neighboringWallCount);

        } else { //the given pixel was black
            if(!checkedPixels[cpx][cpy + 1]){
                //if the pixel was not checked, call this method with the new pixel
                findWalls(cpx, cpy + 1);
            }
        }


        //if West pixel is a wall
        if (new Color(miniMap.getRGB(cpx - 1, cpy)).getRed() != 0){

            //now find the boundary coordinates (x1,y1) (x2,y2) to pipe into the wallEdgePrepper method
            //(x1,y1) is the point below (x2,y2)
            x1 = cpx;
            y1 = cpy + 1;
            x2 = cpx;
            y2 = cpy;
            red = new Color(miniMap.getRGB(cpx - 1, cpy)).getRed();
            neighboringWallCount ++;

            wallEdgePrepper(x1,x2,y1,y2,red,neighboringWallCount);

        } else { //the given pixel was black
            if(!checkedPixels[cpx - 1][cpy]){
                //if the pixel was not checked, call this method with the new pixel
                findWalls(cpx - 1, cpy);
            }
        }

    }
    
    public void wallEdgePrepper(int x1, int x2, int y1, int y2, int red, int neighboringWallCount){

        //same x coordinate means the wall is vertical, otherwise it is horizontal
        boolean horizontal = (y1 == y2);

        //find out what the intercept of the wall is. If it's a vertical line, it has an x intercept and no y intercept. If it's a horizontal line, it has a y intercept and no x intercept.
        int intercept;

        if (horizontal){
            intercept = y1;
        } else{
            intercept = x1;
        }
        final int SCALE_FACTOR = 100;
        driver.walls.add (new TwoDRaytracing.Wall(horizontal, SCALE_FACTOR * intercept, red, SCALE_FACTOR * x1, SCALE_FACTOR * y1, SCALE_FACTOR * x2, SCALE_FACTOR * y2));
    }
}
