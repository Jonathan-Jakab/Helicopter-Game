package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;
import org.csc133.a3.interfaces.IsDrawable;

import java.util.Random;

public class Building extends FixedGameObject implements IsDrawable {

    private double buildingValue;
    private double currBuildingDamage;
    private double maxBuildingDamage;
    private double buildingDamagePrecent;
    private double buildingLoss;

    private Point lowerLeftInLocalSpace;

    public Building(Dimension worldSize, Dimension buildingSize, Point2D location) {

        buildingValue = new Random().nextInt(1000 - 100) + 100;
        currBuildingDamage = 0;
        maxBuildingDamage = 0;
        buildingDamagePrecent = 0;
        buildingLoss = 0;

        this.worldSize = worldSize;
        this.color = ColorUtil.rgb(255, 0, 0);
        this.dimension = new Dimension(buildingSize.getWidth(), buildingSize.getHeight());

        lowerLeftInLocalSpace = new Point(-dimension.getWidth() / 2, -dimension.getHeight() / 2);

        this.translate(location.getX(), location.getY());
        this.rotate(180);
        this.scale(-1, 1);
    }

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy();

        gXform.translate(originScreen.getX(), originScreen.getY());

        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());

        gXform.translate(-originScreen.getX(), -originScreen.getY());
        g.setTransform(gXform);

        int x = originParent.getX() + lowerLeftInLocalSpace.getX();
        int y = originParent.getY() + lowerLeftInLocalSpace.getY();
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.setColor(color);
        g.drawRect(x, y, w, h, 5);

        g.drawString("V: " + (int) buildingValue, x + w, y + h);
        g.drawString("D: " + (int) buildingDamagePrecent + "%", x + w, y + h + 30);

        g.setTransform(gOrigXform);
    }

    public void startFireInBuilding(Fire fire) {

        double xMin = myTranslation.getTranslateX() - dimension.getWidth() / 2 + 20;
        double xMax = myTranslation.getTranslateX() + dimension.getWidth() / 2 - 20;
        double yMin = myTranslation.getTranslateY() - dimension.getHeight() / 2 + 20;
        double yMax = myTranslation.getTranslateY() + dimension.getHeight() / 2 - 20;

        double locX = new Random().nextInt((int)(xMax - xMin)) + xMin;
        double locY = new Random().nextInt((int)(yMax - yMin)) + yMin;
        Point2D loc = new Point2D(locX, locY);

        fire.start(loc);
    }

    public boolean isFireInBuilding(Fire fire) {

        double fireWidth = fire.getDimension().getWidth();
        double fireHeight = fire.getDimension().getHeight();
        double fireX = fire.getX() - fireWidth / 2;
        double fireY = fire.getY() - fireHeight / 2;

        double buildingWidth = dimension.getWidth();
        double buildingHeight = dimension.getHeight();
        double buildingX = this.getX() - buildingWidth / 2;
        double buildingY = this.getY() - buildingHeight / 2;

        Rectangle r1 = new Rectangle((int) fireX, (int) fireY, (int)(fireWidth), (int)(fireHeight));
        Rectangle r2 = new Rectangle((int) buildingX, (int) buildingY, (int)(buildingWidth), (int)(buildingHeight));

        if (r1.intersects(r2)) {

            return true;
        }
        return false;
    }

    public void damageBuildings(double currBuildingDamage) {

        double buildingSize = dimension.getWidth() * dimension.getHeight();

        if (currBuildingDamage < buildingSize) {

            if (currBuildingDamage > maxBuildingDamage) {

                maxBuildingDamage = currBuildingDamage;
                buildingDamagePrecent = Math.ceil((maxBuildingDamage / buildingSize) * 100);
            }
        } else {

            maxBuildingDamage = buildingSize;
            buildingDamagePrecent = 100;
        }
        buildingLoss = buildingValue * (buildingDamagePrecent / 100);
    }

    public double getBuildingDamagePercent() {

        return buildingDamagePrecent;
    }

    public double getBuildingLoss() {

        return buildingLoss;
    }
}