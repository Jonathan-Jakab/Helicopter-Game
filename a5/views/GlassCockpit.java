package org.csc133.a5.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;

import com.codename1.ui.layouts.GridLayout;
import org.csc133.a5.GameWorld;

import java.io.IOException;

public class GlassCockpit extends Container {

    GameWorld gw = GameWorld.getInstance();

    DigitalDisplayComponent headingDigits;
    DigitalDisplayComponent speedDigits;
    DigitalDisplayComponent fuelDigits;
    DigitalDisplayComponent fireCountDigits;
    DigitalDisplayComponent fireSizeDigits;
    DigitalDisplayComponent buildingPercentDigits;
    DigitalDisplayComponent buildingValueDigits;

    private final Container titles = new Container(new GridLayout(1, 7));
    private final Container descriptions = new Container(new GridLayout(1, 7));

    public GlassCockpit(GameWorld gameWorld) {

        gw = gameWorld;

        this.setLayout(new GridLayout(2, 1));

        titles.getStyle().setBgColor(ColorUtil.WHITE);
        titles.getStyle().setBgTransparency(255);
        titles.add("HEADING");
        titles.add("SPEED");
        titles.add("FUEL");
        titles.add("FIRES");
        titles.add("FIRE SIZE");
        titles.add("DAMAGE (%)");
        titles.add("LOSS ($)");

        headingDigits = new DigitalDisplayComponent(ColorUtil.rgb(255,0,0), 3);
        speedDigits = new DigitalDisplayComponent(ColorUtil.rgb(255,50,0), 2);
        fuelDigits = new DigitalDisplayComponent(ColorUtil.YELLOW, 6);
        fireCountDigits = new DigitalDisplayComponent(ColorUtil.GREEN, 3);
        fireSizeDigits = new DigitalDisplayComponent(ColorUtil.CYAN, 4);
        buildingPercentDigits = new DigitalDisplayComponent(ColorUtil.BLUE, 3);
        buildingValueDigits = new DigitalDisplayComponent(ColorUtil.MAGENTA, 4);

        descriptions.getStyle().setBgColor(ColorUtil.GRAY);
        descriptions.getStyle().setBgTransparency(255);
        descriptions.add(headingDigits);
        descriptions.add(speedDigits);
        descriptions.add(fuelDigits);
        descriptions.add(fireCountDigits);
        descriptions.add(fireSizeDigits);
        descriptions.add(buildingPercentDigits);
        descriptions.add(buildingValueDigits);

        this.add(titles);
        this.add(descriptions);
    }

    public void update() {

        this.headingDigits.setHeading(gw.getHeading());
        this.speedDigits.setSpeed(gw.getSpeed());
        this.fuelDigits.setFuel(gw.getFuel());
        this.fireCountDigits.setFireCount(gw.getNumBurningFires());
        this.fireSizeDigits.setFireSize(gw.getTotalBurningFireSize());
        this.buildingPercentDigits.setBuildPercent(gw.getTotalPercentageBuildingsDestroyed());
        this.buildingValueDigits.setBuildingVal(gw.getFinancialLoss());

        this.repaint();
    }
}