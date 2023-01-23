package org.csc133.a5.interfaces;

import com.codename1.ui.geom.Point;

public interface IsSelectable {

    public void setSelected(boolean yesNo);
    public boolean isSelected();

    public boolean contains(Point mousePointer);
}
