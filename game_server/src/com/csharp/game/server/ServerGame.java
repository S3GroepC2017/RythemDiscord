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
    private final int gameid;
    private static int nextId = 0;
    private Player hostPlayer = null;
    NodeGenerator nodeGenerator;

    public ServerGame() throws RemoteException {
        remotePublisher = new RemotePublisher();
        remotePublisher.registerProperty("noteListIndex");
        remotePublisher.registerProperty("players");
        nodeGenerator = new NodeGenerator();
        gameid = nextId;
        ServerGame.nextId++;
        players = new ArrayList<>();
        noteListIndex = 0;
    }

    @Override
    public int getGameId() throws RemoteException
    {
        return gameid;
    }

    @Override
    public boolean joinPlayer(Player player)throws RemoteException
    {
        if(players.contains(player) || !players.add(player))
        {
            return false;
        }

        boolean success = false;
        try {
            if(hostPlayer == null)
            {
                hostPlayer = player;
                System.out.println("host set");
            }
            System.out.println("Player joined successfully");
            remotePublisher.inform("players", null, players);
            System.out.println("Broadcast sent for new player");
            success = true;
        } catch (RemoteException e) {
            e.printStackTrace();
            success = false;
        }
        remotePublisher.inform("players", null, players);
        return success;
    }

    @Override
    public void subscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException {
        try {
            remotePublisher.subscribeRemoteListener(listener, propertyName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(Player localPlayer) throws RemoteException {
        if(!localPlayer.equals(hostPlayer))
        {
            return;
        }
        distributeNodes();
    }

    private void distributeNodes()
    {
        for(Player player : players)
        {
            player.setNodeList(nodeGenerator.generateNode());
        }

        try {
            remotePublisher.inform("players", null, players);
            System.out.println("Broadcast sent with nodes");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println(players);
    }

    @Override
    public void keyPressed(KeyPressedResult result) throws RemoteException {
        switch (result) {
            case CORRECT:
                noteListIndex++;
                break;
            case WRONG:
                noteListIndex = 0;
                break;
            case SEQUENCE_FINISHED:
                noteListIndex = 0;
                distributeNodes();
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
