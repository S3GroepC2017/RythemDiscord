package com.csharp.game.server;

import java.rmi.Remote;

public interface IServerGame extends Remote {
    void keyPressed(KeyPressedResult result);
    int getGameId();
}
