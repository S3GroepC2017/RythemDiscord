package com.csharp.sharedclasses;

import java.rmi.Remote;

public interface IServerManager extends Remote{
    String createGame();
}
