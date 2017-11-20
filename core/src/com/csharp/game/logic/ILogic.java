package com.csharp.game.logic;

import java.util.List;

/**
 * @author Groep C#
 */
public interface ILogic
{
    // New game
    void startGame();

    // Join game
    void joinGame(int gameID);

    boolean logIn(String username, String password);

    List<Player> getNodes();

    KeyPressedResult keyPressed(char keyPressed);
}
