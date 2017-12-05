package com.csharp.game;

import com.badlogic.gdx.InputProcessor;
import com.csharp.game.logic.ILogic;
import com.csharp.sharedclasses.KeyPressedResult;
import com.csharp.sharedclasses.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private KeyPressedResult lastSucces;
    boolean keyWasPressed;

    public InputManager(RythemDiscord game)
    {
        this.game = game;

        //Changed from new to global logic
        this.logic = game.getLogic();

//      TODO: DEZE FUNCITE WORD IN DE CLASS VAN GameScreen AL AANGEROEPEN
//        logic.newGame();
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
        // TODO        lastSucces = logic.keyPressed(character);
//        throw new NotImplementedException();
        //IGNORE

        KeyPressedResult keyResult = logic.keyPressed(character);
        boolean success = false;

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

    public List<Player> getKeys()
    {
        //TODO getPlayers for all players
        return logic.getPlayers();
    }

    public boolean isKeyWasPressed()
    {
        boolean updateDetected = keyWasPressed;
        keyWasPressed = false;
        return updateDetected;
    }

    public KeyPressedResult getLastSuccess()
    {
        return lastSucces;
    }

    public void resetSuccess()
    {
        lastSucces = KeyPressedResult.WRONG;
    }

    public int getKeyIndex()
    {
        //TODO Return key index
        //return logic.getKeyIndex;
        return 0;
    }
}
