package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.KeyPressedResult;

import java.util.HashMap;

/**
 * RythemDiscord
 *
 * @author Groep C#
 *
 * This class is the application menu screen.
 */
public class MainMenuScreen extends MenuScreen implements IMenuScreen {

    public MainMenuScreen(RythemDiscord game) {
        super(game);

        //loading textures
        loadTextures();

        //loading of UI components
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemSP_default", new Texture(Gdx.files.internal("menu/menuItemSP_default.png")));
        textures.put("menuItemSP_pressed", new Texture(Gdx.files.internal("menu/menuItemSP_pressed.png")));
        textures.put("menuItemMP_default", new Texture(Gdx.files.internal("menu/menuItemMP_default.png")));
        textures.put("menuItemMP_pressed", new Texture(Gdx.files.internal("menu/menuItemMP_pressed.png")));
        textures.put("menuItemExit_default", new Texture(Gdx.files.internal("menu/menuItemExit_default.png")));
        textures.put("menuItemExit_pressed", new Texture(Gdx.files.internal("menu/menuItemExit_pressed.png")));
    }

    @Override
    public void createUiComponents() {
        //Creating the UI elements
        ImageButton.ImageButtonStyle spBtnStyle = new ImageButton.ImageButtonStyle();
        spBtnStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemSP_default")));
        spBtnStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemSP_pressed")));
        spBtnStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemSP_pressed")));

        ImageButton.ImageButtonStyle mpButtonStyle = new ImageButton.ImageButtonStyle();
        mpButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemMP_default")));
        mpButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemMP_pressed")));
        mpButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemMP_pressed")));

        ImageButton.ImageButtonStyle exitBtnStyle = new ImageButton.ImageButtonStyle();
        exitBtnStyle.up = new TextureRegionDrawable(new TextureRegion(super.textures.get("menuItemExit_default")));
        exitBtnStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemExit_pressed")));
        exitBtnStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemExit_pressed")));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("modes");

        //declaring the elements
        final Image titleImage = new Image(textures.get("gameTitle"));
        final Label creatorsLabel0 = new Label("A game made by:", labelStyle);
        final Label creatorsLabel1 = new Label("Michelle, Niels, Joe, Teun, Lars and Dane", labelStyle);

        final ImageButton spBtn = new ImageButton(spBtnStyle);
        final ImageButton mpBtn = new ImageButton(mpButtonStyle);
        final ImageButton exitBtn = new ImageButton(exitBtnStyle);

        //adding element events
        spBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String gameKey =  game.getLogic().newGame();
                if(game.getLogic().joinGame(gameKey)) {
                    game.getLogic().startGame();
                }
            }
        });

        mpBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
                dispose();
                game.setScreen(new LoginScreen(game));
            }
        });


        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.dispose();
                System.exit(0);
            }
        });

        //Drawing the UI components (this is in chronological order)
        table.top().padTop(90).row();
        table.add(titleImage).row();
        table.add(creatorsLabel0).pad(10).row();
        table.add(creatorsLabel1).row();
        table.add(spBtn).size(200, 25).padBottom(10).padTop(50).row();
        table.add(mpBtn).size(200, 25).padBottom(10).row();
        table.add(exitBtn).size(100, 25).row();
    }

    @Override
    public void callback(DTOClientUpdate callbackUpdate)
    {
        if (callbackUpdate.getNewKeyPressResult() == KeyPressedResult.STARTUP)
        {
            Gdx.app.postRunnable(this::switchToGameScreen);
        }
    }

    private void switchToGameScreen()
    {
        dispose();
        game.setScreen(new GameScreen(game));
    }
}
