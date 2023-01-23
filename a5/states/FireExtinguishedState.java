package org.csc133.a5.states;

import org.csc133.a5.gameobjects.Fire;
import org.csc133.a5.interfaces.FireState;

public class FireExtinguishedState implements FireState {

    public void updateFireState(Fire fire) {

        System.out.println("Fire Is Extinguished");
        fire.setFireState(this);
    }

    public String toString() {

        return "extinguished";
    }
}