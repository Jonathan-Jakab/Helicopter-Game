package org.csc133.a5.gameobjects.helicopterParts;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class TailFrame {

    private int myColor;
    private Transform myTranslation, myRotation, myScale;
    private Point lowerLeftInLocalSpace, topLeft, topRight, bottomLeft, bottomRight;

    public TailFrame(int color) {

        topLeft = new Point(-50, 225);
        topRight = new Point(50, 225);
        bottomLeft = new Point(-30, -225);
        bottomRight = new Point(30, -225);

        lowerLeftInLocalSpace = new Point(topLeft.getX(), bottomLeft.getY());
        myColor = color;
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
        g.drawLine(originParent.getX() + topLeft.getX(),
                originParent.getY() + topLeft.getY(),
                originParent.getX() + topRight.getX(),
                originParent.getY() + topRight.getY());

        g.drawLine(originParent.getX() + topRight.getX(),
                originParent.getY() + topRight.getY(),
                originParent.getX() + bottomRight.getX(),
                originParent.getY() + bottomRight.getY());

        g.drawLine(originParent.getX() + bottomRight.getX(),
                originParent.getY() + bottomRight.getY(),
                originParent.getX() + bottomLeft.getX(),
                originParent.getY() + bottomLeft.getY());

        g.drawLine(originParent.getX() + bottomLeft.getX(),
                originParent.getY() + bottomLeft.getY(),
                originParent.getX() + topLeft.getX(),
                originParent.getY() + topLeft.getY());

        g.setColor(ColorUtil.GRAY);
        g.fillRect(originParent.getX() - 5,
                originParent.getY() + bottomLeft.getY(),
                10, topLeft.getY() - bottomLeft.getY());

        g.setTransform(gOrigXform);
    }
}
