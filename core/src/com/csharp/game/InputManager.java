package com.csharp.game;

import com.badlogic.gdx.InputProcessor;
import com.csharp.game.logic.ILogic;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;

import java.util.List;


/**
 * RythemDiscord
 *
 * @author Groep C#
 * <p>
 * This class handles all events past down from the application.
 */
public class InputManager implements InputProcessor
{
    final RythemDiscord game;
    private ILogic logic;

    public InputManager(RythemDiscord game)
    {
        this.game = game;
        this.logic = game.getLogic();
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
        if (character == ' ')
        {
            return false;
        }

        KeyPressedResult keyResult = logic.keyPressed(character);
        boolean success;

        switch (keyResult)
        {
            case SEQUENCE_FINISHED:
            case CORRECT:
                success = true;
                break;
            default:
                success = false;
                break;
        }

        return success;
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
}
