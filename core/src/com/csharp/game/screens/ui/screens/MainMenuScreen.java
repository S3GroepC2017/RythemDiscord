package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csharp.game.RythemDiscord;
import com.csharp.game.screens.game.screens.GameScreen;

/**
 * RythemDiscord
 *
 * @author Groep C#
 *
 * This class is the application menu screen.
 */
public class MainMenuScreen extends UIScreen {

    //textures and renderers
    private Texture gameTitle;
    private Texture[] menuSpTextures;
    private Texture[] menuMpTextures;
    private Texture[] menuExitTextures;


    public MainMenuScreen(RythemDiscord game) {
        super(game);

        //loading textures
        loadTextures();

        //loading of UI components
        createUiComponents();
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

    public void dispose() {
        gameTitle.dispose();
        for(Texture t : menuSpTextures) {
            t.dispose();
        }
        for(Texture t : menuMpTextures) {
            t.dispose();
        }
        for(Texture t : menuExitTextures) {
            t.dispose();
        }
    }
}
