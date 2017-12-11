package com.csharp.sharedclasses;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerManager extends Remote{
    String createGame() throws RemoteException;
}
