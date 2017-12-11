package com.csharp.game.logic;

import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.IGame;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game extends UnicastRemoteObject implements IGame, Remote
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
        super();
        this.localPlayer = localPlayer;
        this.serverGame = serverGame;
        this.uiCallback = uiCallback;
        players = new ArrayList<Player>();
        players.add(localPlayer);
        System.out.println("local game created");
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
                System.out.println("local player set: " + player.toString());
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

        /*
        if(nodeList[nodeListPosition] == keyPressed)
        {
            nodeListPosition++;
            return checkEndOfSequence();
        }
        nodeListPosition = 0;
        */
    }

    public void beginGame()
    {
        if (serverGame == null)
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
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        System.out.println("PROP CHANGE");
        if (evt.getPropertyName().equals("players"))
        {
            this.players = (ArrayList<Player>) evt.getNewValue();
            setLocalPlayer(players);
            if (players.get(0).getNode(0) != '\u0000' && !started)
            {
                //TODO: BeginGame!!!!
                started = true;
                System.out.println("Players have nodes: " + Arrays.toString(players.get(0).getNodeList()));
            }
            else
            {
                if (started)
                {
                    System.out.println("Game started already");
                    return;
                }
                System.out.println("New player joined, these are the players now: " + players);
            }
        }
        else if (evt.getPropertyName().equals("noteListIndex"))
        {
            this.nodeListPosition = (Integer) evt.getNewValue();
            System.out.println("New node list position: " + nodeListPosition);
            //TODO: HANDLE THE LAST KEY IN THE SEQUENCE
            // if(localPlayer.getNode(nodeListPosition) == '\u0000')

        }
        else if (evt.getPropertyName().equals("lastKeyPressResult"))
        {
            lastKeyPressResult = (KeyPressedResult) evt.getNewValue();
            uiCallback.afterCallback(this.nodeListPosition, lastKeyPressResult);
        }

    }

    /*
    // TODO: remove if implemented elsewhere

    //Checks if the end of the Sequence is reached.
    private KeyPressedResult checkEndOfSequence()
    {
        if(nodeListPosition != nodeList.length)
        {
            return KeyPressedResult.CORRECT;
        }
        nodeListPosition = 0;
        numberOfSequencesLeft--;
        return checkEndOfGame();
    }

    //Checks if the end of the game is reached
    private KeyPressedResult checkEndOfGame()
    {
        if(numberOfSequencesLeft != 0)
        {
            return KeyPressedResult.SEQUENCE_FINISHED;
        }
        return KeyPressedResult.GAME_FINISHED;
    }
    */
}
