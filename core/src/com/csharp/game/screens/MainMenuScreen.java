package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.csharp.game.RythemDiscord;

/**
 * RythemDiscord
 * @author Groep C#
 * 
 * This class is the application menu screen.
 */
public class MainMenuScreen implements Screen
{
    final RythemDiscord game;

    //textures and renderers
    private Texture gameTitle;
    private Texture[] menuStart;
    private Texture[] menuExit;


    //Font
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout glyphLayout;
    private float width;

    public MainMenuScreen(RythemDiscord game)
    {
        this.game = game;
        loadFont();
        loadCreatorsText();
        loadTextures();
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        //Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        Gdx.gl.glClearColor(67 / 255f, 71 / 255f, 81 / 255f, 1);

        //render game title
        renderGameTitle();

        //render menu buttons
        renderMenuButtons();

        //handle user input
        handleUserInput();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    //Disposing all loaded items and textures PLS DONT FORGETI!
    @Override
    public void dispose()
    {
        gameTitle.dispose();
        font.dispose();
        generator.dispose();
        for (Texture t : menuStart) {
            t.dispose();
        }
        for(Texture t : menuExit) {
            t.dispose();
        }
    }

    private void loadFont()
    {
        //creating and loading in custom font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    private void loadCreatorsText()
    {
        parameter.size = 12;
        font = generator.generateFont(parameter);
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "A game made by: Michelle, Joe, Teun, Lars, Niels & Dane");
        width = glyphLayout.width;
    }

    private void loadTextures()
    {
        gameTitle = new Texture(Gdx.files.internal("menu/GameTitle.png"));

        menuStart = new Texture[2];
        menuStart[0] = new Texture(Gdx.files.internal("menu/menuItemStart_default.png"));
        menuStart[1] = new Texture(Gdx.files.internal("menu/menuItemStart_pressed.png"));

        menuExit = new Texture[2];
        menuExit[0] = new Texture(Gdx.files.internal("menu/menuItemExit_default.png"));
        menuExit[1] = new Texture(Gdx.files.internal("menu/menuItemExit_pressed.png"));
    }

    private void renderGameTitle()
    {
        game.spriteBatch.begin();
        game.spriteBatch.draw(gameTitle, (Gdx.graphics.getWidth() / 2) - 250, 674, 500, 156);
        font.draw(game.spriteBatch, glyphLayout, (Gdx.graphics.getWidth() / 2) - (width / 2), 630);
        game.spriteBatch.end();
    }

    private void renderMenuButtons()
    {
        game.spriteBatch.begin();
        game.spriteBatch.draw(menuStart[0], (Gdx.graphics.getWidth() / 2) - 50, 500, 100, 25);
        game.spriteBatch.draw(menuExit[0], (Gdx.graphics.getWidth() / 2) - 50, 450, 100, 25);
        game.spriteBatch.end();
    }

    private void handleUserInput() {

        //tracking of start button
        if (Gdx.input.getX() > ((Gdx.graphics.getWidth() / 2) - 50) && Gdx.input.getX() < ((Gdx.graphics.getWidth() / 2) + 50)
                && Gdx.input.getY() < 400 && Gdx.input.getY() > 375) {
            //rerender start button
            game.spriteBatch.begin();
            game.spriteBatch.draw(menuStart[1], (Gdx.graphics.getWidth() / 2) - 50, 502, 100, 25);
            game.spriteBatch.end();

            //if button is pressed
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        //tracking exit button
        if (Gdx.input.getX() > ((Gdx.graphics.getWidth() / 2) - 50) && Gdx.input.getX() < ((Gdx.graphics.getWidth() / 2) + 50)
                 && Gdx.input.getY() < 450 && Gdx.input.getY() > 425) {
            game.spriteBatch.begin();
            game.spriteBatch.draw(menuExit[1], (Gdx.graphics.getWidth() / 2) - 50, 452, 100, 25);
            game.spriteBatch.end();

            if (Gdx.input.isTouched()) {
                this.dispose();
                System.exit(0);
            }
        }
    }
}
