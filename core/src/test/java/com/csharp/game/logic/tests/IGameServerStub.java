package com.csharp.game.logic.tests;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.IServerManager;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.IRemotePropertyListener;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IGameServerStub implements IServerGame, IServerManager
{
    public KeyPressedResult lastReceivedResultKeyPressResult = KeyPressedResult.NONE;
    public List<Player> playersJoined = new ArrayList<>();
    public List<Player> playersLeft = new ArrayList<>();
    public int gameId = 3;
    public Map<IRemotePropertyListener, String> propertyListeners = new HashMap<>();
    public Map<IRemotePropertyListener, String> unsubscribedPropertyListeners = new HashMap<>();
    public Player playerRequestedStart = null;
    public boolean gameCreated = false;
    public String fakeGameKey = "Game:3";

    @Override
    public void keyPressed(KeyPressedResult result)
    {
        lastReceivedResultKeyPressResult = result;
    }

    @Override
    public boolean joinPlayer(Player player)
    {
        return false;
    }

    @Override
    public int getGameId()
    {
        return gameId;
    }

    @Override
    public void subscribe(IRemotePropertyListener listener, String propertyName)
    {
        propertyListeners.put(listener, propertyName);
    }

    @Override
    public void startGame(Player player)
    {
        playerRequestedStart = player;
    }

    @Override
    public void unsubscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException
    {
        propertyListeners.remove(listener, propertyName);
        unsubscribedPropertyListeners.put(listener, propertyName);
    }

    @Override
    public void leave(Player player) throws RemoteException
    {
        playersJoined.remove(player);
        playersLeft.add(player);
    }

    @Override
    public String createGame() throws RemoteException
    {
        gameCreated = true;
        return fakeGameKey;
    }
}
