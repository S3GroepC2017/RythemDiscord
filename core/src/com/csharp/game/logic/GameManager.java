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
    private Registry gameServerRegistry = null;
    private static final String GAME_SERVER_HOST_ADRESS = "192.168.0.100";
//    private static final String GAME_SERVER_HOST_ADRESS = "localhost";
    private static final int GAME_SERVER_REGISTRY_PORT = 1099;

    public GameManager()
    {
        try
        {
            clientLoginServer = new ClientLoginServer();
            System.out.println("Locating gameServerRegistry at: " + GAME_SERVER_HOST_ADRESS + ":" + GAME_SERVER_REGISTRY_PORT);
            gameServerRegistry = LocateRegistry.getRegistry(GAME_SERVER_HOST_ADRESS, GAME_SERVER_REGISTRY_PORT);
        } catch (RemoteException e) {
            System.out.println("Registry not found.");
            e.printStackTrace();
            System.exit(1);
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
            IServerManager serverManager = (IServerManager) gameServerRegistry.lookup("ServerManager");
            String gameKey = serverManager.createGame();
            System.out.println("Game created with game key: " + gameKey);
            joinGame(gameKey);
            return gameKey;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean joinGame(String gameKey)
    {
        System.out.println("JOIN GAME CALLED");
        try {
            serverGame = (IServerGame) gameServerRegistry.lookup(gameKey);
            currentGame = new Game(localPlayer, serverGame, uiCallback);

            serverGame.subscribe(currentGame, "dtoProperty");

            if (serverGame.joinPlayer(localPlayer))
            {
                System.out.println("Game join successful with local player: " + localPlayer.getName());
                return true;
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (NotBoundException e)
        {
            // The given gameKey isn't registered in the Registry
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean singlePlayerGame() {
        localPlayer = new Player("SinglePlayer");
        if(!newGame().isEmpty()) {
            startGame();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean logIn(String username, String password)
    {
////        // TODO REMOVE THIS DEBUG CODE:
//        localPlayer = new Player(username);
//        return true;

        // TODO: CONTACT LOGIN SERVER FOR VERIFICATION

        boolean success = false;

        if (clientLoginServer.login(username, password))
        {
            localPlayer = new Player(username);
            success = true;
        }
        //        success = true;
        //        localPlayer = new Player(username);

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
        System.out.println("Callback changed to: " + callback.getClass().toString());
        this.uiCallback = callback;

        if (currentGame != null)
        {
            currentGame.setUiCallback(this.uiCallback);
        }
    }

    @Override
    public void leaveCurrentGame()
    {
        try
        {
            serverGame.unsubscribe(currentGame, "dtoProperty");

            serverGame.leave(localPlayer);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
}
