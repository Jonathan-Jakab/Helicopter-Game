package org.csc133.a5.interfaces;

public interface IsObservable {

    public void addObserver(IsObserver observer);
    public void notifyObservers();
}
