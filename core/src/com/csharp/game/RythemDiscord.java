package com.csharp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.csharp.game.screens.MainMenuScreen;


/**
 * RythemDiscord
 * Created by Dane Naebers on 24-10-17.
 *
 * This class is the main graphics class
 * The only thing this class does is holding the main sprite batch and font
 * On startup it immediately redirects to the menu screen.
 */

public class RythemDiscord extends Game {

    //The global accessible sprite batch
	public SpriteBatch spriteBatch;

    @Override
	public void create () {
    	//creating the sprite batch
		spriteBatch = new SpriteBatch();

        //load and set the menu screen
		this.setScreen(new MainMenuScreen(this));
	}


	@Override
	public void render () {
		super.render();
	}

	//this method clears the items and releases them from memory.
	@Override
	public void dispose () {
        spriteBatch.dispose();
	}
}
