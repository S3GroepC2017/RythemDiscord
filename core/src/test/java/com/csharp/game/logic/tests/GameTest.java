package com.csharp.game.logic.tests;

import com.csharp.game.logic.Game;
import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.IGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 30-10-2017.
 */
public class GameTest
{
    IGameServerStub gameServerStub;
    ILoginServerStub loginServerStub;
    IGameServerStub serverGameStub;
    IUIstub uiStub;
    Player localPlayer;
    IGame game;
    List<Player> players;

    @Before
    public void setUp() throws Exception
    {
        gameServerStub = new IGameServerStub();
        loginServerStub = new ILoginServerStub();
        uiStub = new IUIstub();
        localPlayer = new Player("TestPlayer");
        serverGameStub = new IGameServerStub();
        game = new Game(localPlayer, serverGameStub, new IUIstub());
    }

    @Test
    public void getNodes() throws Exception
    {
        // setup all the things
        Player testPlayer = new Player("testPlayer");

        // set the players to have notes
        List<Player> playersWithNodes = new ArrayList<>();
        testPlayer.setNodeList(new char[]{'a'});
        playersWithNodes.add(testPlayer);

        game.propertyChange(new PropertyChangeEvent(gameServerStub, "players", null, new DTOClientUpdate(0, KeyPressedResult.STARTUP, new ArrayList<>(playersWithNodes))));

        // retrieve and check the notes
        List<Player> players = game.getNodes();
        Assert.assertFalse("The player array was null!", players == null);

        System.out.println(players);
        System.out.println(playersWithNodes);
        Assert.assertEquals(players, playersWithNodes);

        players.add(new Player("ds"));
        Assert.assertNotEquals(players, playersWithNodes);
    }

    @Test
    public void beginGame()
    {

    }

    @Test
    public void checkKeyPressed() throws Exception
    {
        localPlayer.setNodeList(new char[]{'a', 'b', 'c', 'd'});

        //TODO: replace character array with player list.
        List<Player> players = game.getNodes();

        char[] nodes = new char[]{players.get(0).getNode(0), players.get(0).getNode(1), players.get(0).getNode(2),
                                  players.get(0).getNode(3)};

        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(0, KeyPressedResult.STARTUP, players)));
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(0, KeyPressedResult.CORRECT, players)));

        System.out.println(game.checkKeyPressed(nodes[0]));

        Assert.assertEquals("The correct key was typed, but the result wasn't correct", KeyPressedResult.CORRECT,
                            serverGameStub.lastReceivedResultKeyPressResult);

        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(1, KeyPressedResult.CORRECT, players)));
        game.checkKeyPressed('\u0000');
        Assert.assertEquals("The incorrect key was typed, but the result was correct", KeyPressedResult.WRONG,
                            serverGameStub.lastReceivedResultKeyPressResult);

        players = new ArrayList<>();
        localPlayer.setNodeList(new char[]{nodes[0]});
        players.add(localPlayer);
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(0, KeyPressedResult.CORRECT, players)));

        game.checkKeyPressed(nodes[0]);
        Assert.assertEquals("The end of sequence wasn't reached", KeyPressedResult.SEQUENCE_FINISHED,
                            serverGameStub.lastReceivedResultKeyPressResult);
    }

    @Test
    public void getNodeListPosition() throws Exception
    {
        //if there is no value given to the node list position, this value is 0
        Assert.assertEquals(0, game.getNodeListPosition());
        Assert.assertNotEquals(4, game.getNodeListPosition());

        //change the position manually
        int testNodeListPosition = 3;
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(localPlayer);
        localPlayer.setNodeList(new char[]{'a', 'b', 'c', 'd'});
        char[] nodes = new char[]{allPlayers.get(0).getNode(0), allPlayers.get(0).getNode(1), allPlayers.get(0).getNode(2),
                allPlayers.get(0).getNode(3)};

        players = game.getNodes();
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(0, KeyPressedResult.STARTUP, players)));


        System.out.println(nodes);
        System.out.println(localPlayer.getNodeList());
        System.out.println(game.checkKeyPressed(nodes[0]));
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(1, KeyPressedResult.CORRECT, players)));
        System.out.println(game.checkKeyPressed(nodes[1]));
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(2, KeyPressedResult.CORRECT, players)));
        System.out.println(game.checkKeyPressed(nodes[2]));
        game.propertyChange(new PropertyChangeEvent(serverGameStub, "players", null, new DTOClientUpdate(3, KeyPressedResult.CORRECT, players)));

        Assert.assertEquals(testNodeListPosition, game.getNodeListPosition());
    }
}
