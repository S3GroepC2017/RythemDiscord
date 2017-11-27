package com.csharp.game.logic;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.*;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game extends UnicastRemoteObject implements IRemotePropertyListener
{
    private int nodeListPosition = 0;
    private List<Player> players;
    private Player localPlayer;
    private IServerGame serverGame;
    private boolean started = false;

    //Constructor
    public Game(Player localPlayer, IServerGame serverGame) throws RemoteException {
        super();
        this.localPlayer = localPlayer;
        this.serverGame = serverGame;
        players = new ArrayList<Player>();
    }

    private void setLocalPlayer(List<Player> players)
    {
        for (Player player : players)
        {
            if (localPlayer.equals(player))
            {
                localPlayer = player;
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
        if(!started)
        {
            return KeyPressedResult.NONE;
        }
        //TODO: Add ALL the results.
        KeyPressedResult result = KeyPressedResult.NONE;
        if (localPlayer.getNode(nodeListPosition) == keyPressed)
        {
            nodeListPosition++;
            result = KeyPressedResult.CORRECT;
        }
        else {
            result = KeyPressedResult.WRONG;
        }
        serverGame.keyPressed(result);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        if(evt.getPropertyName().equals("players"))
        {
            this.players = (ArrayList<Player>) evt.getNewValue();
            setLocalPlayer(players);
            if(players.get(0).getNode(0) != ' ')
            {
                //TODO: BeginGame!!!!
                started = true;
            }
        }
        else if(evt.getPropertyName().equals("noteListIndex"))
        {
            this.nodeListPosition = (Integer) evt.getNewValue();
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
