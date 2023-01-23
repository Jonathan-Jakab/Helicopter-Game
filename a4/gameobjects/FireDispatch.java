package org.csc133.a4.gameobjects;

import com.codename1.ui.Transform;
import org.csc133.a4.interfaces.IsObservable;
import org.csc133.a4.interfaces.IsObserver;

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


    //If Fire is selected then turn on the helicopter engine and start flying.
    //Set the 1st point to be the helipad, 2nd the river, and 3rd to be whichever fire is selected. The 4th point should be the river again

    private double t = 0;
    private double speed = 1;
    private int heading;

    @Override
    public void update(IsObservable o) {

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

