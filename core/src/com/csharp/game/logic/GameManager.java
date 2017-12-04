package com.csharp.game.logic;

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
    private IGame currentGame;
    private Player localPlayer;
    private IServerGame serverGame;

    //TODO: ADD 2 PRIVATE VARIABLES FOR THE LOGIN AND GAME SERVERS
    private Registry registry = null;
    private String hostAddress = "127.0.0.1";
    private int portNumber = 1099;

    public GameManager() {
        try {
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

        localPlayer = new Player(username);
        return true;
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

    @Override
    public int getNodeListPosition() {
        return currentGame.getNodeListPosition();
    }

}
