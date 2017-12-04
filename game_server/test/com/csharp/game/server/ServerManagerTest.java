package com.csharp.game.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String gameRegistryKey = serverManager.createGame();
        Assert.assertEquals("Tests if the registry key is correct.","Game:0", gameRegistryKey);
        gameRegistryKey = serverManager.createGame();
        Assert.assertEquals("Tests if the registry key is correct.","Game:1", gameRegistryKey);
    }
}
