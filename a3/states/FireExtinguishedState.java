package org.csc133.a3.states;

import org.csc133.a3.gameobjects.Fire;
import org.csc133.a3.interfaces.FireState;

public class FireExtinguishedState implements FireState {

    public void updateFireState(Fire fire) {

        System.out.println("Fire Is Extinguished");
        fire.setFireState(this);
    }

    public String toString() {

        return "extinguished";
    }
}