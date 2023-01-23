package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;
import org.csc133.a3.interfaces.FireState;
import org.csc133.a3.interfaces.IsDrawable;
import org.csc133.a3.states.FireBurningState;
import org.csc133.a3.states.FireExtinguishedState;
import org.csc133.a3.states.FireUnstartedState;

public class Fire extends FixedGameObject implements IsDrawable {

    private FireState fireState;

    private FireUnstartedState fireUnstartedState;
    private FireBurningState fireBurningState;
    private FireExtinguishedState fireExtinguishedState;

    private Point lowerLeftInLocalSpace;

    public Fire(Dimension worldSize, int fireSize) {

        fireUnstartedState = new FireUnstartedState();
        fireBurningState = new FireBurningState();
        fireExtinguishedState = new FireExtinguishedState();

        fireState = fireUnstartedState;

        int size = fireSize;

        this.worldSize = worldSize;
        this.color = ColorUtil.MAGENTA;
        this.dimension = new Dimension(size, size);
        lowerLeftInLocalSpace = new Point(-dimension.getWidth() / 2, -dimension.getHeight() / 2);

        this.translate(0, 0);
        this.rotate(180);
        this.scale(-1, 1);
    }

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy();

        gXform.translate(originScreen.getX(), originScreen.getY());

        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());

        gXform.translate(-originScreen.getX(), -originScreen.getY());
        g.setTransform(gXform);

        g.setColor(color);
        if (getFireState().toString() == "burning") {

            int x = originParent.getX() + lowerLeftInLocalSpace.getX() - dimension.getWidth() / 2;
            int y = originParent.getY() + lowerLeftInLocalSpace.getY() - dimension.getHeight() / 2;

            int w = dimension.getWidth();
            int h = dimension.getHeight();

            g.fillArc(x, y, w, h, 0, 360);
            g.drawString(String.valueOf(w), x + w, y + w);
        }
        g.setTransform(gOrigXform);
    }

    public void setFireState(FireState newState) { this.fireState = newState; }

    public FireState getFireState() { return this.fireState; }

    public boolean isStillBurning() {
        return getFireState().toString() == "burning";
    }

    public void shrink(int water) {

        int sizeChange = water / 5;
        if (dimension.getWidth() - sizeChange > 0) {
            dimension.setWidth(dimension.getWidth() - sizeChange);
            dimension.setHeight(dimension.getHeight() - sizeChange);
        } else {
            this.setFireState(fireExtinguishedState);
        }
    }

    public void grow() {

        if (getFireState().toString() == "burning") {
            dimension.setWidth(dimension.getWidth() + 2);
            dimension.setHeight(dimension.getHeight() + 2);
        }
    }

    public boolean near(Helicopter helicopter) {

        double helicopterWidth = 10;
        double helicopterHeight = 10;
        double helicopterX = helicopter.getX() - helicopterWidth / 2;
        double helicopterY = helicopter.getY() - helicopterHeight / 2;

        double fireWidth = dimension.getWidth();
        double fireHeight = dimension.getHeight();
        double fireX = this.getX() - fireWidth / 2;
        double fireY = this.getY() - fireHeight / 2;

        Rectangle r1 = new Rectangle((int) helicopterX, (int) helicopterY, (int)(helicopterWidth), (int)(helicopterHeight));
        Rectangle r2 = new Rectangle((int) fireX, (int) fireY, (int)(fireWidth), (int)(fireHeight));

        if (r1.intersects(r2)) {

            return true;
        }
        return false;
    }

    public void start(Point2D location) {

        this.translate(location.getX(), location.getY());
        this.setFireState(fireBurningState);
    }
}