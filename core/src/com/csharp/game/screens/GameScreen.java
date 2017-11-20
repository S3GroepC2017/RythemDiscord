package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csharp.game.InputManager;
import com.csharp.game.RythemDiscord;
import com.csharp.game.logic.KeyPressedResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;

/**
 * RythemDiscord
 *
 * @author Groep C#
 * <p>
 * This is the main game code for the application.
 */
public class GameScreen implements Screen {

    final RythemDiscord game;
    private InputManager inputManager;  //!!maybe not needed

    //textures and renderers
    private ShapeRenderer shapeRenderer;
    private ArrayList<Texture> backgroundTextures;
    private ArrayList<Texture> originalKeyTextures;
    private ArrayList<Texture> playableKeyTextures;
    private Texture[] escKeys;

    //font
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout glyphLayout;
    private float width;

    public GameScreen(final RythemDiscord game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        inputManager = new InputManager(game);
        Gdx.input.setInputProcessor(inputManager); //passing all inputs to the custom input process class
        loadBackgroundTextures();
        loadKeyTextures(inputManager.getKeys());
        loadExitTextures();
        loadFont();
        loadFinishText();
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
        renderKeys();

        //rendering of the current keyframe (Key that needs to be pressed)
        renderCurrentKeyFrame();

        //render exit button
        renderExitButton();

        //Handle user input
        if(inputManager.isKeyWasPressed()){
            handleUserInput();
        }
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
        for (Texture t : backgroundTextures) {
            t.dispose();
        }
        for (Texture t : originalKeyTextures) {
            t.dispose();
        }
        for (Texture t : playableKeyTextures) {
            t.dispose();
        }
        for (Texture escKey : escKeys) {
            escKey.dispose();
        }
    }

    private void loadBackgroundTextures() {
        //choosing and loading the background
        Random rand = new Random();
        backgroundTextures = new ArrayList<Texture>();
        switch (rand.nextInt(6)) {
            case 0:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/PunkCityFinal.png")));
                break;
            case 1:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/JungleFinal.png")));
                break;
            case 2:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/cliffs/CliffsFinal.png")));
                break;
            case 3:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/UnderwaterFinal.png")));
                break;
            case 4:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/forrest/ForestFinal.png")));
                break;
            case 5:
                backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/space/SpaceFinal.png")));
                break;
        }
    }

    private void loadKeyTextures(char[] keys) {
        originalKeyTextures = new ArrayList<Texture>();
        playableKeyTextures = new ArrayList<Texture>();

        for (char key : keys) {
            Texture keyTexture = new Texture(Gdx.files.internal("keys/" + Character.toString(key) + ".png"));
            originalKeyTextures.add(keyTexture);
            playableKeyTextures.add(keyTexture);
        }
    }

    private void loadExitTextures() {
        escKeys = new Texture[2];
        escKeys[0] = new Texture(Gdx.files.internal("keys/EscKey_default.png"));
        escKeys[1] = new Texture(Gdx.files.internal("keys/EscKey_pressed.png"));
    }

    private void loadFont() {
       generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF"));
       parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    private void loadFinishText() {
        parameter.size = 40;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Congrats you did it!!!");
        width = glyphLayout.width;
    }

    private void renderBackground() {
        game.spriteBatch.begin();
        for (Texture t : backgroundTextures) {
            game.spriteBatch.draw(t, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, 200);
        Gdx.gl20.glLineWidth(10);
        shapeRenderer.end();
    }

    private void renderCurrentKeyFrame() {
        //render keyframe from current playable note
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(255, 0, 0, 0.7f);
        shapeRenderer.rect(50, 50, 100, 100);
        Gdx.gl20.glLineWidth(15);
        shapeRenderer.end();
    }

    private void renderKeys() {
        if (!playableKeyTextures.isEmpty()) {
            game.spriteBatch.begin();
            game.spriteBatch.draw(playableKeyTextures.get(0), 60, 60, 80, 80); //current key

            for (Texture t : playableKeyTextures) {
                if (playableKeyTextures.indexOf(t) == 15) {
                    break;
                }
                game.spriteBatch.draw(t, calculateKeyMargin(playableKeyTextures.indexOf(t)), 60, 80, 80); //key
            }
            game.spriteBatch.end();
        }
    }

    private void renderExitButton() {
        game.spriteBatch.begin();
        game.spriteBatch.draw(escKeys[0], 1470, 830, 100, 50);
        game.spriteBatch.end();
    }

    private int calculateKeyMargin(int index) {
        int margin = 60;

        for (int i = 0; i < index; i++) {
            margin += 100;
        }

        return margin;
    }

    private void handleUserInput() {
        //check if all keys are successfully pressed

        if (playableKeyTextures.isEmpty()) {
            //TODO congrats window
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1, 1, 1, 0.8f));
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            game.spriteBatch.begin();
            font.draw(game.spriteBatch, glyphLayout, (Gdx.graphics.getWidth() / 2) - (width / 2), 450);
            game.spriteBatch.end();
            if(Gdx.input.isTouched()) {
                game.setScreen(new MainMenuScreen(game));
                this.dispose();
            }
        } else {
            //Tracking and handling of the Esc Key / Exit key
            if (Gdx.input.getX() > 1470 && Gdx.input.getX() < 1570 &&
                    Gdx.input.getY() < 70 && Gdx.input.getY() > 20) {
                game.spriteBatch.begin();
                game.spriteBatch.draw(escKeys[1], 1470, 830, 100, 50);
                game.spriteBatch.end();
                if (Gdx.input.isTouched()) {
                    game.setScreen(new MainMenuScreen(game));
                    this.dispose();
                }
            }

            //check if key pressed value
            switch(inputManager.getLastSuccess())
            {
                case CORRECT:
                    playableKeyTextures.remove(0);
                    inputManager.resetSuccess();
                    break;
                case SEQUENCE_FINISHED:
                    //ToDo: Write code for when the sequence of keys was completed.
                    throw new NotImplementedException();
                case GAME_FINISHED:
                    //ToDo: Write code for when the game is ended by typing the last code.
                case WRONG:
                    //ToDo: Write code for when the input was wrong.
                    throw new NotImplementedException();
                case NONE:
                    //ToDo: No input was given.
                    break;
            }

//            if (inputManager.getLastSuccess() == KeyPressedResult.CORRECT) {
//                playableKeyTextures.remove(0);
//                inputManager.resetSuccess();
//            }
        }
    }
}
