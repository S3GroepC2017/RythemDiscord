package com.csharp.game.logic;

/**
 * @author Groep C#
 */
public interface ILogic
{
    void startGame();

    char[] getNodes();

    KeyPressedResult keyPressed(char keyPressed);
}
