package com.csharp.game.logic;

import jdk.nashorn.internal.objects.annotations.Constructor;
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

    //Constructor
    public Game()
    {
        nodeGenerator = new NodeGenerator();
        numberOfSequencesLeft = 2;
    }

    //Returns the current nodes of this sequence.
    public char[] getNodes(){
        nodeList = nodeGenerator.generateNode();
        return nodeList;
    }

    //Checks if the pressed key was correct.
    public KeyPressedResult checkKeyPressed(char keyPressed){
        if(nodeList[nodeListPosition] == keyPressed)
        {
            nodeListPosition++;
            return checkEndOfSequence();
        }
        nodeListPosition = 0;
        return KeyPressedResult.WRONG;
    }

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
}
