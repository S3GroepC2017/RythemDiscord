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
import com.csharp.game.screens.ScreenHelper;
import com.csharp.game.screens.ui.screens.MainMenuScreen;
import com.csharp.sharedclasses.Player;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private InputMultiplexer inputMultiplexer;
    private InputManager inputManager;  //!!maybe not needed
    private int playerIndex = 4;

    //UI Items
    private Skin skin;
    private Table table;
    private Stage stage;

    //Camera & Viewport
    private OrthographicCamera camera;
    private Viewport viewport;

    //TODO Screen helper class
    ScreenHelper screenHelper = new ScreenHelper();

    //TODO fill amountOfPlayers
    private int amountOfPlayers;

    //TODO original- and playableKeyTextures
    private ArrayList<Texture>[] allOriginalKeyTextures;
    private ArrayList<Texture>[] allPlayableKeyTextures;

    //textures and renderers
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;
    private Texture[] exitBtnStyleTextures;

    // this is the Timer that gets the current position from the logic at a scheduled rate
    private AnimationTimer timer;

    // the nano seconds after which the timer to contact the logic tics
    private static long NANO_TICKS = 250000000;

    /**
     * Public constructor for GameScreen
     *
     * @param game the root game object that holds the global SpirteBatch for rendering items on the screen.
     */
    public GameScreen(final RythemDiscord game) {
        this.game = game;
        game.getLogic().newGame();
        game.getLogic().startGame();

        //Textures voor amount of players laden
        amountOfPlayers = game.getLogic().getPlayers().size();
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

        // setup the timer that creates the correct lists of nodes with the nodes and index received from the Logic
        this.timer = new AnimationTimer()
        {
            private long prevUpdate;

            @Override
            public void handle(long now)
            {
                long lag = now - prevUpdate;
                if (lag >= NANO_TICKS)
                {
                    handleAnimationTimer();
                    prevUpdate = now;
                }
            }

            @Override
            public void start()
            {
                prevUpdate = System.nanoTime();
                super.start();
            }
        };

        // start the timer that gets the keys from the logic and prints them on the screen
        timer.start();

        //TODO Remove this when replaced with the AnimationTimer
        //TODO replace with unique keys foreach player
        //TODO uncomment next line
//        loadKeyTextures(game.getLogic().getPlayers());

        handleAnimationTimer();

        //loading of UI components
        createUiComponents();
    }

    private void handleAnimationTimer()
    {
        List<Player> players = game.getLogic().getPlayers();
        for (Player player : players)
        {
            char[] fixedNodes = player.getNodeList();
            int originalLength = fixedNodes.length;
            while (fixedNodes.length > originalLength - game.getLogic().getCurrentPosition())
            {
                fixedNodes = Arrays.copyOfRange(fixedNodes, 1, fixedNodes.length-1);
            }
            player.setNodeList(fixedNodes);
        }

        loadKeyTextures(players);
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

        for (ArrayList<Texture> tex : allOriginalKeyTextures)
        {
            for (Texture t : tex)
            {
                t.dispose();
            }
        }

        for (ArrayList<Texture> tex : allPlayableKeyTextures)
        {
            for (Texture t : tex)
            {
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


//    private void loadKeyTextures(char[] keys) {
//        //TODO unique textures for each player
//        for (int i = 0; i < amountOfPlayers; i++) {
//            allOriginalKeyTextures[i] = new ArrayList<Texture>();
//            allPlayableKeyTextures[i] = new ArrayList<Texture>();
//
//            for (char key : keys) {
//                Texture keyTexture = new Texture(Gdx.files.internal("keys/" + Character.toString(key) + ".png"));
//                allOriginalKeyTextures[i].add(keyTexture);
//                allPlayableKeyTextures[i].add(keyTexture);
//            }
//        }
//    }

    /**
     * Loading the textures for the keys you received for the game.
     *
     * @param players player array containing the keys for your game.
     */
    private void loadKeyTextures(List<Player> players) {
        //TODO unique textures for each player
        for (int i = 0; i < amountOfPlayers; i++) {
            Player player = players.get(i);

            allOriginalKeyTextures[i] = new ArrayList<Texture>();
            allPlayableKeyTextures[i] = new ArrayList<Texture>();

            for (char key : player.getNodeList()) {
                Texture keyTexture = new Texture(Gdx.files.internal("keys/" + Character.toString(key) + ".png"));
                allOriginalKeyTextures[i].add(keyTexture);
                allPlayableKeyTextures[i].add(keyTexture);
            }
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
        int height = 200;
        int transparentHeight = 200;
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
        shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, height);
        //draw line when there is more then one player
        //TODO add player object from logic
        for (int i = 0; i < playerIndex; i++)
        {
            transparentHeight = transparentHeight + 200;
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), transparentHeight);
            shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, height);
        }
        Gdx.gl20.glLineWidth(10);
        shapeRenderer.end();
    }

    /**
     * Render the texture for the current note that needs to be pressed.
     */
    private void renderCurrentKeyFrame() {
        //render keyframe from current playable note
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(255, 0, 0, 0.7f);
        shapeRenderer.rect(50, 50, 100, 100);
        Gdx.gl20.glLineWidth(15);
        shapeRenderer.end();
    }

    /**
     * Rendering of the key textures on the screen.
     */
    private void renderKeys() {
        //Render keys for all players
        for (int i = 0; i < allPlayableKeyTextures.length; i++) {
            ArrayList<Texture> tex = allPlayableKeyTextures[i];

            if (!tex.isEmpty()) {
                game.spriteBatch.begin();
                //game.spriteBatch.draw(tex.get(0), 60, 60, 80, 80); //current key

                //TODO meerdere rijen
                for (Texture t : tex) {
                    if (tex.indexOf(t) == 15) {
                        break;
                    }
                    game.spriteBatch.draw(t, screenHelper.calculateHMargin(tex.indexOf(t)), screenHelper.calculateVMargin(i), 80, 100); //key
                }
                game.spriteBatch.end();
            }
        }
    }


    private void handleUserInput()
    {
        //check if all keys are successfully pressed


    }
}
