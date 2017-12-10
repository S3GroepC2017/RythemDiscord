package com.csharp.game.server;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.IServerManager;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ServerManager implements IServerManager
{
    private List<IServerGame> games;
    private Registry registry;

    public ServerManager(Registry registry)
    {
        this.games = new ArrayList<>();
        this.registry = registry;
    }

    @Override
    public String createGame()
    {
        try
        {
            IServerGame game = new ServerGame();
            games.add(game);
            String registryKey = "Game:" + game.getGameId();
            registry.rebind(registryKey, game);
            return registryKey;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
