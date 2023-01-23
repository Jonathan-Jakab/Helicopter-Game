package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a2.interfaces.IsDrawable;

public class Helipad extends FixedGameObject implements IsDrawable {

    private int width;
    private int circleWidthOffSet;

    public Helipad(Dimension worldSize){

        width = worldSize.getWidth() / 15;
        circleWidthOffSet = width / 5;
        this.worldSize = worldSize;
        this.color = ColorUtil.GRAY;
        this.location = new Point2D(worldSize.getWidth() / 2 - width / 2, width/2);
        this.dimension = new Dimension(width, width);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        g.setColor(color);

        int x = containerOrigin.getX() + (int)location.getX();
        int y = containerOrigin.getY() - ((int)(location.getY()) + dimension.getHeight()) + worldSize.getHeight();

        g.drawRect(x, y, width, width, 5);
        g.drawArc(x + circleWidthOffSet/2, y+ circleWidthOffSet/2, width - circleWidthOffSet, width - circleWidthOffSet,
                0,360 );
    }

    public Point2D getLZ(){

        return new Point2D(location.getX() + dimension.getWidth()/2, location.getY() + dimension.getHeight()/2);
    }

    public boolean isUnderneath(Helicopter helicopter) {

        Point2D centerOfHelicopter = new Point2D(helicopter.getLocation().getX() + helicopter.getDimension().getWidth() / 2, helicopter.getLocation().getY() + helicopter.getDimension().getHeight() / 2);

        return (centerOfHelicopter.getX() > location.getX()
                && centerOfHelicopter.getX() < location.getX() + dimension.getWidth()
                && centerOfHelicopter.getY() > location.getY()
                && centerOfHelicopter.getY() < location.getY() + dimension.getHeight());
    }
}