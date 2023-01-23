package org.csc133.a2.gameobjects;

import org.csc133.a2.interfaces.IsMovable;

public abstract class MovableGameObject extends GameObject implements IsMovable {

    private int speed;
    private int heading;

    public static final int MAX_SPEED = 10;

    public MovableGameObject() {
        super();
        this.speed = 0;
        this.heading = 0;
    }

    public int getSpeed(){ return speed; }

    public void setSpeed(int newSpeed) {

        if (newSpeed > 10) {
            this.speed = 10;
        } else if (newSpeed < 0) {
            this.speed = 0;
        } else {
            this.speed = newSpeed;
        }
    }

    public int getHeading(){ return heading; }

    public void setHeading(int newHeading) {

        while(newHeading >= 360.0) {
            newHeading -= 360.0;
        }
        while(newHeading < 0.0) {
            newHeading += 360.0;
        }
        heading = newHeading;;
    }


    public void move() {


        double radius = (this.getSpeed() * 5);
        double theta = Math.toRadians(270 - this.getHeading());

        int deltaX = (int)Math.round(radius * Math.cos(theta));
        int deltaY = (int)Math.round(radius * Math.sin(theta));

        int newX = (int) (location.getX() + (deltaX));
        int newY = (int) (location.getY() - (deltaY));

        if(newX >  this.worldSize.getWidth() && (heading != 0 || heading != 180)){

            newX = 0;
        }
        else if(newX < 0 && (heading != 0 || heading != 180)) {

            newX = this.worldSize.getWidth();
        }

        if(newY > this.worldSize.getHeight() && (heading != 90 || heading != 270) ) {

            newY = 0;
        }
        else if(newY < 0 && (heading != 90 || heading != 270) ) {

            newY = this.worldSize.getHeight();
        }

        this.setLocation(newX, newY);
    }
}