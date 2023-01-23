package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.interfaces.IsDrawable;

public abstract class GameObject implements IsDrawable {

    protected int color;

    protected Transform myTranslation, myRotation, myScale;
    protected Point lowerLeftInLocalSpace;

    protected Dimension dimension;
    protected Dimension worldSize;

    public GameObject() {

        dimension = new Dimension(1, 1);
        color = ColorUtil.YELLOW;

        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
    }

    public abstract void draw(Graphics g, Point originParent, Point originScreen);

    public void move(double deltaX, double deltaY) { this.setLocation(deltaX, deltaY); }

    public double getX() { return myTranslation.getTranslateX(); }

    public double getY() { return myTranslation.getTranslateY(); }

    public void setLocation(double x, double y) { myTranslation.translate((int) x, (int) y); }

    public int getColor() { return this.color; }

    public void setColor(int newColor) { this.color = newColor; }

    public Dimension getDimension() { return this.dimension; }

    public void setDimension(Dimension newDimension) { this.dimension = newDimension; }

    public String toString() { return this.getClass().getSimpleName(); }

    public void resetRotation() { myRotation.setIdentity(); }

    public void resetTranslation() { myTranslation.setIdentity(); }

    public void rotate(double degrees) { myRotation.rotate((float) Math.toRadians(degrees), 0, 0); }

    public void scale(double sx, double sy) { myScale.scale((float) sx, (float) sy); }

    public void translate(double tx, double ty) { myTranslation.translate((float) tx, (float) ty); }

    public Transform getRotation() { return myRotation; }

    public Transform getTranslate() { return myTranslation; }

    public Transform getScale() { return myScale; }
}