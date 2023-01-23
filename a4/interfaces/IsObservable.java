package org.csc133.a4.interfaces;

import org.csc133.a4.gameobjects.Fire;

public interface IsObservable {

    public void addObserver(IsObserver observer);
    public void notifyObservers();
}
