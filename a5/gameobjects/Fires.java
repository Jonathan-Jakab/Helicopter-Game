package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Fires extends GameObjectCollection < Fire > {

    public Fires() {

        super();
        this.color = ColorUtil.MAGENTA;
    }

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {

        for (Fire fire: getGameObjects()) {
            fire.draw(g, originParent, originScreen);
        }
    }
}