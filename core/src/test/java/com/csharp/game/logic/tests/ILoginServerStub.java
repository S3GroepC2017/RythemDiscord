package com.csharp.game.logic.tests;

import com.csharp.sharedclasses.ILogin;

import java.rmi.RemoteException;

public class ILoginServerStub implements ILogin
{
    public String lastUserName;
    public String lastHashedPassword;

    @Override
    public boolean checkLogin(String username, String hashedPassword) throws RemoteException
    {
        lastUserName = username;
        lastHashedPassword = hashedPassword;
        if(lastUserName == "Player1" && lastHashedPassword == "Player1")
        {
            return true;
        }
        return false;
    }
}
