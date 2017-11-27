package com.csharp.game.logic.tests;

import com.csharp.game.logic.Game;
import com.csharp.game.logic.KeyPressedResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Lars on 30-10-2017.
 */
public class GameTest
{
    @Test
    public void getNodes() throws Exception
    {
        Game game = new Game();
        //TODO: replace character array with player list.
        char[] nodes = new char[10];
        //char[] nodes = game.getNodes();
        Assert.assertFalse("The node array was null!", nodes == null);
        int i = 0;
        for(char node : nodes)
        {
            Assert.assertFalse("The " + i + "nd position of the array was empty",node == '\u0000');
            i++;
        }
    }

    @Test
    public void checkKeyPressed() throws Exception
    {
        Game game = new Game();
        //TODO: replace character array with player list.
        char[] nodes = new char[10];
        //char[] nodes = game.getNodes();

        KeyPressedResult keyPressedResult = game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed('\u0000');
        Assert.assertEquals("The incorrect key was typed, but the result was correct", KeyPressedResult.WRONG, keyPressedResult);

        if(nodes[0] != nodes[2])
        {
            keyPressedResult = game.checkKeyPressed(nodes[2]);
            Assert.assertEquals("The incorrect key was typed, but the result was correct", KeyPressedResult.WRONG, keyPressedResult);
        }

        keyPressedResult = game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[1]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[2]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[3]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.SEQUENCE_FINISHED, keyPressedResult);

        //nodes = game.getNodes();

        keyPressedResult = game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[1]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[2]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, keyPressedResult);

        keyPressedResult = game.checkKeyPressed(nodes[3]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.GAME_FINISHED, keyPressedResult);
    }

}