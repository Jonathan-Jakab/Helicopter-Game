package org.csc133.a5;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a5.commands.*;
import org.csc133.a5.views.ControlCluster;
import org.csc133.a5.views.GlassCockpit;
import org.csc133.a5.views.MapView;

public class Game extends Form implements Runnable {

    private GameWorld gw;
    private ControlCluster controlCluster;
    private GlassCockpit glassCockpit;
    private MapView mapView;

    private int TICK_TIME = 100;

    private int mouseX, mouseY;

    public Game() {

        gw = GameWorld.getInstance();
        controlCluster = new ControlCluster(gw);
        glassCockpit = new GlassCockpit(gw);
        mapView = new MapView(gw);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.SOUTH, controlCluster);
        this.add(BorderLayout.NORTH, glassCockpit);
        this.add(BorderLayout.CENTER, mapView);

        addKeyListener('s', new StopEngineCommand(gw));
        addKeyListener('Q', new ExitCommand(gw));
        addKeyListener('d', new DrinkCommand(gw));
        addKeyListener('f', new FightCommand(gw));
        addKeyListener('z', new ZoomInCommand(mapView));
        addKeyListener('x', new ZoomOutCommand(mapView));
        addKeyListener('c', new ZoomResetCommand(mapView));

        addKeyListener(-93, new TurnLeftCommand(gw));
        addKeyListener(-94, new TurnRightCommand(gw));
        addKeyListener(-91, new AccelerateCommand(gw));
        addKeyListener(-92, new BrakeCommand(gw));


        UITimer timer = new UITimer(this);
        timer.schedule(TICK_TIME, true, this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();
    }

    public static int getDispWidth() {

        return Display.getInstance().getDisplayWidth();
    }

    public static int getDispHeight() {

        return Display.getInstance().getDisplayHeight();
    }

    public static int getSmallDim() {

        return Math.min(getDispWidth(), getDispHeight());
    }

    public static int getLargeDim() {

        return Math.max(getDispWidth(), getDispHeight());
    }

    public void paint(Graphics g) {

        super.paint(g);
    }

    @Override
    public void run() {

        gw.tick(TICK_TIME);
        glassCockpit.update();
        controlCluster.update();
        repaint();
    }
}