package it.polimi.ingsw.am40.Model;

import it.polimi.ingsw.am40.Network.VirtualView;

/**
 * An interface implemented by the game, which is observable
 */
public interface IGame {
    /**
     * It registers the parameter as an observer
     * @param virtualView the observer
     */
    public void register(VirtualView virtualView);

    /**
     * It unregisters the parameter which was an observer
     * @param virtualView the observer to be unregistered
     */
    public void unregister(VirtualView virtualView);

    /**
     * It notifies all the observers of the updates of the game
     * @param turnPhase the turn phase
     */
    public void notifyObservers(TurnPhase turnPhase);
}

