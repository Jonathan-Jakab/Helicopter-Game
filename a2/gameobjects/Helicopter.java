package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a2.GameWorld;
import org.csc133.a2.interfaces.IsSteerable;

import static com.codename1.ui.CN.*;

public class Helicopter extends MovableGameObject  implements IsSteerable {

    private int fuel;
    private int water;

    final static int MAX_SPEED = 10;
    final static int MAX_WATER = 1000;

    public Helicopter(Dimension worldSize, Point2D lz){

        this.worldSize = worldSize;
        this.color = ColorUtil.YELLOW;

        int width = worldSize.getWidth() / 50;
        fuel = GameWorld.INIT_FUEL;

        this.location = new Point2D(lz.getX() - width / 2 , lz.getY() - width / 2);
        this.dimension = new Dimension(width, width);
    }


    public void accelerate() {

        int currSpeed = getSpeed();
        this.setSpeed(Math.min(++currSpeed, MAX_SPEED));
    }

    public void brake() {

        int currSpeed = getSpeed();
        this.setSpeed(Math.max(--currSpeed, 0));
    }
    @Override
    public void steerLeft() {

        int currHeading = getHeading();
        currHeading += 15;
        this.setHeading(currHeading);

        if (this.getHeading() < 0 ){
            this.setHeading(currHeading + 360);
        }
        if(this.getHeading() > 360 ){
            this.setHeading(currHeading - 360);
        }
    }

    @Override
    public void steerRight() {

        int currHeading = getHeading();
        currHeading -= 15;
        this.setHeading(currHeading);

        if (this.getHeading() < 0 ){
            this.setHeading(currHeading + 360);
        }
        if(this.getHeading() > 360 ){
            this.setHeading(currHeading - 360);
        }
    }

    public void fight(Fire fire){

        if(fire.near(this)){
            fire.shrink(water);
            dumpWater();
        }
    }

    public boolean near(Fire fire) {

        return fire.near(this);
    }

    public void dumpWater(){

        water = 0;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        g.setColor(color);

        double angle = Math.toRadians(270 - this.getHeading());

        int x = containerOrigin.getX() + (int)location.getX();
        int y = containerOrigin.getY() - ((int)(location.getY()) + dimension.getHeight()) + worldSize.getHeight();
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        int eX = (int)(dimension.getHeight()*1.75 * Math.cos(angle));
        int eY = (int)(dimension.getHeight()*1.75 * Math.sin(angle));
        int oX = x + dimension.getWidth()/2;
        int oY = y + dimension.getWidth()/2;


        g.fillArc(x, y, w, h, 0, 360);
        g.drawLine(oX, oY, oX+eX, oY+eY);

        g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_BOLD, SIZE_SMALL));
        g.drawString("F: " + fuel, x, y + h);
        g.drawString("W: " + water, x, (int) (y + h*1.75));

    }

    public void drinkFrom(River river) {

        if(canDrinkFrom(river) && this.getSpeed() <=2){
            water = Math.min(100+water, MAX_WATER);
        }
    }

    boolean canDrinkFrom(River river){

        return river.underneathHelicopter(this);
    }

    public boolean hasLandedAt(Helipad helipad) {

        return(this.getSpeed() == 0 && helipad.isUnderneath(this));
    }

    public Point2D getLocation() {

        return location;
    }

    public int getFuel() {

        return fuel;
    }

    public int setFuel() {

        if(fuel>0){

            fuel = Math.max(fuel - (getSpeed()*getSpeed() + 5), 0);
        }
        return fuel;
    }
}
