package gameserver_tests;

import com.csharp.game.server.ServerManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static org.junit.Assert.*;

public class ServerManagerTest
{
    ServerManager serverManager;
    @Before
    public void setUp() throws Exception {
        serverManager = new ServerManager(LocateRegistry.createRegistry(1099));
    }

    //This test might fail because of the static integer counting up. This test requires the gameid to start at 0.
    //If another test created a game before the gameid isn't 0 anymore.
    @Test
    public void createGame() throws Exception {
        String gameRegistryKey = createServerGame(serverManager);
        Assert.assertEquals("Tests if the registry key is correct.","Game:0", gameRegistryKey);
        gameRegistryKey = createServerGame(serverManager);
        Assert.assertEquals("Tests if the registry key is correct.","Game:1", gameRegistryKey);
    }

    public String createServerGame(ServerManager serverManager) throws RemoteException
    {
        return serverManager.createGame();
    }
}
