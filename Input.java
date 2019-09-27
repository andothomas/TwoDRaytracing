/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TwoDRaytracing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author ianmahoney
 */
public class Input extends KeyAdapter implements Runnable {
    
    Driver driver;
    
    public void run(){
        
    }
    
   
    boolean w = false, s = false, a = false, d = false;
    
    public Input(Driver driver) {
        this.driver = driver;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = true;
                //driver.player.velocity = 5;
                break;
            case KeyEvent.VK_S:
                s = true;
                //driver.player.velocity = -5;
                break;
            case KeyEvent.VK_A:
                a = true;
                //driver.player.vela = 1;
                break;
            case KeyEvent.VK_D:
                d = true;
                //driver.player.vela = -1;
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = false;
                //driver.player.velocity = 0;
                break;
            case KeyEvent.VK_S:
                s = false;
                //driver.player.velocity = 0;
                break;
            case KeyEvent.VK_A:
                a = false;
                //driver.player.vela = 0;
                break;
            case KeyEvent.VK_D:
                d = false;
                //driver.player.vela = 0;
                break;
        }

    }
}
