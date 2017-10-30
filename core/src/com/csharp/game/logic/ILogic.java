package com.csharp.game.logic;

/**
 * @author Groep C#
 */
public interface ILogic
{
    void startGame();

    char[] getNodes();

    boolean keyPressed(char keyPressed);
}
