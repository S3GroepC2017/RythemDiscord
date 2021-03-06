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
public class JoinGameScreen extends MenuScreen implements IMenuScreen {

    public JoinGameScreen(RythemDiscord game) {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));
        textures.put("joinButton_default", new Texture(Gdx.files.internal("menu/joinButton_default.png")));
        textures.put("joinButton_pressed", new Texture(Gdx.files.internal("menu/joinButton_pressed.png")));
    }

    @Override
    public void createUiComponents() {
        TextField.TextFieldStyle defaultTextFieldStyle = new TextField.TextFieldStyle();
        defaultTextFieldStyle.font = skin.getFont("modes");
        defaultTextFieldStyle.fontColor = Color.WHITE;
        defaultTextFieldStyle.focusedFontColor = new Color(0.49f, 0.63f, 0.92f, 1f);
        defaultTextFieldStyle.background = skin.getDrawable("textfieldBackground");
        defaultTextFieldStyle.cursor = skin.getDrawable("white");

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        ImageButton.ImageButtonStyle joinButtonStyle = new ImageButton.ImageButtonStyle();
        joinButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_default")));
        joinButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_pressed")));
        joinButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_pressed")));

        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final TextField playerNameField = new TextField("", defaultTextFieldStyle);
        final TextField ipField = new TextField("",defaultTextFieldStyle);
        final TextField gamekeyField = new TextField("", defaultTextFieldStyle);
        final ImageButton backButton = new ImageButton(backButtonStyle);
        final ImageButton joinButton = new ImageButton(joinButtonStyle);

        playerNameField.setAlignment(Align.center);
        playerNameField.setMessageText("Enter player name");
        ipField.setAlignment(Align.center);
        ipField.setMessageText("IP Address");
        gamekeyField.setAlignment(Align.center);
        gamekeyField.setMessageText("Gamekey");

        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getLogic().joinGame(gamekeyField.getText(), playerNameField.getText());
                dispose();
                game.setScreen(new LobbyScreen(game));
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
        table.add(playerNameField).padTop(140).size(200, 20).row();
        table.add(ipField).padTop(20).size(200, 20).row();
        table.add(gamekeyField).pad(20).size(200, 20).row();
        table.add(joinButton).size(100, 40).padTop(20);
    }
}
