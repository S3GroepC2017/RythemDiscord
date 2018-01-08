package com.csharp.game.logic;

import com.csharp.sharedclasses.*;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game extends UnicastRemoteObject implements IGame
{
    private int nodeListPosition = 0;
    private List<Player> players;
    private KeyPressedResult lastKeyPressResult = KeyPressedResult.NONE;

    private Player localPlayer;
    private IServerGame serverGame;
    private final IAfterPosUpdateCallback uiCallback;
    private boolean started = false;


    //Constructor
    public Game(Player localPlayer, IServerGame serverGame, IAfterPosUpdateCallback uiCallback) throws RemoteException
    {
        this.localPlayer = localPlayer;
        this.serverGame = serverGame;
        this.uiCallback = uiCallback;
        players = new ArrayList<>();
        players.add(localPlayer);
//        System.out.println("local game created");
    }

    /**
     * Matches all the players in the list to the localPlayer.
     * If the Player objects are the same (as identified by the Username),
     * then the localPlayer will be replaced with the player from the list.
     * (So that if the player in the list has their notes defined the local player
     * will know them as well)
     *
     * @param players The pool of players the localPlayer should be chosen from.
     */
    private void setLocalPlayer(List<Player> players)
    {
        for (Player player : players)
        {
            if (localPlayer.equals(player))
            {
                localPlayer = player;
//                System.out.println("local player set: " + player.toString());
                return;
            }
        }
    }

    //Returns the  nodes of this current sequence.
    public List<Player> getNodes()
    {
        return players;
    }

    //Checks if the pressed key was correct.
    public KeyPressedResult checkKeyPressed(char keyPressed)
    {
        if (!started)
        {
            System.out.println("Received input but not started yet: " + keyPressed);
            return KeyPressedResult.NONE;
        }
        System.out.println("Logic received key press: " + keyPressed);
        //TODO: Add ALL the results.
        KeyPressedResult result;
        if (localPlayer.getNode(nodeListPosition) == keyPressed)
        {
            result = KeyPressedResult.CORRECT;
            if (localPlayer.getNode(nodeListPosition + 1) == '\u0000')
            {
                result = KeyPressedResult.SEQUENCE_FINISHED;
                nodeListPosition = 0;
            }
        }
        else {
            result = KeyPressedResult.WRONG;
        }

        try
        {
            serverGame.keyPressed(result);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public void beginGame()
    {
        if (serverGame == null || started)
        {
            return;
        }

        try
        {
            serverGame.startGame(localPlayer);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getNodeListPosition() {
        return nodeListPosition;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DTOClientUpdate update = (DTOClientUpdate) evt.getNewValue();

        if (update.getNewKeyPressResult() == KeyPressedResult.SEQUENCE_FINISHED
                || update.getNewKeyPressResult() == KeyPressedResult.STARTUP)
        {
            started = true;
        }
        else
        {
            nodeListPosition = update.getNewNodeListPosition();
            lastKeyPressResult = update.getNewKeyPressResult();
        }

        players = update.getNewPlayerList();
        setLocalPlayer(players);

        uiCallback.callback(update);
    }
}
