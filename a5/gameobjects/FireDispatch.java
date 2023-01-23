package org.csc133.a5.gameobjects;

import com.codename1.ui.Transform;
import org.csc133.a5.interfaces.IsObservable;
import org.csc133.a5.interfaces.IsObserver;

import java.util.concurrent.CopyOnWriteArrayList;


///////////////////////////////////////////////////////////////////////////////////////
//////////////WERE NOT IMPLEMENTED DUE TO TIME AND DIFFICULTY CONSTRAINTS//////////////
///////////////////////////////////////////////////////////////////////////////////////


public class FireDispatch implements IsObserver {

    private Fire fire;
    public static CopyOnWriteArrayList<FireDispatch> fireDispatches;

    private Transform myTranslation, myRotation, myScale;

    public FireDispatch(CopyOnWriteArrayList<GameObject> gameObjects) {

        fireDispatches = new CopyOnWriteArrayList<>();
    }

    private double t = 0;
    private double speed = 1;
    private int heading;

    @Override
    public void update(IsObservable o) {

        if(o instanceof Fire) {
            //ADD UPDATER TO CHANGE THE LOCATION OF THE BEZIER CURVE
        }

    }

    public void rotate(double degrees) {
        myRotation.rotate((float) Math.toRadians(degrees), 0, 0);
    }

    public void scale(double sx, double sy) {
        myScale.scale((float) sx, (float) sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.translate((float) tx, (float) ty);
    }


}

