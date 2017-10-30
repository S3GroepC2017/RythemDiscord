package com.csharp.game;

import com.badlogic.gdx.InputProcessor;
import com.csharp.game.logic.GameManager;
import com.csharp.game.logic.ILogic;


/**
 * RythemDiscord
 * @Author Groep C#
 * <p>
 * This class handles all events past down from the application.
 */
public class InputManager implements InputProcessor
{
    final RythemDiscord game;
    private ILogic logic;
    private boolean lastSucces;

    public InputManager(RythemDiscord game)
    {
        this.game = game;
        this.logic = new GameManager();
        logic.startGame();

        //TODO Show array in future keys of the gamewindow
        for (char c : logic.getNodes())
        {
            System.out.print(Character.toString(c));
        }
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        //TODO Handle all keys for the game
        System.out.println(character);
        lastSucces = logic.keyPressed(character);

        //IGNORE
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    public char[] getKeys()
    {
        return logic.getNodes();
    }

    public boolean getLastSuccess()
    {
        return lastSucces;
    }

    public void resetSuccess()
    {
        lastSucces = false;
    }

    public int getKeyIndex()
    {
        //TODO Return key index
//        return logic.getKeyIndex;
        return 0;
    }
}
