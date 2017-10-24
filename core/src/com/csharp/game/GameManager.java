package com.csharp.game;

import java.util.List;

/**
 * Created by Lars on 25-9-2017.
 */
public class GameManager  implements ILogic{

    private Game currentGame;

    public GameManager(){
        currentGame = new Game();
    }

    @Override
    public void startGame() {

    }

    @Override
    public char[] getNodes() {
        return currentGame.getNodes();
    }

    @Override
    public boolean keyPressed(char keyPressed) {
        return false;
    }
}
