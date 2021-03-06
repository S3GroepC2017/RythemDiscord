package com.csharp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csharp.game.logic.GameManager;
import com.csharp.game.logic.ILogic;
import com.csharp.game.screens.ui.screens.LoginScreen;
import com.csharp.game.screens.ui.screens.MainMenuScreen;


/**
 * RythemDiscord
 *
 * @author Groep C#
 * <p>
 * This class is the main graphics class
 * The only thing this class does is holding the main sprite batch and font
 * On startup it immediately redirects to the menu screen.
 */

public class RythemDiscord extends Game
{

    //The global accessible sprite batch
    public SpriteBatch spriteBatch;
    private ILogic logic; //Logic for each screen in the game

    @Override
    public void create()
    {
        //creating logic
        logic = new GameManager();

        //creating the sprite batch
        spriteBatch = new SpriteBatch();

        //load and set the menu screen
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render()
    {
        super.render();
    }

    //this method clears the items and releases them from memory.
    @Override
    public void dispose()
    {
        spriteBatch.dispose();
    }

    public ILogic getLogic()
    {
        return logic;
    }
}
