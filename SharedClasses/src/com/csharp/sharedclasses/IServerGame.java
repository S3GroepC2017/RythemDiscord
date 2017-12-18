package com.csharp.sharedclasses;

import com.csharp.sharedclasses.fontyspublisher.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame extends Remote
{
    void keyPressed(KeyPressedResult result) throws RemoteException;
    boolean joinPlayer(Player player) throws RemoteException;
    int getGameId() throws RemoteException;
    void subscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException;
    void startGame(Player player) throws RemoteException;

    /**
     * Used to unsubscirbe from properties of the publisher
     * @param listener The listener that should be unsubscribed
     * @param propertyName The target property to unsubscribe
     * @throws RemoteException
     */
    void unsubscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException;

    /**
     * Before calling this the client should have unsubscribed from all active subscriptions.
     * Will remove the player from the game.
     * @param player
     */
    void leave(Player player);
}
