package org.csc133.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a2.commands.*;
import org.csc133.a2.views.ControlCluster;
import org.csc133.a2.views.GlassCockpit;
import org.csc133.a2.views.MapView;

public class Game extends Form implements Runnable {

    private GameWorld gw;
    private ControlCluster controlCluster;
    private GlassCockpit glassCockpit;
    private MapView mapView;

    private static final int TICK_TIME = 100;


    public Game(){
        gw = new GameWorld();
        controlCluster = new ControlCluster(gw);
        glassCockpit = new GlassCockpit(gw);
        mapView = new MapView(gw);



        this.setLayout(new BorderLayout());
        this.add(BorderLayout.SOUTH, controlCluster);
        this.add(BorderLayout.NORTH, glassCockpit);
        this.add(BorderLayout.CENTER, mapView);

        addKeyListener('Q', new ExitCommand(gw));
        addKeyListener('d', new DrinkCommand(gw));
        addKeyListener('f', new FightCommand(gw));
        addKeyListener(-93, new TurnLeftCommand(gw));
        addKeyListener(-94, new TurnRightCommand(gw));
        addKeyListener(-91, new AccelerateCommand(gw));
        addKeyListener(-92, new BrakeCommand(gw));

        UITimer timer = new UITimer(this);
        timer.schedule(TICK_TIME, true, this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();
    }

    public static int getDispWidth(){

        return Display.getInstance().getDisplayWidth();
    }

    public static int getDispHeight(){

        return Display.getInstance().getDisplayHeight();
    }

    public static int getSmallDim(){

        return Math.min(getDispWidth(), getDispHeight());
    }

    public static int getLargeDim(){

        return Math.max(getDispWidth(), getDispHeight());
    }

    public void paint(Graphics g){

        super.paint(g);
    }

    @Override
    public void run(){

        gw.tick();
        glassCockpit.update();
        repaint();
    }
}