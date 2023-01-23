package org.csc133.a4.states;

import org.csc133.a4.gameobjects.Helicopter;
import org.csc133.a4.interfaces.HelicopterState;

public class HelicopterReadyState implements HelicopterState {

    public void updateHelicopterState(Helicopter helicopter) {

        System.out.println("Helicopter Engine Is Ready");
        helicopter.setHelicopterState(this);
    }

    public String toString() {

        return "ready";
    }
}