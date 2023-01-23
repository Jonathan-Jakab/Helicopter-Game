package org.csc133.a4.gameobjects.helicopterParts;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class MainRotor {

    private int width, length, myColor;
    private Transform myTranslation, myRotation, myScale;
    Point lowerLeftInLocalSpace;

    public MainRotor() {

        width = 30;
        length = 900;

        lowerLeftInLocalSpace = new Point(-width / 2, -length / 2);
        myColor = ColorUtil.GRAY;
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
    }

    public void rotate(double degrees) { myRotation.rotate((float) Math.toRadians(degrees), 0, 0); }

    public void scale(double sx, double sy) { myScale.scale((float) sx, (float) sy); }

    public void translate(double tx, double ty) { myTranslation.translate((float) tx, (float) ty); }

    public void localDraw(Graphics g, Point originParent, Point originScreen) {

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy();

        gXform.translate(originScreen.getX(), originScreen.getY());

        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());

        gXform.translate(-originScreen.getX(), -originScreen.getY());
        g.setTransform(gXform);

        g.setColor(myColor);
        g.fillRect(originParent.getX() + lowerLeftInLocalSpace.getX(),
                originParent.getY() + lowerLeftInLocalSpace.getY(),
                width, length);

        g.setColor(ColorUtil.BLACK);
        g.fillArc(originParent.getX() - 10,
                originParent.getY() - 10,
                20, 20, 0, 360);

        g.setTransform(gOrigXform);
    }
}