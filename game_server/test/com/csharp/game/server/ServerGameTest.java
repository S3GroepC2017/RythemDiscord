package com.csharp.game.server;

import com.csharp.sharedclasses.Player;
import org.junit.Before;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;

import static org.junit.Assert.*;

public class ServerGameTest {
    ServerGame serverGame;
    Player player1;
    Player player2;
    @Before
    public void setUp() throws Exception {
        serverGame = new ServerGame();
        player1 = new Player("player1");
        player2 = new Player("player2");
    }

    @Test
    public void getGameId() throws Exception {
        int serverGameId = serverGame.getGameId();
        assertEquals("Test gameId automatic increase Test 1", 0, serverGameId);
        serverGame = new ServerGame();
        serverGameId = serverGame.getGameId();
        assertEquals("Test gameId automatic increase Test 2", 1, serverGameId);
    }

    @Test
    public void joinPlayer() throws Exception {
        boolean joinPlayerSucceeded = serverGame.joinPlayer(player1);
        assertTrue("Test joinPlayer1 for the first time", joinPlayerSucceeded);
        joinPlayerSucceeded = serverGame.joinPlayer(player1);
        assertFalse("Test joinPlayer1 for the seccond time", joinPlayerSucceeded);
        joinPlayerSucceeded = serverGame.joinPlayer(player2);
        assertTrue("Test joinPlayer2 for the first time", joinPlayerSucceeded);
    }

    @Test
    public void subscribe() throws Exception {

    }

    @Test
    public void startGame() throws Exception {
    }

    @Test
    public void keyPressed() throws Exception {
    }

}