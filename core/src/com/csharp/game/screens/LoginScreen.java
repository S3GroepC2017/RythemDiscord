package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.csharp.game.RythemDiscord;

public class LoginScreen implements Screen {

    final RythemDiscord game;


    //textures and renderers
    private Texture gameTitle;

    //Font
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout glyphLayout;
    private float width;


    /**
     * Public constructor for LoginScreen
     * @param game the root game object that holds the global SpriteBatch for rendering items.
     */
    public LoginScreen(final RythemDiscord game){
        this.game = game;
        loadFont();
        loadText();
        loadTextures();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        Gdx.gl.glClearColor(67 / 255f, 71 / 255f, 81 / 255f, 1);

        //render game title
        renderGameTitle();

        //render menu buttons


        //handle user input
        handleUserInput();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //LOADING OF OBJECTS AND TEXTURES

    private void loadTextures() {
        gameTitle = new Texture(Gdx.files.internal("menu/GameTitle.png"));
    }

    private void loadFont() {
        //creating and loading in custom font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    private void loadText() {

    }

    //RENDERING OF OBJECTS AND TEXTURES

    private void renderGameTitle()
    {
        game.spriteBatch.begin();
        game.spriteBatch.draw(gameTitle, (Gdx.graphics.getWidth() / 2) - 250, 674, 500, 156);
        font.draw(game.spriteBatch, glyphLayout, (Gdx.graphics.getWidth() / 2) - (width / 2), 630);
        game.spriteBatch.end();
    }


    private void handleUserInput() {

    }
}
