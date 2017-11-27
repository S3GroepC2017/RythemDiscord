package com.csharp.game.logic;

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
        //registry.lookup("ServerManager");
    }

    @Override
    public void joinGame(int gameID)
    {
        // TODO: CONTACT SERVER FOR GAME OBJECT WITH ID
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
}
