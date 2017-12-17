package com.csharp.sharedclasses;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {

    boolean checkLogin(String username, String hashedPassword) throws RemoteException;

}
