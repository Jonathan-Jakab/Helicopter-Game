package org.csc133.a4.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public interface IsSelectable {

    public void setSelected(boolean yesNo);
    public boolean isSelected();

    public boolean contains(Point mousePointer);
}
