package com.csharp.game;

import com.badlogic.gdx.InputProcessor;


/**
 * RythemDiscord
 * Created by Dane Naebers on 24-10-17.
 *
 * This class handles all events past down from the application.
 */
public class InputManager implements InputProcessor {
    final RythemDiscord game;

    public InputManager(RythemDiscord game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
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
