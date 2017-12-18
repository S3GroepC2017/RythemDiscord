package com.csharp.game.logic;

import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

import java.util.List;

/**
 * @author Groep C#
 */
public interface ILogic
{
    /**
     * begin the game once in an existing game
     */
    void startGame();

    /**
     * make new game on the server and join that game
     */
    String newGame();

    /**
     * join existing game on the server
     * @param gameKey key to look up the existing game
     */
    void joinGame(String gameKey);

    boolean logIn(String username, String password);

    List<Player> getPlayers();

    KeyPressedResult keyPressed(char keyPressed);

    Player getLocalPlayer();

    void setCallback(IAfterPosUpdateCallback callback);
}
