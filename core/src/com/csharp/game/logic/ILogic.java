package com.csharp.game.logic;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public interface ILogic {
    void startGame();
    char[] getNodes();
    boolean keyPressed(char keyPressed);
}
