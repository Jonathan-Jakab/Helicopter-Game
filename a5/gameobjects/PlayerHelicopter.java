package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;

public class PlayerHelicopter extends Helicopter{

   private static PlayerHelicopter player;

    public static PlayerHelicopter getInstance(Dimension worldSize, Helipad helipad) {

        if(player == null) {

            player = new PlayerHelicopter(worldSize, helipad);
        }
        return player;
    }

    private PlayerHelicopter(Dimension worldSize, Helipad helipad) {

        super(worldSize, helipad, ColorUtil.YELLOW);
        this.setSpeed(0);
        this.setHeading(0);
        this.setFuel(25000);
    }

    public void restartInstance(Dimension worldSize, Helipad helipad) {

        player = null;
    }
}
