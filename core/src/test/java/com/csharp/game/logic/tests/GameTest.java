package com.csharp.game.logic.tests;

import com.csharp.game.logic.Game;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import org.junit.Assert;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 30-10-2017.
 */
public class GameTest
{
    @Test
    public void getNodes() throws Exception
    {
        Game game = new Game(new Player("testPlayer"), new IServerGameStub());
        //TODO: replace character array with player list.
        List<Player> players = game.getNodes();
        Assert.assertFalse("The player array was null!", players == null);

        for (Player player : players)
        {
            int numbplayer = 1;
            int i = 0;
            char key;
            do
            {
                key = player.getNode(i);
                Assert.assertFalse("Position " + i + " of player:" + numbplayer + "'s array was empty", key == '\u0000');
                i++;
                numbplayer++;
            } while (key != ' ');
        }
    }

    @Test
    public void checkKeyPressed() throws Exception
    {
        Player localPlayer = new Player("TestPlayer");
        IServerGameStub serverGameStub = new IServerGameStub();
        localPlayer.setNodeList(new char[]{'a', 'b', 'c', 'd'});
        Game game = new Game(localPlayer, serverGameStub);

        //TODO: replace character array with player list.
        List<Player> players = game.getNodes();

        char[] nodes = new char[]{
                players.get(0).getNode(0),
                players.get(0).getNode(1),
                players.get(0).getNode(2),
                players.get(0).getNode(3)
        };

        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, players));

        game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);

        game.checkKeyPressed('\u0000');
        Assert.assertEquals("The incorrect key was typed, but the result was correct", KeyPressedResult.WRONG, serverGameStub.lastReceivedResultKeyPressResult);
        
        players = new ArrayList<>();
        localPlayer.setNodeList(new char[]{nodes[0]});
        players.add(localPlayer);
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, players));

        game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The end of sequence wasn't reached", KeyPressedResult.SEQUENCE_FINISHED, serverGameStub.lastReceivedResultKeyPressResult);

//        game.checkKeyPressed(nodes[2]);
//        Assert.assertEquals("The incorrect key was typed, but the result was correct", KeyPressedResult.WRONG, serverGameStub.lastReceivedResultKeyPressResult);
//
//
//        game.checkKeyPressed(nodes[0]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[1]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[2]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[3]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.SEQUENCE_FINISHED, serverGameStub.lastReceivedResultKeyPressResult);

//        //nodes = game.getNodes();
//
//        game.checkKeyPressed(nodes[0]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[1]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[2]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT, serverGameStub.lastReceivedResultKeyPressResult);
//
//        game.checkKeyPressed(nodes[3]);
//        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.GAME_FINISHED, serverGameStub.lastReceivedResultKeyPressResult);
    }
}