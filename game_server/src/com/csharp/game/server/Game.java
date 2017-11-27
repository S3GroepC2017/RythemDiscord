package com.csharp.game.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game
{
    private int nodeListPosition = 0;
    private List<Player> players;
    private Player localPlayer;

    //Constructor
    public Game()
    {
        players = new ArrayList<Player>();
    }

    public void setLocalPlayer(Player localPlayer)
    {
        this.localPlayer = localPlayer;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;

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
        if (localPlayer.getNode(nodeListPosition) == keyPressed)
        {
            nodeListPosition++;

            // TODO: SEND MESSAGE TO SERVER
            return KeyPressedResult.CORRECT;
        }

        // TODO: SEND MESSAGE TO SERVER
        return KeyPressedResult.WRONG;

        /*
        if(nodeList[nodeListPosition] == keyPressed)
        {
            nodeListPosition++;
            return checkEndOfSequence();
        }
        nodeListPosition = 0;
        */
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
