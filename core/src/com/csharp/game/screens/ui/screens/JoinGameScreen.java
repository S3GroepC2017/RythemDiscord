package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csharp.game.RythemDiscord;

import java.util.HashMap;

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
        textures = new HashMap<>();
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));
        textures.put("joinButton_default", new Texture(Gdx.files.internal("menu/joinButton_default.png")));
        textures.put("joinButton_pressed", new Texture(Gdx.files.internal("menu/joinButton_pressed.png")));
    }

    @Override
    public void createUiComponents() {
        TextField.TextFieldStyle defaultTextFieldStyle = new TextField.TextFieldStyle();
        defaultTextFieldStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());
        defaultTextFieldStyle.fontColor = Color.WHITE;
        defaultTextFieldStyle.focusedFontColor = new Color(0.49f, 0.63f, 0.92f, 1f);

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        ImageButton.ImageButtonStyle joinButtonStyle = new ImageButton.ImageButtonStyle();
        joinButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_default")));
        joinButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_pressed")));
        joinButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("joinButton_pressed")));

        final TextField playerNameField = new TextField("Enter name", defaultTextFieldStyle);
        final TextField ipField = new TextField("IP address", defaultTextFieldStyle);
        final TextField gamekeyField = new TextField("Gamekey", defaultTextFieldStyle);
        final ImageButton backButton = new ImageButton(backButtonStyle);
        final ImageButton joinButton = new ImageButton(joinButtonStyle);

        playerNameField.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playerNameField.getText().equals("Enter name")) {
                    playerNameField.setText("");
                }
                return false;
            }
        });

        ipField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(ipField.getText().equals("IP address")) {
                    ipField.setText("");
                }
                return false;
            }
        });

        gamekeyField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(gamekeyField.getText().equals("Gamekey")) {
                    gamekeyField.setText("");
                }
                return false;
            }
        });

        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getLogic().joinGame(gamekeyField.getText());
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
        table.add(playerNameField).padTop(200).row();
        table.add(ipField).padTop(20).row();
        table.add(gamekeyField).pad(20).row();
        table.add(joinButton).size(150, 60).padTop(50);
    }
}
