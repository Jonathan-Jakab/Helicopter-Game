package org.csc133.a3.gameobjects;

import org.csc133.a3.interfaces.IsMovable;

public abstract class MovableGameObject extends GameObject implements IsMovable {

    private int speed;
    private int heading;

    public static final int MAX_SPEED = 10;

    public MovableGameObject() {

        super();
        this.heading = 0;
        this.speed = 0;
        this.myRotation.rotate(heading, 0, 0);
    }

    public int getSpeed() { return speed; }

    public void setSpeed(int newSpeed) {

        if (newSpeed > MAX_SPEED) {
            this.speed = 10;
        } else if (newSpeed < 0) {
            this.speed = 0;
        } else {
            this.speed = newSpeed;
        }
    }

    public int getHeading() { return heading; }

    public void setHeading(int newHeading) {

        if (newHeading < 0) {
            heading = newHeading + 360;
        } else if (newHeading > 360) {
            heading = newHeading - 360;
        } else {
            heading = newHeading;
        }

        this.resetRotation();
        this.rotate(180);
        this.rotate(-heading);
    }


    public void move(int elapsedTime) {

        double distance = (this.getSpeed() * 5 * (elapsedTime / 100));
        double angle = Math.toRadians(90 - this.getHeading());

        double deltaX = (int)(Math.cos(angle) * distance);
        double deltaY = (int)(Math.sin(angle) * distance);

        double newX = (int)(myTranslation.getTranslateX() + (deltaX));
        double newY = (int)(myTranslation.getTranslateY() + (deltaY));

        this.resetTranslation();
        this.setLocation(newX, newY);
    }
}