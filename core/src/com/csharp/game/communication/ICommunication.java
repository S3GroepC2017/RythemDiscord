package com.csharp.game.communication;

import com.csharp.game.logic.Game;

public interface ICommunication
{
    boolean logIn(String username, String password);

    Game newGame();

    Game joinGame(int gameID);
}
