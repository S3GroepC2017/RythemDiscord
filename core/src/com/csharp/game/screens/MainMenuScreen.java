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
    private Texture[] menuSpTextures;
    private Texture[] menuMpTextures;
    private Texture[] menuExitTextures;


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
        for (Texture t : menuSpTextures) {
            t.dispose();
        }
        for(Texture t : menuMpTextures) {
            t.dispose();
        }
        for (Texture t : menuExitTextures) {
            t.dispose();
        }
        stage.dispose();
    }

    private void loadTextures() {
        gameTitle = new Texture(Gdx.files.internal("menu/GameTitle.png"));

        menuSpTextures = new Texture[2];
        menuSpTextures[0] = new Texture(Gdx.files.internal("menu/menuItemSP_default.png"));
        menuSpTextures[1] = new Texture(Gdx.files.internal("menu/menuItemSP_default.png")); //TODO PRESSED

        menuMpTextures = new Texture[2];
        menuMpTextures[0] = new Texture(Gdx.files.internal("menu/menuItemMP_default.png"));
        menuMpTextures[1] = new Texture(Gdx.files.internal("menu/menuItemMP_default.png")); //TODO PRESSED

        menuExitTextures = new Texture[2];
        menuExitTextures[0] = new Texture(Gdx.files.internal("menu/menuItemExit_default.png"));
        menuExitTextures[1] = new Texture(Gdx.files.internal("menu/menuItemExit_pressed.png"));
    }

    private void createUiComponents() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        //Creating the UI elements
        ImageButton.ImageButtonStyle spBtnStyle = new ImageButton.ImageButtonStyle();
        spBtnStyle.up = new TextureRegionDrawable(new TextureRegion(menuSpTextures[0]));
        spBtnStyle.down = new TextureRegionDrawable(new TextureRegion(menuSpTextures[1]));
        spBtnStyle.over = new TextureRegionDrawable(new TextureRegion(menuSpTextures[1]));

        ImageButton.ImageButtonStyle mpButtonStyle = new ImageButton.ImageButtonStyle();
        mpButtonStyle.up = new TextureRegionDrawable(new TextureRegion(menuMpTextures[0]));
        mpButtonStyle.down = new TextureRegionDrawable(new TextureRegion(menuMpTextures[1]));
        mpButtonStyle.over = new TextureRegionDrawable(new TextureRegion(menuMpTextures[1]));

        ImageButton.ImageButtonStyle exitBtnStyle = new ImageButton.ImageButtonStyle();
        exitBtnStyle.up = new TextureRegionDrawable(new TextureRegion(menuExitTextures[0]));
        exitBtnStyle.down = new TextureRegionDrawable(new TextureRegion(menuExitTextures[1]));
        exitBtnStyle.over = new TextureRegionDrawable(new TextureRegion(menuExitTextures[1]));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());

        //table preferences
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true); //debugging the ui

        //declaring the elements
        final Image titleImage = new Image(gameTitle);
        final Label creatorsLabel0 = new Label("A game made by:", labelStyle);
        final Label creatorsLabel1 = new Label("Michelle, Niels, Joe, Teun, Lars and Dane", labelStyle);

        final ImageButton spBtn = new ImageButton(spBtnStyle);
        final ImageButton mpBtn = new ImageButton(mpButtonStyle);
        final ImageButton exitBtn = new ImageButton(exitBtnStyle);


        //adding element events
        spBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        mpBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
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
        table.add(titleImage).row();
        table.add(creatorsLabel0).pad(10).row();
        table.add(creatorsLabel1).row();
        table.add(spBtn).size(200, 25).padBottom(10).padTop(50).row();
        table.add(mpBtn).size(200, 25).padBottom(10).row();
        table.add(exitBtn).size(100, 25).row();

    }
}
