package org.csc133.a5.strategies;

import org.csc133.a5.gameobjects.NonPlayerHelicopter;
import org.csc133.a5.interfaces.IsStrategy;

public class AvoidStrategy implements IsStrategy {

    private NonPlayerHelicopter NPH;

    private double targetHeading;

    public AvoidStrategy(NonPlayerHelicopter NPH) {

        this.NPH = NPH;
    }

    public void applyStrategy() {

        int speed = NPH.getSpeed();

//        for(int i = 0; i < 180; i += 15) {
//
//            NPH.steerRight();
//        }

        for(int i = 0; i < speed; i++) {

            NPH.brake();
        }
    }
}
