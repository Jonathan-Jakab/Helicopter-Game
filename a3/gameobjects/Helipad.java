package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.interfaces.IsDrawable;

public class Helipad extends FixedGameObject implements IsDrawable {

    private int width;
    private int circleWidthOffSet;

    private Point lowerLeftInLocalSpace;

    public Helipad(Dimension worldSize) {

        width = worldSize.getWidth() / 15;
        circleWidthOffSet = width / 5;
        this.worldSize = worldSize;
        this.color = ColorUtil.GRAY;
        this.dimension = new Dimension(width, width);

        lowerLeftInLocalSpace = new Point(-dimension.getWidth() / 2, -dimension.getHeight() / 2);

        this.translate(worldSize.getWidth() / 2, worldSize.getHeight() / 9);
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
        g.drawRect(originParent.getX() + lowerLeftInLocalSpace.getX(),
                originParent.getY() + lowerLeftInLocalSpace.getY(),
                dimension.getWidth(), dimension.getHeight(), 5);

        g.drawArc(originParent.getX() + lowerLeftInLocalSpace.getX() + circleWidthOffSet / 2,
                originParent.getY() + lowerLeftInLocalSpace.getY() + circleWidthOffSet / 2,
                dimension.getWidth() - circleWidthOffSet, dimension.getHeight() - circleWidthOffSet, 0, 360);

        g.setTransform(gOrigXform);
    }

    public boolean isUnderneath(Helicopter helicopter) {

        double helicopterWidth = 20;
        double helicopterHeight = 20;
        double helicopterX = helicopter.getX() - helicopterWidth / 2;
        double helicopterY = helicopter.getY() - helicopterHeight / 2;

        double helipadWidth = dimension.getWidth();
        double helipadHeight = dimension.getHeight();
        double helipadX = this.getX() - helipadWidth / 2;
        double helipadY = this.getY() - helipadHeight / 2;

        return (helicopterX > helipadX &&
                (helicopterX + helicopterWidth) < (helipadX + helipadWidth) &&
                helicopterY > helipadY &&
                (helicopterY + helicopterHeight) < (helipadY + helipadHeight));
    }
}