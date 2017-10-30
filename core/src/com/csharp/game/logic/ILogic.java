package com.csharp.game.logic;

import java.util.List;

/**
 * @Author Groep C#
 */
public interface ILogic
{
    void startGame();

    char[] getNodes();

    boolean keyPressed(char keyPressed);
}
