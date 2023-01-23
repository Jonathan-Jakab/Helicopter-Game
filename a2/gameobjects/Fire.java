package org.csc133.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;
import org.csc133.a2.interfaces.IsDrawable;

public class Fire extends FixedGameObject implements IsDrawable {

    boolean fireHasStarted;


    public Fire(Dimension worldSize, int fireSize) {

        int size = fireSize;

        this.worldSize = worldSize;
        this.color = ColorUtil.MAGENTA;
        this.location = new Point2D(0, 0);
        this.dimension = new Dimension(size, size);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {

        g.setColor(color);

        if (isStillBurning() && fireHasStarted) {

            int x = containerOrigin.getX() + (int) location.getX();
            int y = containerOrigin.getY() - ((int)(location.getY()) + dimension.getHeight()) + worldSize.getHeight();

            int w = dimension.getWidth();
            int h = dimension.getHeight();

            g.fillArc(x, y, w, h, 0, 360);
            g.drawString(String.valueOf(w), x + w, y + w);
        }
    }

    public boolean isStillBurning() {
            return dimension.getWidth() > 0;
        }


    public void shrink(int water){

        int sizeChange = water / 5;
        if (dimension.getWidth() > 0) {
            dimension.setWidth(dimension.getWidth() - sizeChange);
            dimension.setHeight(dimension.getHeight() - sizeChange);
            location.setX(location.getX() + sizeChange / 2);
            location.setY(location.getY() + sizeChange / 2);
        }
    }

    public void grow() {

        if (dimension.getWidth() > 0 && fireHasStarted) {
            dimension.setWidth(dimension.getWidth() + 2);
            dimension.setHeight(dimension.getHeight() + 2);
            location.setX(location.getX() - 1);
            location.setY(location.getY() - 1);
        }
    }

    public boolean near(Helicopter helicopter) {

        Rectangle r1 = new Rectangle((int)helicopter.getLocation().getX(), (int)helicopter.getLocation().getY(), helicopter.getDimension().getWidth(), helicopter.getDimension().getHeight());
        Rectangle r2 = new Rectangle((int)location.getX(), (int)location.getY(), dimension.getWidth(), dimension.getHeight());

        if (r1.intersects(r2)){
            return true;
        }
        return false;
    }

    public void start(Point2D location){
        this.location = location;
        this.fireHasStarted = true;
    }
}