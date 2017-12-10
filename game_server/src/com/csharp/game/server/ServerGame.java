package com.csharp.game.server;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerGame extends UnicastRemoteObject implements IServerGame {
    private int noteListIndex;
    private final RemotePublisher remotePublisher;
    private final List<Player> players;
    private final int gameId;
    private static int nextId = 0;
    private Player hostPlayer = null;

    public ServerGame() throws RemoteException {
        remotePublisher = new RemotePublisher();
        remotePublisher.registerProperty("noteListIndex");
        remotePublisher.registerProperty("players");
        gameId = nextId;
        nextId++;
        players = new ArrayList<>();
        noteListIndex = 0;
    }

    @Override
    public int getGameId()
    {
        return gameId;
    }

    @Override
    public boolean joinPlayer(Player player)
    {
        if(!players.add(player))
        {
            return false;
        }
        try {
            if(hostPlayer == null)
            {
                hostPlayer = player;
            }
            remotePublisher.inform("players", null, players);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void subscribe(IRemotePropertyListener listener, String propertyName) {
        try {
            remotePublisher.subscribeRemoteListener(listener, propertyName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(Player localPlayer) {
        if(!localPlayer.equals(hostPlayer))
        {
            return;
        }
        NodeGenerator nodeGenerator = new NodeGenerator();
        for(Player player : players)
        {
            player.setNodeList(nodeGenerator.generateNode());
        }
        try {
            remotePublisher.inform("players", null, players);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void keyPressed(KeyPressedResult result) {
        switch (result) {
            case CORRECT:
                noteListIndex++;
                break;
            case WRONG:
                noteListIndex = 0;
                break;
            default:
                //TODO: Handle other outcome.
                break;
        }
        try {
            remotePublisher.inform("noteListIndex", null, noteListIndex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
