package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csharp.game.RythemDiscord;

/**
 * 11-12-17
 * RythemDiscord created by Dane Naebers
 */
public class HostGameScreen extends MenuScreen implements IMenuScreen {
    public HostGameScreen(RythemDiscord game) {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));
        textures.put("hostButton_default", new Texture(Gdx.files.internal("menu/hostButton_default.png")));
        textures.put("hostButton_pressed", new Texture(Gdx.files.internal("menu/hostButton_pressed.png")));
    }

    @Override
    public void createUiComponents() {
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        TextField.TextFieldStyle defaultTextFieldStyle = new TextField.TextFieldStyle();
        defaultTextFieldStyle.font = skin.getFont("default");
        defaultTextFieldStyle.fontColor = Color.WHITE;
        defaultTextFieldStyle.focusedFontColor = new Color(0.49f, 0.63f, 0.92f, 1f);
        defaultTextFieldStyle.background = skin.getDrawable("textfieldBackground");
        defaultTextFieldStyle.cursor = skin.getDrawable("white");

        ImageButton.ImageButtonStyle hostButtonStyle = new ImageButton.ImageButtonStyle();
        hostButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("hostButton_default")));
        hostButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("hostButton_pressed")));
        hostButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("hostButton_pressed")));

        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final ImageButton backButton = new ImageButton(backButtonStyle);
        final TextField gameNameField = new TextField("", defaultTextFieldStyle);
        final TextField playerNameField = new TextField("", defaultTextFieldStyle);
        final ImageButton hostButton = new ImageButton(hostButtonStyle);

        gameNameField.setAlignment(Align.center);
        gameNameField.setMessageText("Enter game name");
        playerNameField.setAlignment(Align.center);
        playerNameField.setMessageText("Enter player name");

        hostButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getLogic().newGame(playerNameField.getText());
                dispose();
                game.setScreen(new CreateLobbyScreen(game));
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.top().add(backButton).size(80, 50).expandX().align(Align.right).padTop(20).padRight(20).row();
        table.top().add(titleImage).padTop(20).padBottom(10).row();
        table.add(gameNameField).padTop(120).size(200, 20).row();
        table.add(playerNameField).padTop(20).size(200, 20).row();
        table.add(hostButton).size(100, 40).padTop(20);
    }
}
