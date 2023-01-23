package org.csc133.a5.strategies;

import org.csc133.a5.gameobjects.Fire;
import org.csc133.a5.gameobjects.GameObject;
import org.csc133.a5.gameobjects.NonPlayerHelicopter;
import org.csc133.a5.interfaces.IsStrategy;

public class FightStrategy implements IsStrategy {

    private NonPlayerHelicopter NPH;
    private Fire fire;

    private GameObject object;

    public FightStrategy(NonPlayerHelicopter NPH, GameObject object) {

        this.NPH = NPH;
        this.object = object;
    }

//    public FightStrategy(NonPlayerHelicopter NPH, Fire fire) {
//
//        this.NPH = NPH;
//        this.fire = fire;
//    }

    public void applyStrategy() {

        int dx, dy;


            dx = (int) (NPH.getX() - object.getX());
            dy = (int) (NPH.getY() - object.getY());


        int angle = (int)Math.toDegrees( Math.atan2(dx, dy) );
        angle += 180;

        if(angle >= 360) {

            angle -= 360;
        }
        NPH.setHeading( angle );
    }
}
