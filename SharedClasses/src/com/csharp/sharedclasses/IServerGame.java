package com.csharp.sharedclasses;

import com.csharp.sharedclasses.fontyspublisher.*;

import java.rmi.Remote;

public interface IServerGame extends Remote {
    void keyPressed(KeyPressedResult result);
    boolean joinPlayer(Player player);
    int getGameId();
    void subscribe(IRemotePropertyListener listener, String propertyName);
    void startGame(Player player);
}
