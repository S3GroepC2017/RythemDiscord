package com.csharp.game;

import com.badlogic.gdx.InputProcessor;
import com.csharp.game.logic.GameManager;
import com.csharp.game.logic.ILogic;
import com.csharp.game.logic.KeyPressedResult;

import java.io.Console;


/**
 * RythemDiscord
 *
 * @author Groep C#
 * <p>
 * This class handles all events past down from the application.
 */
public class InputManager implements InputProcessor {
    final RythemDiscord game;
    private ILogic logic;
    private KeyPressedResult lastSucces = KeyPressedResult.NONE;
    private boolean keyWasPressed;

    public InputManager(RythemDiscord game) {
        this.game = game;
        this.logic = new GameManager();
        logic.startGame();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //TODO Handle all keys for the game
        char character = Character.toString((char) (keycode + 36 + 32)).charAt(0);
        System.out.println(character);
        lastSucces = logic.keyPressed(character);
        keyWasPressed = true;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //TODO Handle all keys for the game
        System.out.println(character);
        lastSucces = logic.keyPressed(character);
        keyWasPressed = true;


        //IGNORE
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public char[] getKeys() {
        return logic.getNodes();
    }

    public boolean isKeyWasPressed() {
        boolean updateDetected = keyWasPressed;
        keyWasPressed = false;
        return updateDetected;
    }

    public KeyPressedResult getLastSuccess() {
        return lastSucces;
    }

    public void resetSuccess() {
        lastSucces = KeyPressedResult.WRONG;
    }

    public int getKeyIndex() {
        //TODO Return key index
        //return logic.getKeyIndex;
        return 0;
    }
}
