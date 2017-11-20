package com.csharp.game.communication;

import com.csharp.game.logic.Game;

public class Communication implements ICommunication
{
    @Override
    public boolean logIn(String username, String password)
    {
        return false;
    }

    @Override
    public Game newGame()
    {
        return null;
    }

    @Override
    public Game joinGame(int gameID)
    {
        return null;
    }
}
