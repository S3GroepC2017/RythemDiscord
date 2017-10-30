package com.csharp.game.logic;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class GameManager implements ILogic
{
    private Game currentGame;

    @Override
    public void startGame()
    {
        currentGame = new Game();
    }

    @Override
    public char[] getNodes()
    {
        return currentGame.getNodes();
    }

    @Override
    public boolean keyPressed(char keyPressed)
    {
        return currentGame.checkKeyPressed(keyPressed);
    }
}
