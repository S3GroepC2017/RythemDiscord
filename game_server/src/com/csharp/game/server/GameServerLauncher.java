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
    private static final int GAME_SERVER_REGISTRY_PORT = 1099;
    private static final String GAME_SERVER_BINDING_NAME = "ServerManager";
    private Registry gameServerRegistry = null;
    private IServerManager serverManager;

    public static void main(String[] arg)
    {
        GameServerLauncher gameServer = new GameServerLauncher();
    }

    // Constructor
    public GameServerLauncher()
    {
        // Print port number for gameServerRegistry
        System.out.println("Server: Port number " + GAME_SERVER_REGISTRY_PORT);

        // Create gameServerRegistry at port number
        try
        {
            gameServerRegistry = LocateRegistry.createRegistry(GAME_SERVER_REGISTRY_PORT);
            System.out.println("Game Server Registry created on port:" + GAME_SERVER_REGISTRY_PORT);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot locate gameServerRegistry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            gameServerRegistry = null;
            System.exit(1);
        }

        try
        {
            serverManager = new ServerManager(gameServerRegistry);
            System.out.println("Server: server manager created");
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        try
        {
            gameServerRegistry.rebind(GAME_SERVER_BINDING_NAME, serverManager);
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind server manager");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            System.exit(1);
        }

        System.out.println("done");
    }
}
