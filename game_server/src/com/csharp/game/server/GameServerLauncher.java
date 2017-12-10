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
    private final int portNumber = 1099;
    private Registry registry = null;
    private final String bindingName = "ServerManager";
    private IServerManager serverManager;

    public static void main(String[] arg)
    {

    }

    // Constructor
    public GameServerLauncher()
    {
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create registry at port number
        try
        {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Create new instance of server manager.
        //try {
        serverManager = new ServerManager(registry);
        System.out.println("Server: Student administration created");
        //}
        /*catch (RemoteException ex) {
            System.out.println("Server: Cannot create student administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            serverManager = null;
        }*/

        // Bind student administration using registry
        try
        {
            registry.rebind(bindingName, serverManager);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind student administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
}
