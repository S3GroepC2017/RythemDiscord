package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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


    //Font
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout glyphLayout;
    private float width;

    public MainMenuScreen(RythemDiscord game)
    {
        this.game = game;

        this.skin = new Skin();
        this.table = new Table();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(100, 100, this.camera);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight/2, 0);


        //loading UI components
        loadUiComponents();



        //loadFont();
        //loadCreatorsText();
        loadTextures();
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        //Updating the camera
        this.camera.update();
        this.game.spriteBatch.setProjectionMatrix(this.camera.combined);

        //Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        Gdx.gl.glClearColor(67 / 255f, 71 / 255f, 81 / 255f, 1);

        //render game title
        renderGameTitle();

        //rendering the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        //render menu buttons
        //renderMenuButtons();

        //handle user input
        handleUserInput();
    }

    @Override
    public void resize(int width, int height)
    {
        //Viewport and Camera update for SpriteBatch
        this.viewport.update(width, height);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        //Viewport for UI Stage
        this.stage.getViewport().update(width, height, true);
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
        stage.dispose();
        for (Texture t : menuStart) {
            t.dispose();
        }
        for(Texture t : menuExit) {
            t.dispose();
        }
    }

    private void loadUiComponents() {

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);

        final TextButton button = new TextButton("start", skin);
        table.add(button);
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
        game.spriteBatch.draw(gameTitle, 50 - 17, 75, 35, 20);
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
