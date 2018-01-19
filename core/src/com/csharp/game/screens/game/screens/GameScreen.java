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
import com.csharp.game.screens.game.screens.ScreenHelper;
import com.csharp.game.screens.TextureKeyContainer;
import com.csharp.game.screens.ui.screens.MainMenuScreen;
import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.KeyPressedResult;
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
    private TextureKeyContainer allkeys;
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
        game.getLogic().setCallback(this);
        this.game = game;

        //Textures voor amount of players laden
        amountOfPlayers = game.getLogic().getPlayers().size();

        //Alle keys die mogelijk zijn laden
        allkeys = new TextureKeyContainer().fromKeyCharArray("qwertyuiopasdfghjklzxcvbnm".toCharArray());

        allOriginalKeyTextures = new ArrayList[amountOfPlayers];
        allPlayableKeyTextures = new ArrayList[amountOfPlayers];

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
        loadKeyTextures(game.getLogic().getPlayers());

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

        //rendering of the generated keys
        renderKeys();

        //rendering of the current keyframe (Key that needs to be pressed)
        renderCurrentKeyFrame();

        //rendering the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
     * @param players player array containing the keys for your game.
     */
    private void loadKeyTextures(List<Player> players) {
        //TODO unique textures for each player
        for (int i = 0; i < amountOfPlayers; i++) {
            Player player = players.get(i);

            allOriginalKeyTextures[i] = new ArrayList<>();

            for (char key : player.getNodeList()) {
                allOriginalKeyTextures[i].add(allkeys.get(key));
            }

            allPlayableKeyTextures[i] = new ArrayList<>(allOriginalKeyTextures[i]);
        }
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
                dispose();
                game.setScreen(new MainMenuScreen(game));
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
     * Render the texture for the current note that needs to be pressed.
     */
    private void renderCurrentKeyFrame() {
        for (int i = 0; i < amountOfPlayers; i++) {
            int[] position = ScreenHelper.calculateKeyframe(i);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(255, 0, 0, 0.7f);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 255, 0, 0.7f);
                    break;
                case 2:
                    shapeRenderer.setColor(0, 0, 255, 0.7f);
                    break;
                case 3:
                    shapeRenderer.setColor(0, 0.5f, 0.5f, 0.7f);
                    break;
            }
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
        game.spriteBatch.begin();
        for (int i = 0; i < amountOfPlayers; i++) {
            for (int o = 0; o < allPlayableKeyTextures[i].size(); o++) {
                if (o == 15) {
                    break;
                }

                int[] playerNoteMargin = ScreenHelper.calculatePlayerKeysMargin(i, o);
                if (allPlayableKeyTextures[i].get(o) != null) {
                    game.spriteBatch.draw(allPlayableKeyTextures[i].get(o), playerNoteMargin[0], playerNoteMargin[1], 90, 90);
                }
            }
        }
        game.spriteBatch.end();
    }

    private void removeFirstKeyFromArrays() {
        for (ArrayList<Texture> keyTexture : allPlayableKeyTextures) {
            keyTexture.remove(0);
        }
    }

    @Override
    public void callback(DTOClientUpdate callbackUpdate) {
        KeyPressedResult keyPressedResult = callbackUpdate.getNewKeyPressResult();
        List<Player> updatedPlayerList = callbackUpdate.getNewPlayerList();

        System.out.println(updatedPlayerList);
        System.out.println(game.getLogic().getPlayers());

        System.out.println("received keypressresult: " + keyPressedResult.toString());

        switch (keyPressedResult) {
            case NONE:
                break;
            case WRONG:
                for (int i = 0; i < allOriginalKeyTextures.length; i++) {
                    allPlayableKeyTextures[i] = new ArrayList<>(allOriginalKeyTextures[i]);
                }
                renderKeys();
                break;
            case SEQUENCE_FINISHED:
                System.out.println("Sequence ended");
                System.out.println("new keys: " + callbackUpdate.getNewPlayerList());
                loadKeyTextures(updatedPlayerList);
                renderKeys();
                break;
            case CORRECT:
                removeFirstKeyFromArrays();
                renderKeys();
                break;
            case GAME_FINISHED:
                break;
        }
    }
}
