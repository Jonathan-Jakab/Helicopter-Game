package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a2.interfaces.IsDrawable;

public abstract class GameObject implements IsDrawable {

    protected int color;
    protected Point2D location;
    protected Dimension dimension;
    protected Dimension worldSize;

    public GameObject(){

        location = new Point2D(0,0);
        dimension = new Dimension(1,1);
        color = ColorUtil.YELLOW;
    }

    public GameObject(Point2D location, Dimension dimension){

        this.color = ColorUtil.YELLOW;
        this.location = location;
        this.dimension = dimension;
    }

    public GameObject(Dimension worldsize, Dimension Building, Point2D location){

        this.color = color;
        this.location = location;
        this.dimension = dimension;
    }

    public abstract void draw(Graphics g, Point point);

    public Point2D getLocation(){

        return location;
    }

    public void setLocation(int x, int y){

        this.location.setX(x);
        this.location.setY(y);
    }

    public void move(int deltaX, int deltaY) {

        int newX = (int) (location.getX() + deltaX);
        int newY = (int) (location.getY() + deltaY);

        this.location.setX(newX);
        this.location.setY(newY);
    }

    public int getColor() { return this.color; }

    public void setColor(int newColor) { this.color = newColor; }

    public Dimension getDimension() { return this.dimension; }

    public void setDimension(Dimension newDimension) { this.dimension = newDimension; }

    public String toString(){
        return this.getClass().getSimpleName();
    }
}