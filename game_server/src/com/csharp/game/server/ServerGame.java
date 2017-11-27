package com.csharp.game.server;

import com.csharp.game.server.fontyspublisher.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerGame extends UnicastRemoteObject implements IServerGame {
    private int noteListIndex;
    private final RemotePublisher remotePublisher;
    private final List<Player> players;
    private final int gameid;
    private static int nextId = 0;

    public ServerGame() throws RemoteException {
        remotePublisher = new RemotePublisher();
        remotePublisher.registerProperty("noteListIndex");
        gameid = nextId;
        nextId++;
        players = new ArrayList<>();
        noteListIndex = 0;
    }

    @Override
    public int getGameId()
    {
        return gameid;
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
    }
}
