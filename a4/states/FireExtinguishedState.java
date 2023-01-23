package org.csc133.a4.states;

import org.csc133.a4.gameobjects.Fire;
import org.csc133.a4.interfaces.FireState;

public class FireExtinguishedState implements FireState {

    public void updateFireState(Fire fire) {

        System.out.println("Fire Is Extinguished");
        fire.setFireState(this);
    }

    public String toString() {

        return "extinguished";
    }
}