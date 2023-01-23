package org.csc133.a5.states;

import org.csc133.a5.gameobjects.Fire;
import org.csc133.a5.interfaces.FireState;

public class FireBurningState implements FireState {

    public void updateFireState(Fire fire) {

        System.out.println("Fire Is Burning");
        fire.setFireState(this);
    }

    public String toString() {

        return "burning";
    }
}