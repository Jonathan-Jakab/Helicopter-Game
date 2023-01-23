package org.csc133.a2.gameobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GameObjectCollection<T> extends GameObject implements Iterable<T>{

    CopyOnWriteArrayList<T> gameObjects;

    class GameObjectIterator implements Iterator<T>{

        int index = 0;

        @Override
        public boolean hasNext() {

            return index < gameObjects.size();
        }

        @Override
        public T next() {

            return gameObjects.get(index++);
        }
    }

    public GameObjectCollection(){

        gameObjects = new CopyOnWriteArrayList<>();
    }

    CopyOnWriteArrayList<T> getGameObjects(){

        return gameObjects;
    }

    public void add(T gameObject){

        gameObjects.add(gameObject);
    }

    public void remove(T gameObject){

        gameObjects.remove(gameObject);
    }

    public int getSize(){

        return gameObjects.size();
    }

    @Override
    public Iterator<T> iterator(){

        return new GameObjectIterator();
    }


}
