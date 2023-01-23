package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a2.interfaces.IsDrawable;

import java.util.Random;

public class Building extends FixedGameObject implements IsDrawable {

    private double buildingValue;
    private double currBuildingDamage;
    private double maxBuildingDamage;
    private double buildingDamagePrecent;
    private double buildingLoss;

    public Building(Dimension worldSize, Dimension buildingSize, Point2D location){

        buildingValue = new Random().nextInt(1000-100)+100;
        currBuildingDamage = 0;
        maxBuildingDamage = 0;
        buildingDamagePrecent = 0;
        buildingLoss = 0;

        this.worldSize = worldSize;
        this.color = ColorUtil.rgb(255,0,0);
        this.location =  new Point2D(location.getX() - buildingSize.getWidth()/2, location.getY() - buildingSize.getHeight()/2);
        this.dimension = new Dimension(buildingSize.getWidth(), buildingSize.getHeight());
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        g.setColor(color);

        int x = containerOrigin.getX() + (int)location.getX();
        int y = containerOrigin.getY() + worldSize.getHeight() - ((int)(location.getY() + dimension.getHeight()));
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.drawRect(x, y, w, h, 5);

        g.drawString("V: " + (int)buildingValue, x + w, y + h);
        g.drawString("D: " + (int)buildingDamagePrecent + "%", x + w, y + h +30);
    }

    public void setFireInBuilding(Fire fire){

        int fireSize = fire.getDimension().getWidth();

        double xMin = location.getX() + 50;
        double xMax = location.getX() + dimension.getWidth() - 50;
        double yMin = location.getY() + 50;
        double yMax = location.getY() + getDimension().getHeight() - 50;

        double locX = (int) (new Random().nextInt((int) (xMax - xMin)) + xMin);
        double locY = (int) (new Random().nextInt((int) (yMax - yMin)) + yMin);
        Point2D loc = new Point2D(locX, locY);

        fire.start(loc);
    }


    public boolean isFireInBuilding(Fire fire){

        Point2D centerOfFire = new Point2D(fire.getLocation().getX() + fire.getDimension().getWidth()/2, fire.getLocation().getY() + fire.getDimension().getHeight()/2);

        return (centerOfFire.getX() > location.getX()
                && centerOfFire.getX() < location.getX() + dimension.getWidth()
                && centerOfFire.getY() > location.getY()
                && centerOfFire.getY() < location.getY() + dimension.getHeight());
    }

    public void setBuildingDamage(double currBuildingDamage){

        double buildingSize = dimension.getWidth() * dimension.getHeight();

        if(currBuildingDamage < buildingSize){
            if(currBuildingDamage > maxBuildingDamage){
                maxBuildingDamage = currBuildingDamage;
                buildingDamagePrecent = Math.ceil((maxBuildingDamage / buildingSize) * 100);

            }
        }else{
            maxBuildingDamage = buildingSize;
            buildingDamagePrecent = 100;
        }
        buildingLoss = buildingValue * (buildingDamagePrecent/100);
    }

    public double getBuildingDamagePercent(){

        return buildingDamagePrecent;
    }

    public double getBuildingLoss(){

        return buildingLoss;
    }
}