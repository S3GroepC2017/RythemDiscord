package com.csharp.game.screens.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csharp.game.InputManager;
import com.csharp.game.RythemDiscord;
import com.csharp.game.screens.ui.screens.MainMenuScreen;
import com.csharp.sharedclasses.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RythemDiscord
 *
 * @author Groep C#
 * <p>
 * This is the main game code for the application.
 */
public class GameScreen implements Screen, IAfterPosUpdateCallback {

    final RythemDiscord game;
    private InputMultiplexer inputMultiplexer;
    private InputManager inputManager;  //!!maybe not needed

    //UI Items
    private Skin skin;
    private Table table;
    private Stage stage;

    //Camera & Viewport
    private OrthographicCamera camera;
    private Viewport viewport;

    //TODO fill amountOfPlayers
    private int amountOfPlayers;

    //TODO original- and playableKeyTextures
    private ArrayList<Texture>[] allOriginalKeyTextures;
    private ArrayList<Texture>[] allPlayableKeyTextures;

    //textures and renderers
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;
    private Texture[] exitBtnStyleTextures;

    /**
     * Public constructor for GameScreen
     *
     * @param game the root game object that holds the global SpirteBatch for rendering items on the screen.
     */
    public GameScreen(final RythemDiscord game) {
        this.game = game;
        game.getLogic().setCallback(this);
        game.getLogic().newGame();
        game.getLogic().startGame();

        this.inputMultiplexer = new InputMultiplexer();
        this.inputManager = new InputManager(game);

        this.skin = new Skin();
        this.table = new Table();
        this.stage = new Stage();

        this.inputMultiplexer.addProcessor(inputManager);
        this.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(1000, 1000, this.camera);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        shapeRenderer = new ShapeRenderer();

        //loading textures
        loadBackgroundTextures();
        loadExitTextures();

        //TODO replace with unique keys foreach player
        //TODO uncomment next line
        loadKeyTextures();
        renderKeys();

        //loading of UI components
        createUiComponents();
    }

    @Override
    public void show() {

    }

    /**
     * Main render function for this screen.
     * this method is received from the root game object 'game'.
     *
     * @param delta is the time that has passed sins the last game loop.
     */
    @Override
    public void render(float delta) {
        //Updating the camera
        this.camera.update();
        this.game.spriteBatch.setProjectionMatrix(this.camera.combined);

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

        //rendering the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        //Handle user input
        handleUserInput();
    }

    @Override
    public void resize(int width, int height) {
        //Viewport and Camera update for SpriteBatch
        this.viewport.update(width, height);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        //Viewport for UI Stage
        this.stage.getViewport().update(width, height, true);
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
        backgroundTexture.dispose();

        for (ArrayList<Texture> tex : allOriginalKeyTextures) {
            for (Texture t : tex) {
                t.dispose();
            }
        }

        for (ArrayList<Texture> tex : allPlayableKeyTextures) {
            for (Texture t : tex) {
                t.dispose();
            }
        }

        for (Texture escKey : exitBtnStyleTextures) {
            escKey.dispose();
        }
    }


    //LOADING OF OBJECTS AND TEXTURES

    /**
     * Loading the background textures.
     */
    private void loadBackgroundTextures() {
        Random rand = new Random();
        switch (rand.nextInt(6)) {
            case 0:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/punkcity/PunkCityFinal.png"));
                break;
            case 1:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/jungle/JungleFinal.png"));
                break;
            case 2:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/cliffs/CliffsFinal.png"));
                break;
            case 3:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/underwater/UnderwaterFinal.png"));
                break;
            case 4:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/forrest/ForestFinal.png"));
                break;
            case 5:
                backgroundTexture = new Texture(Gdx.files.internal("backgrounds/space/SpaceFinal.png"));
                break;
        }
    }

    private void loadExitTextures() {
        exitBtnStyleTextures = new Texture[2];
        exitBtnStyleTextures[0] = new Texture(Gdx.files.internal("keys/EscKey_default.png"));
        exitBtnStyleTextures[1] = new Texture(Gdx.files.internal("keys/EscKey_pressed.png"));
    }

