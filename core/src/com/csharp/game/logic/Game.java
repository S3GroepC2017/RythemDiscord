package com.csharp.game.logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lars on 25-9-2017.
 */
public class Game
{
    private char[] nodeList;
    private int nodeListPosition = 0;
    private int numberOfSequencesLeft;
    private NodeGenerator nodeGenerator;

    public Game()
    {
        nodeGenerator = new NodeGenerator();
        numberOfSequencesLeft = 2;
    }

    public char[] getNodes(){
        nodeList = nodeGenerator.generateNode();
        return nodeList;
    }

    public KeyPressedResult checkKeyPressed(char keyPressed){
        if(nodeList[nodeListPosition] == keyPressed)
        {
            nodeListPosition++;
            return checkEndOfSequence();
        }
        nodeListPosition = 0;
        return KeyPressedResult.WRONG;
    }

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

    private KeyPressedResult checkEndOfGame()
    {
        if(numberOfSequencesLeft != 0)
        {
            return KeyPressedResult.SEQUENCE_FINISHED;
        }
        return KeyPressedResult.GAME_FINISHED;
    }
}
