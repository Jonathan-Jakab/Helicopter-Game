package org.csc133.a2.gameobjects;

public abstract class FixedGameObject extends GameObject {

    FixedGameObject() {

        super();
    }

    public void move(int elapsedTime) {
        // Override the move method
        // fixed objects cannot move
    }
}




