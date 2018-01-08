package gameserver_tests;

import com.csharp.game.server.ServerGame;
import com.csharp.game.server.ServerManager;
import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import com.csharp.sharedclasses.fontyspublisher.IPropertyListener;
import com.sun.org.apache.xerces.internal.parsers.DTDParser;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServerGameTest
{
    Registry registry = null;
    IServerGame serverGame;
    ServerManager serverManager;
    Player player1;
    Player player2;
    GameServerDriver gameServerDriver;

    @Before
    public void setUp() throws Exception
    {
        if (registry == null)
        {
            registry = LocateRegistry.createRegistry(1099);
        }
        else
        {
            registry = LocateRegistry.getRegistry(1099);
        }
        serverManager = new ServerManager(registry);
        serverGame = new ServerGame();
        player1 = new Player("player1");
        player2 = new Player("player2");
        gameServerDriver = new GameServerDriver(serverGame);
    }

    public void TearDown() throws InterruptedException
    {
        Thread.sleep(10000);
    }

    @Test
    public void startGame() throws Exception
    {
        ServerManagerTest serverManagerTest = new ServerManagerTest();
        String gameId = serverManagerTest.createServerGame(serverManager);
        IServerGame serverGameTest = (IServerGame) registry.lookup(gameId);

        DTOClientUpdate dtoClientUpdate = null;
        gameServerDriver.joinPlayer(player1);
        Thread.sleep(500);
        PropertyChangeEvent propertyChangeEvent = gameServerDriver.lastPropertyChangeEvent;
        if (propertyChangeEvent.getPropertyName().equals("dtoProperty"))
        {
            dtoClientUpdate = (DTOClientUpdate) propertyChangeEvent.getNewValue();
        }

        assertEquals("Test Player list is updated after joining", 1, dtoClientUpdate.getNewPlayerList().size());
        assertEquals("Test Correct Player has joined the game", player1, dtoClientUpdate.getNewPlayerList().get(0));
        serverGameTest.startGame(player1);
        assertEquals("Test NodeListIndex is updated", 0, dtoClientUpdate.getNewNodeListPosition());
    }

    @Test
    public void keyPressed() throws Exception
    {
        KeyPressedResult actualResult = KeyPressedResult.CORRECT;
        DTOClientUpdate dtoCatcher = null;

        startGame();
        gameServerDriver.keyPressed(actualResult);

        if (gameServerDriver.lastPropertyChangeEvent.getPropertyName().equals("dtoProperty"))
        {
            dtoCatcher = (DTOClientUpdate) gameServerDriver.lastPropertyChangeEvent.getNewValue();
            actualResult = dtoCatcher.getNewKeyPressResult();
        }

        assertEquals(actualResult, dtoCatcher.getNewKeyPressResult());

    }

}
