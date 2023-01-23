package org.csc133.a5.gameobjects;

import org.csc133.a5.interfaces.IsSelectable;

public abstract class FixedGameObject extends GameObject implements IsSelectable {

    private boolean selected;
    FixedGameObject() {

        super();
    }

    public void move(int elapsedTime) {
    }

    @Override
    public void setSelected(boolean yesNo) {

        selected = yesNo;
    }

    @Override
    public boolean isSelected() {

        return selected;
    }
}