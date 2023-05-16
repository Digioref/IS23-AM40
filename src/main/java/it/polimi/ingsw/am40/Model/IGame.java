package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Network.VirtualView;

public interface IGame {
    public void register(VirtualView virtualView);

    public void unregister(VirtualView virtualView);

    public void notifyObservers(TurnPhase turnPhase);
}

