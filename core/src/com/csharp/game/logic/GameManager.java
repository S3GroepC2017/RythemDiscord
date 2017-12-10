package com.csharp.game.logic;

import com.csharp.sharedclasses.IServerGame;
import com.csharp.sharedclasses.IServerManager;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

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
    private Game currentGame;
    private Player localPlayer;
    private IServerGame serverGame;

    //TODO: ADD 2 PRIVATE VARIABLES FOR THE LOGIN AND GAME SERVERS
    private ClientLoginServer clientLoginServer;
    private Registry registry = null;
    private String hostAddress = "127.0.0.1";
    private int portNumber = 1099;

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
        // TODO: CONTACT SERVER FOR GAME OBJECT
        // currentGame = new Game();
        try {
            IServerManager serverManager = (IServerManager) registry.lookup("ServerManager");
            String gameKey = serverManager.createGame();
            joinGame(gameKey);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void joinGame(String gameKey)
    {
        // TODO: CONTACT SERVER FOR GAME OBJECT WITH ID
        try {
            serverGame = (IServerGame) registry.lookup(gameKey);
            currentGame = new Game(localPlayer, serverGame);
            serverGame.subscribe(currentGame, "noteListIndex");
            serverGame.subscribe(currentGame, "players");
            serverGame.joinPlayer(localPlayer);
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
    public List<Player> getNodes()
    {
        return currentGame.getNodes();
    }

    @Override
    public KeyPressedResult keyPressed(char keyPressed)
    {
        //return currentGame.checkKeyPressed(keyPressed);
        return KeyPressedResult.WRONG;
    }
    
}
