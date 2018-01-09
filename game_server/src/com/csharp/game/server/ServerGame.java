package com.csharp.game.server;

import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerGame extends UnicastRemoteObject implements IServerGame
{
    private int noteListIndex;
    private KeyPressedResult lastKeyPressResult = KeyPressedResult.NONE;
    private final RemotePublisher remotePublisher;
    private List<Player> players;
    private final int gameId;
    private static int nextId = 0;
    private Player hostPlayer = null;
    NodeGenerator nodeGenerator;
    private boolean started = false;

    private DTOClientUpdate dtoProperty;

    public ServerGame() throws RemoteException
    {
        remotePublisher = new RemotePublisher();
        /*
        remotePublisher.registerProperty("noteListIndex");
        remotePublisher.registerProperty("players");
        remotePublisher.registerProperty("lastKeyPressResult");
        */
        remotePublisher.registerProperty("dtoProperty");
        nodeGenerator = new NodeGenerator();
        gameId = nextId;
        ServerGame.nextId++;
        players = new ArrayList<>();
        noteListIndex = 0;
    }

    @Override
    public int getGameId() throws RemoteException
    {
        return gameId;
    }

    @Override
    public boolean joinPlayer(Player player) throws RemoteException
    {
        if (player == null || started)
        {
            return false;
        }
        if (players.size() >= 4)
        {
            return false;
        }
        if (players.contains(player) || !players.add(player))
        {
            return false;
        }

        boolean success = false;
        try
        {
            if (hostPlayer == null)
            {
                hostPlayer = player;
//                System.out.println("host set");
            }
            System.out.println("Player joined successfully");
            dtoProperty = new DTOClientUpdate(0, KeyPressedResult.NONE, players);
            remotePublisher.inform("dtoProperty", null, dtoProperty);
            System.out.println("Broadcast sent for new player");
            success = true;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            success = false;
        }
//        remotePublisher.inform("players", null, players);
        return success;
    }

    // TODO FIX THIS
    @Override
    public void subscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException
    {
        try
        {
            remotePublisher.subscribeRemoteListener(listener, propertyName);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(Player localPlayer) throws RemoteException
    {
        if (!localPlayer.equals(hostPlayer) || started)
        {
            return;
        }

        started = true;
        distributeNodes();

        dtoProperty = new DTOClientUpdate(0, KeyPressedResult.STARTUP, players);
        remotePublisher.inform("dtoProperty", null, dtoProperty);
    }

    // TODO FIX THIS
    @Override
    public void unsubscribe(IRemotePropertyListener listener, String propertyName) throws RemoteException
    {
        remotePublisher.unsubscribeRemoteListener(listener, propertyName);
    }

    @Override
    public void leave(Player player)
    {
        players.remove(player);

        if (hostPlayer.equals(player) && !players.isEmpty())
        {
            hostPlayer = players.get(0);
        }
    }

    private void distributeNodes()
    {
        players = nodeGenerator.generateNode(players);

//        System.out.println(players.get(0).toString());

//        System.out.println(players);
    }

    @Override
    public void keyPressed(KeyPressedResult result) throws RemoteException
    {
        if (!started)
        {
            return;
        }

        switch (result)
        {
            case CORRECT:
                noteListIndex++;
                lastKeyPressResult = KeyPressedResult.CORRECT;
                break;
            case WRONG:
                noteListIndex = 0;
                lastKeyPressResult = KeyPressedResult.WRONG;
                break;
            case SEQUENCE_FINISHED:
                noteListIndex = 0;
                lastKeyPressResult = KeyPressedResult.SEQUENCE_FINISHED;
                System.out.println("Sequence ended in the server");
                distributeNodes();
                System.out.println("New nodes were distributed");
                break;
            default:
                //TODO: Handle other outcome.
                break;
        }

        try
        {
            dtoProperty = new DTOClientUpdate(noteListIndex, lastKeyPressResult, players);

            remotePublisher.inform("dtoProperty", null, dtoProperty);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public int getNumberOfPlayers()
    {
        return players.size();
    }
}
