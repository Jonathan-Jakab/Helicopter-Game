package org.csc133.a4.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface IsDrawable {

    public void draw(Graphics g, Point originParent, Point originScreen);
}