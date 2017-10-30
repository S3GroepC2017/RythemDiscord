package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csharp.game.InputManager;
import com.csharp.game.RythemDiscord;
import com.csharp.game.logic.GameManager;
import com.csharp.game.logic.ILogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * RythemDiscord
 * Created by Dane Naebers on 25-10-2017.
 *
 * This is the main game code for the application.
 */
public class GameScreen implements Screen {

    final RythemDiscord game;
    private InputManager inputManager;  //!!maybe not needed

    //textures and renderers
    private ShapeRenderer shapeRenderer;
    private ArrayList<Texture> backgroundTextures;
    private Texture[] escKeys;


    public GameScreen(final RythemDiscord game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        inputManager = new InputManager(game);
        Gdx.input.setInputProcessor(inputManager); //passing all inputs to the custom input process class
        loadBackgroundTextures();
        LoadExitTextures();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clearing the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background always needs to be the first thing to render
        renderBackground();

        //rendering of the note section (play area)
        renderNoteSection();

        //rendering of the generated keys
        //TODO

        //rendering of the current keyframe (Key that needs to be pressed)
        renderCurrentKeyFrame();

        //render exit button
        renderExitButton();

        //Handle user input
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

    //Disposing all loaded items and textures PLS DONT FORGETI!
    @Override
    public void dispose() {
        shapeRenderer.dispose();
        for(Texture t : backgroundTextures) {
            t.dispose();
        }
        for(int i = 0; i < escKeys.length; i++) {
            escKeys[i].dispose();
        }
    }

    private void loadBackgroundTextures() {
        //choosing and loading the background
        Random rand =  new Random();
        backgroundTextures = new ArrayList<Texture>();
        switch(rand.nextInt(4)) {
            case 0:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/far-buildings.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/back-buildings.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/foreground.png")));
                break;
            case 1:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-1.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-2.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-3.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-4.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-5.png")));
                break;
            case 2:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/cliffs/sky.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/cliffs/clouds.png")));
                break;
            case 3:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/far.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/sand.png")));
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/foreground-merged.png")));
                break;
        }
    }

    private void LoadExitTextures() {
        escKeys =  new Texture[2];
        escKeys[0] = new Texture(Gdx.files.internal("keys/EscKey_default.png"));
        escKeys[1] = new Texture(Gdx.files.internal("keys/EscKey_pressed.png"));
    }

    private void renderBackground() {
        game.spriteBatch.begin();
        for(Texture t : backgroundTextures) {
            game.spriteBatch.draw(t , 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        game.spriteBatch.end();
    }

    private void renderNoteSection() {
        //render transparent background
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.1f));
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), 200);
        shapeRenderer.end();
        //render border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0,0,0,1);
        shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, 200);
        Gdx.gl20.glLineWidth(10);
        shapeRenderer.end();
    }

    private void renderCurrentKeyFrame() {
        //render keyframe from current playable note
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(255,0,0,0.7f);
        shapeRenderer.rect(50, 50, 100, 100);
        Gdx.gl20.glLineWidth(15);
        shapeRenderer.end();
    }

    private void renderExitButton() {
        game.spriteBatch.begin();
        game.spriteBatch.draw(escKeys[0], 1470, 830, 100, 50);
        game.spriteBatch.end();
    }

    private void handleUserInput() {
        //Tracking and handling of the Esc Key / Exit key
        if(Gdx.input.getX() > 1470 && Gdx.input.getX() < 1570 &&
                Gdx.input.getY() < 70 && Gdx.input.getY() > 20) {
            game.spriteBatch.begin();
            game.spriteBatch.draw(escKeys[1], 1470, 830, 100, 50);
            game.spriteBatch.end();
            if(Gdx.input.isTouched()) {
                game.setScreen(new MainMenuScreen(game));
                this.dispose();
            }
        }
    }
}
