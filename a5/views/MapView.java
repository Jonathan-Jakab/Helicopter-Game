package org.csc133.a5.views;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a5.GameWorld;
import org.csc133.a5.gameobjects.FixedGameObject;
import org.csc133.a5.gameobjects.GameObject;
import org.csc133.a5.interfaces.IsSelectable;

import java.util.concurrent.CopyOnWriteArrayList;


public class MapView extends Container {

    private GameWorld gw;
    private float winLeft, winRight, winTop, winBottom;
    private Transform worldToND, ndToScreen, theVTM;
    private float origLeft, origRight, origTop, origBottom;

    private float winHeight, winWidth;

    private Point ptrPosition;
    private double px, py;
    private boolean position;

    private Point originParent;
    private Point originScreen;

    private CopyOnWriteArrayList<GameObject> gameObjects;

    public MapView(GameWorld gameWorld) {

        gw = gameWorld;
        winLeft = winBottom = 0;
        origLeft = origBottom = 0;

    }

    @Override
    public void laidOut() {

        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));

        winRight = this.getWidth();
        winTop = this.getHeight();
        origRight = this.getWidth();
        origTop = this.getHeight();
        gw.getInstance().init();
    }

//    public void displayTransform(Graphics g) {
//
//        Transform gXform = Transform.makeIdentity();
//        g.getTransform(gXform);
//        gXform.translate(getAbsoluteX(), getAbsoluteY());
//
//        gXform.translate(0, getHeight());
//        gXform.scale(1, -1);
//
//        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
//
//        g.setTransform(gXform);
//    }

    private Transform buildWorldtoNDXform(float winWidth, float winHeight, float winLeft, float winBottomtom) {

        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale((1 / winWidth), (1 / winHeight));
        tmpXform.translate(-winLeft, -winBottomtom);
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

        winHeight = winTop - winBottom;
        winWidth = winRight - winLeft;

        worldToND = buildWorldtoNDXform(winWidth, winHeight, winLeft, winBottom);
        ndToDisplay = buildWNDToDisplayXform(this.getWidth(), this.getHeight());
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

        originParent = new Point(this.getX(), this.getY());
        originScreen = new Point(getAbsoluteX(), getAbsoluteY());

        for (GameObject go : gw.getGameObjectCollection()) {

            go.draw(g, originParent, originScreen);
        }

        g.resetAffine();
    }

    public void mapviewZoomIn() {

        double tempHeight = winTop - winBottom;
        double tempWidth = winRight - winLeft;
        winLeft += tempWidth * 0.05;
        winRight -= tempWidth * 0.05;
        winTop -= tempHeight * 0.05;
        winBottom += tempHeight * 0.05;
        this.repaint();
    }

    public void mapviewZoomOut() {

        double tempHeight = winTop - winBottom;
        double tempWidth = winRight - winLeft;
        winLeft -= tempWidth * 0.05;
        winRight += tempWidth * 0.05;
        winTop += tempHeight * 0.05;
        winBottom -= tempHeight * 0.05;
        this.repaint();
    }

    public void mapviewResetZoom() {

        winLeft = origLeft;
        winRight = origRight;
        winTop = origTop;
        winBottom = origBottom;
        this.repaint();
    }

    public void mapviewPanLeft() {
        winLeft += 5;
        winRight += 5;
    }

    public void mapviewPanRight() {
        winLeft -= 5;
        winRight -= 5;
    }

    public void mapviewPanUp() {
        winTop -= 5;
        winBottom -= 5;
    }

    public void mapviewPanDown() {
        winTop += 5;
        winBottom += 5;
    }


    public void pointerPressed(int x, int y) {

        px = x - originScreen.getX();
        py = winHeight -(y - originScreen.getY());

        ptrPosition = new Point((int)px, (int)py);

        gameObjects = gw.getGameObjectCollection();

        if (position == false) {

            for (GameObject go : gameObjects) {

                if (go instanceof IsSelectable) {

                    IsSelectable temp = (IsSelectable) go;
                    if (temp.contains(ptrPosition)) {

                        temp.setSelected(true);
                    } else {

                        temp.setSelected(false);
                    }
                }
            }
            repaint();
        } else {

            for (GameObject go : gameObjects) {

                if (go instanceof FixedGameObject) {

                    FixedGameObject temp = (FixedGameObject) go;
                    if (temp.isSelected()) {

                        temp.setLocation(px, py);
                        temp.setSelected(false);
                    }
                }
            }
            repaint();
            position = false;
        }
    }
}




