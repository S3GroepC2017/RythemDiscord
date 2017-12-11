package com.csharp.game.server;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.IServerManager;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    private List<IServerGame> games;
    private Registry registry;

    public ServerManager(Registry registry) throws RemoteException
    {
        this.games = new ArrayList<>();
        this.registry = registry;
    }

    @Override
    public String createGame() throws RemoteException
    {
        try
        {
            IServerGame game = new ServerGame();
            games.add(game);
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
