package org.csc133.a3.states;

import org.csc133.a3.gameobjects.Fire;
import org.csc133.a3.interfaces.FireState;

public class FireBurningState implements FireState {

    public void updateFireState(Fire fire) {

        System.out.println("Fire Is Burning");
        fire.setFireState(this);
    }

    public String toString() {

        return "burning";
    }
}