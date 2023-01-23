package org.csc133.a5.states;

import org.csc133.a5.gameobjects.Helicopter;
import org.csc133.a5.interfaces.HelicopterState;

public class HelicopterStoppingState implements HelicopterState {

    public void updateHelicopterState(Helicopter helicopter) {

        System.out.println("Helicopter Engine Is Stopping");
        helicopter.setHelicopterState(this);
    }

    public String toString() {

        return "stopping";
    }
}