package com.csharp.game;

import com.badlogic.gdx.InputProcessor;
import com.csharp.game.logic.GameManager;
import com.csharp.game.logic.ILogic;


/**
 * RythemDiscord
 * Created by Dane Naebers on 24-10-17.
 *
 * This class handles all events past down from the application.
 */
public class InputManager implements InputProcessor {
    final RythemDiscord game;
    private ILogic logic;

    public InputManager(RythemDiscord game) {
        this.game = game;
        this.logic = new GameManager();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //TODO Handle all keys for the game
        System.out.println(character);
        System.out.println(logic.keyPressed(character));

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
}
