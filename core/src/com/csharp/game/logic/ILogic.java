package com.csharp.game.logic;

import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

import java.util.List;

/**
 * @author Groep C#
 */
public interface ILogic
{
    // New game
    void startGame();

    // Join game
    void joinGame(String gameKey);

    boolean logIn(String username, String password);

    List<Player> getNodes();

    KeyPressedResult keyPressed(char keyPressed);
}
