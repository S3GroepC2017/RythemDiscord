package com.csharp.game.logic;

import java.util.List;

/**
 * @author Groep C#
 */
public class GameManager implements ILogic
{
    private Game currentGame;
    private Player localPlayer;
    //TODO: ADD 2 PRIVATE VARIABLES FOR THE LOGIN AND GAME SERVERS

    @Override
    public void startGame()
    {
        // TODO: CONTACT SERVER FOR GAME OBJECT
        // currentGame = new Game();
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
        return currentGame.checkKeyPressed(keyPressed);
    }
}
