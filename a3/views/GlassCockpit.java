package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a3.GameWorld;

public class GlassCockpit extends Container {

    GameWorld gw;

    private final Container titles = new Container(new GridLayout(1, 7));
    private final Container descriptions = new Container(new GridLayout(1, 7));

    private Label currentHeading;
    private Label currentSpeed;
    private Label currentFuel;
    private Label numBurningFires;
    private Label totalBurningFireSize;
    private Label totalPercentageBuildingsDestroyed;
    private Label totalFinancialLoss;

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
        titles.add("DAMAGE");
        titles.add("LOSS");

        currentHeading = new Label("0");
        currentSpeed = new Label("0");
        currentFuel = new Label("0");
        numBurningFires = new Label("0");
        totalBurningFireSize = new Label("0");
        totalPercentageBuildingsDestroyed = new Label("0");
        totalFinancialLoss = new Label("0");


        descriptions.getStyle().setBgColor(ColorUtil.WHITE);
        descriptions.getStyle().setBgTransparency(255);
        descriptions.add(currentHeading);
        descriptions.add(currentSpeed);
        descriptions.add(currentFuel);
        descriptions.add(numBurningFires);
        descriptions.add(totalBurningFireSize);
        descriptions.add(totalPercentageBuildingsDestroyed);
        descriptions.add(totalFinancialLoss);

        this.add(titles);
        this.add(descriptions);
    }

    public void update() {

        this.currentHeading.setText(gw.getHeading());
        this.currentSpeed.setText(gw.getSpeed());
        this.currentFuel.setText(gw.getFuel());
        this.numBurningFires.setText(gw.getNumBurningFires());
        this.totalBurningFireSize.setText(gw.getTotalBurningFireSize());
        this.totalPercentageBuildingsDestroyed.setText(gw.getTotalPercentageBuildingsDestroyed() + "%");
        this.totalFinancialLoss.setText("$" + gw.getFinancialLoss());

        this.repaint();
    }
}