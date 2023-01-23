package org.csc133.a4.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;
import org.csc133.a4.interfaces.IsStrategy;

public class NonPlayerHelicopter extends Helicopter{

    private static NonPlayerHelicopter computer;
    private IsStrategy curStrategy;

    public static NonPlayerHelicopter getInstance(Dimension worldSize, Helipad helipad) {

        if(computer == null) {

            computer = new NonPlayerHelicopter(worldSize, helipad);
        }
        return computer;
    }

    private NonPlayerHelicopter(Dimension worldSize, Helipad helipad) {

        super(worldSize, helipad, ColorUtil.GREEN);
        this.setSpeed(0);
        this.setHeading(0);
        this.setFuel(25000);
    }

    public void restartInstance(Dimension worldSize, Helipad helipad) {

        computer = null;
    }

    @Override
    public void move(int elapsedTime) {

        invokeStrategy();

        if(getSpeed() < 2 ) {

            this.accelerate();
        }
        super.move(elapsedTime);
    }


    public IsStrategy getStrategy(){
        return curStrategy;
    }

    public void setStrategy(IsStrategy s){
        curStrategy = s;
    }

    public void invokeStrategy(){

        curStrategy.applyStrategy();
    }

}
