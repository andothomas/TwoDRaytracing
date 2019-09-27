/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TwoDRaytracing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;

/**
 *
 * @author ianmahoney
 */
public class Driver {

    Display display;
    int[] resolution = {800, 600};
    boolean running = true;
    Input input;
    ArrayList<Wall> walls = new ArrayList<Wall>();
    
    
    

    private int targetMillis = 1000 / 600,
            lastTime = (int) System.currentTimeMillis(),
            targetTime = lastTime + targetMillis;

    public static float timeScaler = 1;
    
    int frames = 0, timeSinceLastCheck, timeAtLastCheck = (int) System.currentTimeMillis(), fps;

    Timer timer;

    Player player;
    
    int screenDistance = 5;
            
    double m, x, y, b, d;
    
    Initializer initializer;
    
    
    public Driver() {
        
        
        
        display = new Display(this);
        //Thread t1 = new Thread(new Input(this));
        //t1.start();
        input = new Input(this);
        
        display.addKeyListener(input);
        display.requestFocus();
//       walls.add(new Wall(true, -10, 255, -10, -10, -10, 10)); 
//       walls.add(new Wall(true, 10, 255, 10, 10, 10, -10));
//       walls.add(new Wall(false, 10, 255, -10, 10, 10, 10));
//        walls.add(new Wall(false, -10, 255, 10, -10, -10, -10));
        player = new Player(1, 1, 90,this);
        initializer = new Initializer(this);
        
        //System.out.println(walls.size());
      initializer.findWalls(1, 1);
        timer = new Timer(1, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int current = (int) System.currentTimeMillis(); // Now time
                timeSinceLastCheck += (current - timeAtLastCheck);
                //System.out.println(frames);
                
                if (timeSinceLastCheck >= 1000){
                    
                    fps = frames/(timeSinceLastCheck/1000);
                    //System.out.println("FPS: " + fps + ", " + timeScaler);
                    frames = 0;
                    timeSinceLastCheck = 0;
                    timeAtLastCheck = (int) System.currentTimeMillis(); 
                }
                //System.out.println(frames);
                
                if (current < targetTime) {
                    return; // Stop here if its not time for the next frame
                }
                timeScaler = (float) targetMillis / (current - lastTime); //Scale game on how late frame is.
                lastTime = current;
                //targetTime = lastTime + targetMillis;
                targetTime = (current + targetMillis) - (current - targetTime);
                if(running){
                    tick();
                    display.render();
                } else {
                    display.renderTitle();
                }
                frames += 1;
                
                
            }
        });
        
        timer.start();
    }

    public void tick() {
        //System.out.println(walls.size());
        player.tick();
        //System.out.println(Arrays.toString(walls.toArray()));
        for(int i = 0; i < resolution[0]; i+=1) {
            for (int j = 0; j < walls.size(); j+= 1) {
                try {
                //m = (double) (((double)screenDistance - (double)player.y)/((double)i - (double)player.x));
                m = (Math.tan(Math.toRadians(player.angle + (i * 90/resolution[0]))));
                b = (double) ((double) player.y - m * (double) player.x);
                //System.out.println(m + ", " + b);
                if (walls.get(j).horizontal){
                    y = (double)(((double) walls.get(j).intercept - b) / m);
                    x = (double) walls.get(j).intercept;
                    //System.out.println(x + ", " + y);
                    if (((x >= walls.get(j).x1) && (x <= walls.get(j).x2)) || ((x <= walls.get(j).x1) && (x >= walls.get(j).x2))&& ((x <= walls.get(j).x2) && (y >= walls.get(j).y1) && (x <= walls.get(j).y2)||(x <= walls.get(j).x2) && (y <= walls.get(j).y1) && (x >= walls.get(j).y2))){ //(x >= walls.get(j).x1) && (x <= walls.get(j).x2) && (y >= walls.get(j).y1) && (x <= walls.get(j).y2)
                    //System.out.println(x + ", " + y);
                    d = Math.sqrt((x-(double) player.x)*(x-(double) player.x) + (y-(double) player.y)*(x-(double) player.y) );
                    d = d * Math.cos(Math.toRadians(player.angle + 90 + (i * 90/resolution[0])));
                    //System.out.println(x + ", " + y + ", " + d);
                    display.rayPoints[i] = new double[]{y, (d) , 20};
                    }
                } else {
                    x = (double) walls.get(j).intercept;
                    y = m * x + b;
                    //System.out.println(x + ", " + y);
                    if ((((x >= walls.get(j).x1) && (x <= walls.get(j).x2)) || ((x <= walls.get(j).x1) && (x >= walls.get(j).x2))) && (((y >= walls.get(j).y1) && (y <= walls.get(j).y2)) || ((y <= walls.get(j).y1) && (y >= walls.get(j).y2)))){
                    //if ((x >= 0) && (x <= 100) && (y >= 0) && (x <= 100)){
                    d = Math.sqrt((x-(double) player.x)*(x-(double) player.x) + (y-(double) player.y)*(x-(double) player.y) );
                    d = d * Math.cos(Math.toRadians(player.angle  + 90+ (i * 90/resolution[0])));
                    //System.out.println(x + ", " + y + ", " + d);
                    display.rayPoints[i] = new double[]{y, (d) , 10};
                    }
                }
                
                } catch (Exception e){
                    //e.printStackTrace();
                }
                display.requestFocus();
           
        }

    }
    }

    public static void main(String[] args) {
        new Driver();
    }
}
