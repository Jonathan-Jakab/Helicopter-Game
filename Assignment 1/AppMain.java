package org.csc133.RainStem;


import static com.codename1.ui.CN.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.util.UITimer;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        new Game().show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }
}

class Game extends Form implements Runnable {
    BirdWorld bw;

    public Game() {
        bw = new BirdWorld();

        UITimer timer = new UITimer(this);
        timer.schedule(100, true, this);
    }

    public void paint(Graphics g){
        super.paint(g);
        bw.draw(g);
    }
    @Override
    public void run() {
        bw.tick();
        repaint();
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class BirdWorld {
    Sky sky;
    Cloud cloud;
    Ground ground;
    CopyOnWriteArrayList<RainDrop> rain = new CopyOnWriteArrayList<>();

    public BirdWorld() {
        sky = new Sky();
        cloud = new Cloud();
        ground = new Ground();
        rain = new CopyOnWriteArrayList<>();

    }

    void draw(Graphics g) {
        sky.draw(g);

        for(RainDrop drop : rain)
            drop.draw(g);

        cloud.draw(g); // Drawing order matters, things above will draw behind things below
        ground.draw(g);

        g.setColor(ColorUtil.WHITE);
        g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_BOLD, SIZE_LARGE)); // set font style and size
        g.drawString(":" + rain.size(), 100, Display.getInstance().getDisplayHeight()-100); // display amount of rain objects
    }

    void tick() {
        rain.add(new RainDrop());

        for(RainDrop drop: rain){
            drop.fall();
            if(drop.location.getY() > ground.groundLevel) // how to detect location based on another element
                rain.remove(drop);
        }
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class Sky {
    void draw(Graphics g){
        g.setColor(ColorUtil.LTGRAY);
        g.fillRect(0,0,
                Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayHeight());
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Uh oh! Technical debt!
//
class Cloud {
    void draw(Graphics g) {
        g.setColor(ColorUtil.WHITE);
        g.fillArc(0, -200,
                Display.getInstance().getDisplayWidth(),
                400,
                180, 180);

    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class Ground {
    int groundLevel;

    public Ground(){
        groundLevel = Display.getInstance().getDisplayHeight() - 300; // Set to bottom of the screen, then 300 pixels up
    }

    void draw(Graphics g) {
        g.setColor(ColorUtil.rgb(140, 70, 20));
        g.fillRect(0, groundLevel,
                Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayWidth());
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class RainDrop{
    Point location;
    int size;
    int speed;

    public RainDrop(){ // This can be used for locating Fire in HFD
        Random r = new Random();
        location = new Point(r.nextInt(Display.getInstance().getDisplayWidth()), 0);
        size = 15 + r.nextInt(40);
        speed = 40 + r.nextInt(15);
    }

    void fall(){ // Move function for helicopter?
        location.setY(location.getY()+speed);
    }

    void draw(Graphics g){
        g.setColor(ColorUtil.BLUE);
        g.fillArc(location.getX(),location.getY(), size, size, 0, 360);
    }
}