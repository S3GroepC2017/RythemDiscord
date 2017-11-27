package com.csharp.game.server;

import java.rmi.Remote;

public interface IServerManager extends Remote{
    String createGame();
}
