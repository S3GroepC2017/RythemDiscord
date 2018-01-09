package com.csharp.game.server;

import com.csharp.sharedclasses.*;
import com.sun.security.ntlm.Server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    private final transient Object synchroniseObject;

    private List<IServerGame> games;
    private Registry registry;

    public ServerManager(Registry registry) throws RemoteException
    {
        this.games = new ArrayList<>();
        this.registry = registry;

        synchroniseObject = new Object();
        new Thread(() ->
        {
            while(games != null)
            {
                for (int i = 0; i < games.size();)
                {
                    ServerGame serverGame = (ServerGame) games.get(i);
                    if (serverGame.getNumberOfPlayers() == 0)
                    {
                        synchronized (synchroniseObject)
                        {
                            games.remove(serverGame);
                        }
                    }
                    else
                    {
                        i++;
                    }
                }
            }
        }).start();
    }

    @Override
    public String createGame() throws RemoteException
    {
        try
        {
            IServerGame game = new ServerGame();

            synchronized (synchroniseObject){
                games.add(game);
            }

            String registryKey = "Game:" + game.getGameId();
            registry.rebind(registryKey, game);
            System.out.println("bound game to: " + registryKey + " GameID: " + game.getGameId());
            return registryKey;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
