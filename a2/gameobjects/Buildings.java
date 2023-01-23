package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Buildings extends GameObjectCollection<Building>{
    public Buildings(){

        super();
        this.color = ColorUtil.GRAY;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        for(Building building : getGameObjects()){
            building.draw(g, containerOrigin);
        }
    }
}
