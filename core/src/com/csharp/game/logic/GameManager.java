package com.csharp.game.logic;

import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author Groep C#
 */
public class GameManager implements ILogic
{
    private IAfterPosUpdateCallback uiCallback;
    // TODO REMOVE HARDCODED VALUE
    private Player localPlayer;
    private Game currentGame;

    private IServerGame serverGame;
    private ClientLoginServer clientLoginServer;
    private Registry registry = null;
    private static final String hostAddress = "localhost";
    private static final int portNumber = 1099;

    public GameManager() {
        try {
            clientLoginServer = new ClientLoginServer();
            System.out.println("Locating registry at: " + hostAddress + ":" + portNumber);
            registry = LocateRegistry.getRegistry(hostAddress, portNumber);
        } catch (RemoteException e) {
            System.out.println("Registry not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void startGame()
    {
        if (currentGame == null)
        {
            return;
        }
        currentGame.beginGame();
    }

    @Override
    public String newGame()
    {
        System.out.println("NEW GAME CALLED");
        try {
            IServerManager serverManager = (IServerManager) registry.lookup("ServerManager");
            String gameKey = serverManager.createGame();
            System.out.println("Game created with game key: " + gameKey);
            joinGame(gameKey);
            return gameKey;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        } catch (NotBoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void joinGame(String gameKey)
    {
        System.out.println("JOIN GAME CALLED");
        try {
            serverGame = (IServerGame) registry.lookup(gameKey);
            currentGame = new Game(localPlayer, serverGame, uiCallback);
            serverGame.subscribe(currentGame, "noteListIndex");
            serverGame.subscribe(currentGame, "players");
            serverGame.subscribe(currentGame, "lastKeyPressResult");
            if (serverGame.joinPlayer(localPlayer)){
//                System.out.println("Game join successful with local player: " + localPlayer.getName());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean logIn(String username, String password)
    {
        // TODO: CONTACT LOGIN SERVER FOR VERIFICATION

        boolean success = false;

        if (clientLoginServer.login(username,password))
        {
            localPlayer = new Player(username);
            success = true;
        }

        return success;

    }

    @Override
    public List<Player> getPlayers()
    {
        return currentGame.getNodes();
    }

    @Override
    public KeyPressedResult keyPressed(char keyPressed)
    {
        return currentGame.checkKeyPressed(keyPressed);
//        return KeyPressedResult.WRONG;
    }

    @Override
    public Player getLocalPlayer()
    {
        return localPlayer;
    }

    @Override
    public void setCallback(IAfterPosUpdateCallback callback)
    {
        this.uiCallback = callback;
    }
}
