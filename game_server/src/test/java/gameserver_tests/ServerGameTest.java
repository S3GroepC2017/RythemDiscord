package gameserver_tests;

import com.csharp.game.server.ServerGame;
import com.csharp.game.server.ServerManager;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.IPropertyListener;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.*;

public class ServerGameTest implements IPropertyListener
{
    Registry registry;
    ServerGame serverGame;
    ServerManager serverManager;
    Player player1;
    Player player2;
    GameServerDriver gameServerDriver;

    @Before
    public void setUp() throws Exception {
        registry = LocateRegistry.createRegistry(1099);
        serverManager = new ServerManager(registry);
        serverGame = new ServerGame();
        player1 = new Player("player1");
        player2 = new Player("player2");
        gameServerDriver = new GameServerDriver();
    }

    //This test might fail because of the static integer counting up. This test requires the gameid to start at 0.
    //If another test created a game before the gameid isn't 0 anymore.
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
        joinPlayer(serverGame, true, "Test joinPlayer1 for the first time", player1);
        joinPlayer(serverGame, false, "Test joinPlayer1 for the seccond time", player1);
        joinPlayer(serverGame, false, "Test joinPlayer2 for the first time", player2);
    }

    public void joinPlayer(IServerGame serverGame, boolean expected, String message, Player player) throws RemoteException
    {
        boolean succeeded = serverGame.joinPlayer(player);
        assertEquals(message, expected, succeeded);
    }


    @Test
    public void joinPlayerFail() throws Exception
    {
        boolean joinPlayerSucceeded = serverGame.joinPlayer(null);
        assertFalse("Test joinPlayer null", joinPlayerSucceeded);
    }

    @Test
    public void subscribe() throws Exception {
        subscribeToPropertyListener(serverGame);
    }

    public void subscribeToPropertyListener(ServerGame serverGame) throws RemoteException
    {
        serverGame.subscribe(gameServerDriver, "players");
        serverGame.subscribe(gameServerDriver, "noteListIndex");
        serverGame.subscribe(gameServerDriver, "lastKeyPressResult");
    }

    @Test
    public void startGame() throws Exception {
        ServerManagerTest serverManagerTest = new ServerManagerTest();
        String gameId = serverManagerTest.createServerGame(serverManager);
        IServerGame serverGameTest = (IServerGame) registry.lookup(gameId);
        subscribeToPropertyListener((ServerGame) serverGameTest);
        joinPlayer(serverGameTest, true, "Test join Player by Interface", player1);
        assertEquals("Test Player list is updated after joining", 1, gameServerDriver.players.size());
        assertEquals("Test Correct Player has joined the game", player1, gameServerDriver.players.get(0));
        serverGameTest.startGame(player1);
        assertEquals("Test NodeListIndex is updated", 1, gameServerDriver.nodeListIndex);
    }

    @Test
    public void keyPressed() throws Exception {
    }

}