    /**
     * Loading the textures for the keys you received for the game.
     *
     */
    private void loadKeyTextures() {
        amountOfPlayers = game.getLogic().getPlayers().size();
        allOriginalKeyTextures = new ArrayList[amountOfPlayers];
        for (int i = 0; i < amountOfPlayers; i++) {
            List<Player> players = game.getLogic().getPlayers();
            allOriginalKeyTextures[i] = new ArrayList<>();
            for (char c : players.get(i).getNodeList()) {
                allOriginalKeyTextures[i].add(new Texture(Gdx.files.internal("keys/" + Character.toString(c) + ".png")));
            }
        }
        allPlayableKeyTextures = allOriginalKeyTextures;
    }

    private void createUiComponents() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        //Creating the UI elements
        ImageButton.ImageButtonStyle exitBtnStyle = new ImageButton.ImageButtonStyle();
        exitBtnStyle.up = new TextureRegionDrawable(new TextureRegion(exitBtnStyleTextures[0]));
        exitBtnStyle.down = new TextureRegionDrawable(new TextureRegion(exitBtnStyleTextures[1]));
        exitBtnStyle.over = new TextureRegionDrawable(new TextureRegion(exitBtnStyleTextures[1]));

        //table preferences
        table.setFillParent(true);
        stage.addActor(table);
        //TODO DEBUG!
        table.setDebug(false); //debugging the ui

        //declaring the elements
        final ImageButton exitBtn = new ImageButton(exitBtnStyle);

        //adding element events
        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        //Drawing the UI components (this is in chronological order)
        table.top().right();
        table.add(exitBtn).size(100, 50).padRight(10).padTop(10);
    }


    //RENDERING OF THE OBJECTS AND TEXTURES


    /**
     * Rendering of the chosen background textures on the screen.
     */
    private void renderBackground() {
        game.spriteBatch.begin();
        game.spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.spriteBatch.end();
    }

    /**
     * Render the screen textures for where the notes are displayed.
     */
    private void renderNoteSection() {
        /*
        //render transparent background
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.1f));
        shapeRenderer.end();

        //render border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        //first line renders line for the first player, second line for player 2
        //shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, height);
        //draw line when there is more then one player
        //TODO add player object from logic

        Gdx.gl20.glLineWidth(10);
        shapeRenderer.end();
        */
    }

    /**
     * Render the texture for the current note that needs to be pressed.
     */
    private void renderCurrentKeyFrame() {
        for(int i = 0; i < amountOfPlayers; i++) {
            int[] position = ScreenHelper.calculateKeyframe(i);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(255, 0, 0, 0.7f);
            shapeRenderer.rect(position[0], position[1], 100, 100);
            Gdx.gl20.glLineWidth(15);
            shapeRenderer.end();
        }
    }

    /**
     * Rendering of the key textures on the screen.
     */
    private void renderKeys() {
        //Render keys for all players
        for(int i = 0; i < amountOfPlayers; i++) {
            for(int o = 0; i < allPlayableKeyTextures[i].size(); i++) {
                if(o == 15) {
                    break;
                }
                int[] playerNoteMargin = ScreenHelper.calculatePlayerKeysMargin(i, o);
                game.spriteBatch.begin();
                game.spriteBatch.draw(allOriginalKeyTextures[i].get(o), playerNoteMargin[0], playerNoteMargin[1], 80, 80);
                game.spriteBatch.end();
            }
        }
    }


    private void handleUserInput() {
        //check if all keys are successfully pressed
        //TODO lege methode??
    }

    @Override
    public void afterCallback(int pos) {
        if (pos <= allOriginalKeyTextures[0].size()) {
            for (ArrayList<Texture> keyTexture : allPlayableKeyTextures) {
                keyTexture.remove(pos - 1);
            }
        } else {
            System.out.println("Gehaald!");
        }

        renderKeys();
    }
}
