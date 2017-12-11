package com.csharp.game.logic;

import com.csharp.sharedclasses.ILogin;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientLoginServer {

    private String hostIP = "192.168.247.131";
//    private String hostIP = "10.0.0.3";
    private int port = 1099;
    private ILogin loginserver;
    private Registry registry = null;

//    public ILogin getLoginServer(){
//        return loginserver;
//    }

    public ClientLoginServer(){
        try
        {
            registry = LocateRegistry.getRegistry(hostIP, port);
        } catch (RemoteException ex)
        {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        if (registry != null)
        {
            System.out.println("Client: Registry located");
        } else
        {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

        if (registry != null)
        {
            try
            {
                loginserver = (ILogin) registry.lookup("LoginServer");
            } catch (RemoteException ex)
            {
                System.out.println("Client: Cannot bind loginserver");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                loginserver = null;
            } catch (NotBoundException ex)
            {
                System.out.println("Client: Cannot bind loginserver");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                loginserver = null;
            }

        }

        if (loginserver != null)
        {
            System.out.println("Client: loginserver bound");
        } else
        {
            System.out.println("Client: loginserver is null pointer");
        }
    }

    public boolean login(String userName, String unHashedPassword){

        boolean succes = false;
        //TODO: HashPassword here

        try {
            succes = loginserver.checkLogin(userName, unHashedPassword);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            return succes;
        }

    }


}
