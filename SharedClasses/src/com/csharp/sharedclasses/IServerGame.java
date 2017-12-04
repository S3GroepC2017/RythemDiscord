package com.csharp.sharedclasses;

import com.csharp.sharedclasses.fontyspublisher.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerGame extends Remote {
    void keyPressed(KeyPressedResult result) throws RemoteException;
    boolean joinPlayer(Player player) throws RemoteException;
    int getGameId() throws RemoteException;
    void subscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException;
    void startGame(Player player) throws RemoteException;
}
