package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a2.interfaces.IsDrawable;

public class River extends FixedGameObject  implements IsDrawable {

    public River(Dimension worldSize) {

        this.worldSize = worldSize;
        this.color = ColorUtil.BLUE;
        this.location = new Point2D(0, worldSize.getHeight() - worldSize.getHeight() / 4);
        this.dimension = new Dimension(worldSize.getWidth(), worldSize.getHeight() / 10);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        g.setColor(color);

        int x = containerOrigin.getX() + (int) location.getX();
        int y = containerOrigin.getY() - ((int) (location.getY()) + dimension.getHeight()) + worldSize.getHeight();
        int w = dimension.getWidth();
        int h = dimension.getHeight();

        g.drawRect(x, y, w, h, 5);

    }

    public boolean underneathHelicopter(Helicopter helicopter) {

        Point2D centerOfHelicopter = new Point2D(helicopter.getLocation().getX() + helicopter.getDimension().getWidth() / 2,
                                                helicopter.getLocation().getY() + helicopter.getDimension().getHeight() / 2);

        return (centerOfHelicopter.getX() > location.getX()
                && centerOfHelicopter.getX() < location.getX() + dimension.getWidth()
                && centerOfHelicopter.getY() > location.getY()
                && centerOfHelicopter.getY() < location.getY() + dimension.getHeight());
    }
}

