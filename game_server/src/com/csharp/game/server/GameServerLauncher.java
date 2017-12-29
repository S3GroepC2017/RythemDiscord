package com.csharp.game.server;

import com.csharp.sharedclasses.IServerManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */

public class GameServerLauncher
{
    private static final String hostAddress = "192.168.0.100";
    private static final int portNumber = 1099;
    private Registry registry = null;
    private final String bindingName = "ServerManager";
    private IServerManager serverManager;

    public static void main(String[] arg)
    {
        GameServerLauncher gameServer = new GameServerLauncher();
    }

    // Constructor
    public GameServerLauncher()
    {


        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create registry at port number
        try
        {
            registry = LocateRegistry.getRegistry(hostAddress, portNumber);
            System.out.println("Server: Registry located on: " + hostAddress + " with port number " + portNumber);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot locate registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        try
        {
            serverManager = new ServerManager(registry);
            System.out.println("Server: server manager created");
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        try
        {
            registry.rebind(bindingName, serverManager);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind server manager");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
}
