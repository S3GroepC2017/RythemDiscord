package com.csharp.game.server;

import com.csharp.game.server.fontyspublisher.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerGame extends UnicastRemoteObject implements IServerGame {
    private int noteListIndex;
    private final RemotePublisher remotePublisher;


    public ServerGame() throws RemoteException {
        remotePublisher = new RemotePublisher();
        remotePublisher.registerProperty("noteListIndex");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("GameServerPublisher", remotePublisher);

        noteListIndex = 0;
    }

    @Override
    public void keyPressed(KeyPressedResult result) {
        switch (result) {
            case KeyPressedResult.CORRECT:
                noteListIndex++;
                break;
            case KeyPressedResult.WRONG:
                noteListIndex = 0;
                break;
            default:
                //TODO: Handle other outcome.
                break;
        }
    }
}
