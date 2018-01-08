package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csharp.game.RythemDiscord;

import java.util.HashMap;
import java.util.Map;

public abstract class MenuScreen implements Screen {
    final RythemDiscord game;

    //Textures
    public Map<String, Texture> textures;

    //UI Items
    public Skin skin;
    public Table table;
    public Stage stage;

    //Camera & Viewport
    public OrthographicCamera camera;
    public Viewport viewport;

    public MenuScreen(RythemDiscord game) {
        this.game = game;


        this.skin = new Skin();
        this.table = new Table();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(1000, 1000, this.camera);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        loadUIComponents();
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

    @Override
    public void dispose() {
        for(Map.Entry<String, Texture> entry : textures.entrySet()) {
            entry.getValue().dispose();
        }
        this.skin.dispose();
        this.stage.dispose();
    }


    private void loadUIComponents() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));
        skin.add("modes", new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter()));
        skin.add("textfieldBackground", new Texture(Gdx.files.internal("skin/textfieldBackground.png")));
        skin.add("default", new BitmapFont());

        textures = new HashMap<>();

        //table preferences
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true); //debugging the ui
    }

    public void updateTable() {

    }
}
