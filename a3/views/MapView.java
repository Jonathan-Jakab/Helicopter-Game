package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.AffineTransform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameobjects.GameObject;


public class MapView extends Container {

    private GameWorld gw;
    private float winLeft, winRight, winTop, winBottom;
    private Transform worldToND, ndToScreen, theVTM;

    public MapView(GameWorld gameWorld) {

        gw = gameWorld;
    }

    @Override
    public void laidOut() {

        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
        winRight = this.getWidth();
        winTop = this.getHeight();
        gw.init();
    }

    public void displayTransform(Graphics g) {

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        gXform.translate(0, getHeight());
        gXform.scale(1, -1);

        gXform.translate(-getAbsoluteX(), -getAbsoluteY());

        g.setTransform(gXform);
    }

    private Transform buildWorldtoNDXform(float winWidth, float winHeight, float winLeft, float winBottom) {

        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale((1 / winWidth), (1 / winHeight));
        tmpXform.translate(-winLeft, -winBottom);
        return tmpXform;
    }

    private Transform buildWNDToDisplayXform(float displayWidth, float displayHeight) {

        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0, displayHeight);
        tmpXform.scale(displayWidth, -displayHeight);
        return tmpXform;
    }

    private void setupVTM(Graphics g) {

        Transform worldToND, ndToDisplay, theVTM;

        winLeft = winBottom = 0;
        winRight = this.getWidth();
        winTop = this.getHeight();

        float winHeight = winTop - winBottom;
        float winWidth = winRight - winLeft;

        worldToND = buildWorldtoNDXform(winWidth, winHeight, winLeft, winBottom);
        ndToDisplay = buildWNDToDisplayXform(winWidth, winHeight);
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(getAbsoluteX(), getAbsoluteY());
        gxForm.concatenate(theVTM);
        gxForm.translate(-getAbsoluteX(), -getAbsoluteY());

        g.setTransform(gxForm);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        setupVTM(g);

        Point originParent = new Point(this.getX(), this.getY());
        Point originScreen = new Point(getAbsoluteX(), getAbsoluteY());

        for (GameObject go: gw.getGameObjectCollection()) {

            go.draw(g, originParent, originScreen);
        }

        g.resetAffine();
    }

    // public void zoomIn() {
    //     double h = winTop - winBot;
    //     double w = winRight - winLeft;
    //     winLeft += w * 0.05;
    //     winRight -= w * 0.05;
    //     winTop -= h * 0.05;
    //     winBot += h * 0.05;
    //     this.repaint();
    // }

    // public void zoomOut() {
    //     double h = winTop - winBot;
    //     double w = winRight - winLeft;
    //     winLeft -= w * 0.05;
    //     winRight += w * 0.05;
    //     winTop += h * 0.05;
    //     winBot -= h * 0.05;
    //     this.repaint();
    // }

    // public void panLeft() {
    //     winLeft += 5;
    //     winRight += 5;
    // }

    // public void panRight() {
    //     winLeft -= 5;
    //     winRight -= 5;
    // }

    // public void panUp() {
    //     winTop -= 5;
    //     winBot -= 5;
    // }

    // public void panDown() {
    //     winTop += 5;
    //     winBot += 5;
    // }
}