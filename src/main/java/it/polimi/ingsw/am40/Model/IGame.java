package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Network.IGameObserver;

public interface IGame {
    public void register(IGameObserver IGameObserver);

    public void unregister(IGameObserver IGameObserver);

    public void notifyObserver();
}
