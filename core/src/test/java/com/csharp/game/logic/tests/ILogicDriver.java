package com.csharp.game.logic.tests;

import com.csharp.game.logic.ILogic;
import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

import java.util.List;

public class ILogicDriver
{
    ILogic gameManager;

    String resultNewGame;
    boolean resultJoinGame;
    boolean resultLogIn;
    List<Player> resultGetPlayers;
    KeyPressedResult resultKeyPressed;
    Player resultGetLocalPlayer;

    public ILogicDriver(ILogic gameManager)
    {
        this.gameManager = gameManager;
    }

    void startGame()
    {
        gameManager.startGame();
    }

    String newGame()
    {
        resultNewGame = gameManager.newGame();
        return resultNewGame;
    }

    boolean joinGame(String gameKey)
    {
        resultJoinGame = gameManager.joinGame(gameKey);
        return resultJoinGame;
    }

    boolean logIn(String username, String password)
    {
        resultLogIn = gameManager.logIn(username, password);
        return resultLogIn;
    }

    List<Player> getPlayers()
    {
        resultGetPlayers = gameManager.getPlayers();
        return resultGetPlayers;
    }

    KeyPressedResult keyPressed(char keyPressed)
    {
        resultKeyPressed = gameManager.keyPressed(keyPressed);
        return resultKeyPressed;
    }

    Player getLocalPlayer()
    {
        resultGetLocalPlayer = gameManager.getLocalPlayer();
        return resultGetLocalPlayer;
    }

    void setCallback(IAfterPosUpdateCallback callback)
    {
        gameManager.setCallback(callback);
    }

    void leaveCurrentGame()
    {
        gameManager.leaveCurrentGame();
    }
}
