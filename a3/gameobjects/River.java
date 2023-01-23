package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Rectangle;
import org.csc133.a3.interfaces.IsDrawable;

public class River extends FixedGameObject implements IsDrawable {

    private Point lowerLeftInLocalSpace;

    public River(Dimension worldSize) {

        this.worldSize = worldSize;
        this.color = ColorUtil.BLUE;
        this.dimension = new Dimension(worldSize.getWidth(), worldSize.getHeight() / 10);

        lowerLeftInLocalSpace = new Point(-dimension.getWidth() / 2, -dimension.getHeight() / 2);

        this.translate(worldSize.getWidth() / 2, worldSize.getHeight() / 4 * 3);
        this.rotate(0);
        this.scale(1, 1);
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
        g.drawRect(originParent.getX() + lowerLeftInLocalSpace.getX(),
                originParent.getY() + lowerLeftInLocalSpace.getY(),
                dimension.getWidth(), dimension.getHeight());

        g.setTransform(gOrigXform);
    }

    public boolean underneathHelicopter(Helicopter helicopter) {

        double helicopterWidth = 10;
        double helicopterHeight = 10;
        double helicopterX = helicopter.getX() - helicopterWidth / 2;
        double helicopterY = helicopter.getY() - helicopterHeight / 2;

        double riverWidth = dimension.getWidth();
        double riverHeight = dimension.getHeight();
        double riverX = this.getX() - riverWidth / 2;
        double riverY = this.getY() - riverHeight / 2;

        Rectangle r1 = new Rectangle((int) helicopterX, (int) helicopterY, (int)(helicopterWidth), (int)(helicopterHeight));
        Rectangle r2 = new Rectangle((int) riverX, (int) riverY, (int)(riverWidth), (int)(riverHeight));

        if (r1.intersects(r2)) {

            return true;
        }
        return false;
    }
}