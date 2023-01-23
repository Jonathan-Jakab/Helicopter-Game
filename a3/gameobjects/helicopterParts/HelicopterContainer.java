package org.csc133.a3.gameobjects.helicopterParts;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class HelicopterContainer extends Container {

    private EngineCube myEngine;
    private Cockpit myCockpit;
    private Skids mySkidsleft;
    private Skids mySkidsRight;
    private TailFrame myTailFrame;
    private Tail myTail;

    private MainRotor myMainRotor;
    private TailRotor[] myTailRotor;
    private TailRotor tempTailRotor;

    private Transform myTranslation, myRotation, myScale;

    private int position;
    private boolean rotorDirection;

    public HelicopterContainer() {

        position = 0;
        rotorDirection = true;

        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();

        myEngine = new EngineCube();
        myEngine.scale(1, 1);
        myEngine.translate(0, 0);

        myCockpit = new Cockpit();
        myCockpit.scale(1, 1);
        myCockpit.translate(0, 175);

        mySkidsleft = new Skids();
        mySkidsleft.scale(1, 1);
        mySkidsleft.translate(-225, 130);

        mySkidsRight = new Skids();
        mySkidsRight.scale(1, 1);
        mySkidsRight.rotate(180);
        mySkidsRight.translate(225, 130);

        myTailFrame = new TailFrame();
        myTailFrame.scale(1, 1);
        myTailFrame.translate(0, -275);

        myTail = new Tail();
        myTail.scale(1, 1);
        myTail.translate(0, -525);

        myMainRotor = new MainRotor();
        myMainRotor.scale(1, 1);
        myMainRotor.translate(0, 0);

        myTailRotor = new TailRotor[11];
        createTailRotorPositions();
    }

    public void rotate(double degrees) { myRotation.rotate((float) Math.toRadians(degrees), 0, 0); }

    public void scale(double sx, double sy) { myScale.scale((float) sx, (float) sy); }

    public void translate(double tx, double ty) { myTranslation.translate((float) tx, (float) ty); }

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

        myEngine.localDraw(g, originParent, originScreen);
        myCockpit.localDraw(g, originParent, originScreen);
        mySkidsleft.localDraw(g, originParent, originScreen);
        mySkidsRight.localDraw(g, originParent, originScreen);
        myTailFrame.localDraw(g, originParent, originScreen);
        myTail.localDraw(g, originParent, originScreen);

        myMainRotor.localDraw(g, originParent, originScreen);
        tempTailRotor.localDraw(g, originParent, originScreen);

        g.setTransform(gOrigXform);
    }


    public void updateLTs(double speed) {

        myMainRotor.rotate(speed);

        if (rotorDirection == false) {
            if (position > 1) {
                tempTailRotor = myTailRotor[position];
                position--;
            } else {
                rotorDirection = true;
            }
        }

        if (rotorDirection == true) {
            if (position < 9) {
                tempTailRotor = myTailRotor[position];
                position++;
            } else {
                rotorDirection = false;
            }
        }
    }

    public void createTailRotorPositions() {

        TailRotor p0 = new TailRotor();
        p0.scale(1, 1);
        p0.translate(70, -525);
        myTailRotor[0] = p0;

        TailRotor p1 = new TailRotor();
        p1.scale(1, 0.9);
        p1.translate(70, -525);
        myTailRotor[1] = p1;

        TailRotor p2 = new TailRotor();
        p2.scale(1, 0.8);
        p2.translate(70, -525);
        myTailRotor[2] = p2;

        TailRotor p3 = new TailRotor();
        p3.scale(1, 0.7);
        p3.translate(70, -525);
        myTailRotor[3] = p3;

        TailRotor p4 = new TailRotor();
        p4.scale(1, 0.6);
        p4.translate(70, -525);
        myTailRotor[4] = p4;

        TailRotor p5 = new TailRotor();
        p5.scale(1, 0.5);
        p5.translate(70, -525);
        myTailRotor[5] = p5;

        TailRotor p6 = new TailRotor();
        p6.scale(1, 0.4);
        p6.translate(70, -525);
        myTailRotor[6] = p6;

        TailRotor p7 = new TailRotor();
        p7.scale(1, 0.3);
        p7.translate(70, -525);
        myTailRotor[7] = p7;

        TailRotor p8 = new TailRotor();
        p8.scale(1, 0.2);
        p8.translate(70, -525);
        myTailRotor[8] = p8;

        TailRotor p9 = new TailRotor();
        p9.scale(1, 0.1);
        p9.translate(70, -525);
        myTailRotor[9] = p9;

        TailRotor p10 = new TailRotor();
        p10.scale(1, 0.05);
        p10.translate(70, -525);
        myTailRotor[10] = p10;

        tempTailRotor = myTailRotor[0];
    }
}