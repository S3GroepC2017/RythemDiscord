package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csharp.game.RythemDiscord;

/**
 * RythemDiscord
 *
 * @author Groep C#
 *
 * This class is the application menu screen.
 */
public class MainMenuScreen implements Screen {
    final RythemDiscord game;

    //UI Items
    private Skin skin;
    private Table table;
    private Stage stage;

    //Camera & Viewport
    private OrthographicCamera camera;
    private Viewport viewport;

    //textures and renderers
    private Texture gameTitle;
    private Texture[] menuStart;
    private Texture[] menuExit;


    public MainMenuScreen(RythemDiscord game) {
        this.game = game;

        this.skin = new Skin();
        this.table = new Table();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(1000, 1000, this.camera);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        //loading textures
        loadTextures();

        //loading of UI components
        createUiComponents();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Updating the camera
        this.camera.update();
        this.game.spriteBatch.setProjectionMatrix(this.camera.combined);

        //Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        Gdx.gl.glClearColor(67 / 255f, 71 / 255f, 81 / 255f, 1);

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
        gameTitle.dispose();
        for (Texture t : menuStart) {
            t.dispose();
        }
        for (Texture t : menuExit) {
            t.dispose();
        }
        stage.dispose();
    }

    private void loadTextures() {
        gameTitle = new Texture(Gdx.files.internal("menu/GameTitle.png"));

        menuStart = new Texture[2];
        menuStart[0] = new Texture(Gdx.files.internal("menu/menuItemStart_default.png"));
        menuStart[1] = new Texture(Gdx.files.internal("menu/menuItemStart_pressed.png"));

        menuExit = new Texture[2];
        menuExit[0] = new Texture(Gdx.files.internal("menu/menuItemExit_default.png"));
        menuExit[1] = new Texture(Gdx.files.internal("menu/menuItemExit_pressed.png"));
    }

    private void createUiComponents() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        //Creating the UI elements
        ImageButton.ImageButtonStyle startBtnStyle = new ImageButton.ImageButtonStyle();
        startBtnStyle.up = new TextureRegionDrawable(new TextureRegion(menuStart[0]));
        startBtnStyle.down = new TextureRegionDrawable(new TextureRegion(menuStart[1]));
        startBtnStyle.over = new TextureRegionDrawable(new TextureRegion(menuStart[1]));

        ImageButton.ImageButtonStyle exitBtnStyle = new ImageButton.ImageButtonStyle();
        exitBtnStyle.up = new TextureRegionDrawable(new TextureRegion(menuExit[0]));
        exitBtnStyle.down = new TextureRegionDrawable(new TextureRegion(menuExit[1]));
        exitBtnStyle.over = new TextureRegionDrawable(new TextureRegion(menuExit[1]));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());

        //table preferences
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true); //debugging the ui

        //declaring the elements
        final Image titleImage = new Image(gameTitle);
        final ImageButton startBtn = new ImageButton(startBtnStyle);
        final ImageButton exitBtn = new ImageButton(exitBtnStyle);
        final Label creatorsLabel0 = new Label("A game made by:", labelStyle);
        final Label creatorsLabel1 = new Label("Michelle, Niels, Joe, Teun, Lars and Dane", labelStyle);

        //adding element events
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                System.exit(0);
            }
        });

        //Drawing the UI components (this is in chronological order)
        table.top().padTop(20);
        table.add(titleImage);
        table.row();
        table.add(creatorsLabel0).pad(10);
        table.add().row();
        table.add(creatorsLabel1);
    }
}
