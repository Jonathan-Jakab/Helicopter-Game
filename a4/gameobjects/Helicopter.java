package org.csc133.a4.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a4.GameWorld;
import org.csc133.a4.gameobjects.helicopterParts.HelicopterContainer;
import org.csc133.a4.interfaces.HelicopterState;
import org.csc133.a4.interfaces.IsSteerable;
import org.csc133.a4.states.*;

import static com.codename1.ui.CN.*;

public class Helicopter extends MovableGameObject implements IsSteerable {

    private HelicopterState helicopterState;
    private HelicopterOffState helicopterOffState;
    private HelicopterStartingState helicopterStartingState;
    private HelicopterReadyState helicopterReadyState;
    private HelicopterStoppingState helicopterStoppingState;

    private HelicopterContainer myHelicopter;

    private Point lowerLeftInLocalSpace;

    private int fuel;
    private int water;
    private double rotationalSpeed;

    final static int MAX_SPEED = 10;
    final static int MAX_WATER = 1000;
    final static int MAX_ROTOR_SPEED = 50;

    public Helicopter(Dimension worldSize, Helipad helipad, int color) {

        int width = helipad.getDimension().getWidth();
        this.worldSize = worldSize;
        this.color = color;
        this.dimension = new Dimension(width, width);

        myHelicopter = new HelicopterContainer(color);

        helicopterOffState = new HelicopterOffState();
        helicopterStartingState = new HelicopterStartingState();
        helicopterReadyState = new HelicopterReadyState();
        helicopterStoppingState = new HelicopterStoppingState();
        helicopterState = helicopterOffState;

        fuel = GameWorld.INIT_FUEL;
        rotationalSpeed = 0;

        lowerLeftInLocalSpace = new Point(myHelicopter.getX() - dimension.getWidth() / 2, myHelicopter.getY() - dimension.getHeight() / 2);

        this.translate(helipad.getX(), helipad.getY());
        this.rotate(180);
        this.scale(-1, 1);
        myHelicopter.translate(0, 0);
        myHelicopter.rotate(180);
        myHelicopter.scale(0.175, 0.175);

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


        g.setColor(ColorUtil.rgb(255, 0, 0));

        myHelicopter.draw(g, originParent, originScreen);

        isEngineOff();
        isEngineOn();
        isEngineStarting();
        isEngineStopping();

        int x = originParent.getX() + lowerLeftInLocalSpace.getX();
        int xText = x + this.getDimension().getWidth() / 2;
        int y = originParent.getY() + lowerLeftInLocalSpace.getY();
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.setColor(color);
        g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_BOLD, SIZE_SMALL));
        g.drawString("F: " + fuel, xText, y + h);
        g.drawString("W: " + water, xText, (int)(y + h * 1.2));

        g.setTransform(gOrigXform);
    }

    public void setHelicopterState(HelicopterState newState) {
        this.helicopterState = newState;
    }

    public HelicopterState getHelicopterState() {
        return helicopterState;
    }

    public void startEngine() {
        this.setHelicopterState(helicopterStartingState);
    }

    public void stopEngine() {
        this.setHelicopterState(helicopterStoppingState);
    }

    public void accelerate() {

        if (this.getHelicopterState().toString() != "ready") {

            System.out.println("Helicopter Could not accelerate. The engine is not running");
        } else {

            int currSpeed = getSpeed();
            this.setSpeed(Math.min(++currSpeed, MAX_SPEED));
        }
    }

    public void brake() {

        int currSpeed = getSpeed();
        this.setSpeed(Math.max(--currSpeed, 0));
    }

    @Override
    public void steerLeft() {

        int currHeading = getHeading();
        currHeading -= 15;

        this.setHeading(currHeading);
    }

    @Override
    public void steerRight() {

        int currHeading = getHeading();
        currHeading += 15;

        this.setHeading(currHeading);
    }

    public void fight(Fire fire) {

        fire.shrink(water);
        dumpWater();
    }

    public boolean near(Fire fire) {
        return fire.near(this);
    }

    public int getWater() {
        return water;
    }

    public void dumpWater() {
        water = 0;
    }

    public void drinkFrom(River river) {

        if (canDrinkFrom(river) && this.getSpeed() <= 2) {
            water = Math.min(100 + water, MAX_WATER);
        }
    }

    boolean canDrinkFrom(River river) {
        return river.underneathHelicopter(this);
    }

    public boolean hasLandedAt(Helipad helipad) {
        return (this.getSpeed() == 0 && helipad.isUnderneath(this));
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(double change) {

        if (change == 1) {
            fuel = Math.max(fuel - (getSpeed() * getSpeed() + 5), 0);
        }
        if (change == 0.5) {
            fuel = Math.max(fuel - (getSpeed() * getSpeed() + 5) / 2, 0);
        }
    }

    public void isEngineStarting() {

        if (this.getHelicopterState().toString() == "starting") {

            myHelicopter.updateLocalTransforms(rotationalSpeed);
            rotationalSpeed += 1;
            if (rotationalSpeed == MAX_ROTOR_SPEED) {

                this.setHelicopterState(helicopterReadyState);
            }
            setFuel(0.5);
        }
    }

    public void isEngineOn() {

        if (this.getHelicopterState().toString() == "ready") {

            myHelicopter.updateLocalTransforms(rotationalSpeed);
            setFuel(1);
        }
    }

    public void isEngineStopping() {

        if (this.getHelicopterState().toString() == "stopping") {


            myHelicopter.updateLocalTransforms(rotationalSpeed);
            rotationalSpeed -= 1;
            if (rotationalSpeed == 0) {

                this.setHelicopterState(helicopterOffState);
            }
            setFuel(0);
        }
    }

        public void isEngineOff() {

            if (this.getHelicopterState().toString() == "off") {

                myHelicopter.updateLocalTransforms(0);
                setFuel(0);
            }
        }
    }