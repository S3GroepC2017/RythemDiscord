package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.csharp.game.RythemDiscord;
import java.util.HashMap;

public class LoginScreen extends MenuScreen implements IMenuScreen
{
    public LoginScreen(RythemDiscord game)
    {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures()
    {
        //Alle textures inladen
        textures = new HashMap<String, Texture>();
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemLogin_default", new Texture(Gdx.files.internal("menu/menuItemLogin_default.png")));
        textures.put("menuItemLogin_pressed", new Texture(Gdx.files.internal("menu/menuItemLogin_pressed.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("menu/menuItemBack_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("menu/menuItemBack_pressed.png")));
    }

    @Override
    public void createUiComponents()
    {
        //Style aanmaken
        TextField.TextFieldStyle defaultTextFieldStyle = new TextField.TextFieldStyle();
        defaultTextFieldStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());
        defaultTextFieldStyle.fontColor = Color.WHITE;

        ImageButton.ImageButtonStyle loginButtonStyle = new ImageButton.ImageButtonStyle();
        loginButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_default")));
        loginButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_pressed")));
        loginButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_pressed")));

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        Label.LabelStyle defaultlabelStyle = new Label.LabelStyle();
        defaultlabelStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());

        //Nieuwe fields maken
        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final Label loginLabel = new Label("Multiplayer - Login", defaultlabelStyle);
        final TextField nameField = new TextField("Name:", defaultTextFieldStyle);
        final TextField passwordField = new TextField("Password:", defaultTextFieldStyle);
        final ImageButton loginButton = new ImageButton(loginButtonStyle);
        final ImageButton backButton = new ImageButton(backButtonStyle);

        nameField.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                if (nameField.getText().equals("Name:"))
                {
                    nameField.setText("");
                }

                return false;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character)
            {
                if (character == '\t')
                {
                    stage.setKeyboardFocus(passwordField);
                }

                return false;
            }
        });

        passwordField.addListener(new FocusListener()
        {
            //Wanneer focus komt op password field, text weghalen
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused)
            {
                if (passwordField.getText().equals("Password:"))
                {
                    passwordField.setText("");
                    passwordField.setPasswordMode(true);
                    passwordField.setPasswordCharacter('*');
                }
            }
        });

        loginButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println(String.format("Login aangeroepen, Name: %s, Password: %s", nameField.getText(), passwordField.getText()));
            }
        });

        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        //Fields toevoegen aan de tabel
        table.top().add(titleImage).padTop(20).padBottom(10).row();
        table.add(loginLabel).padBottom(20).row();
        table.add(nameField).padBottom(10).padTop(100).row();
        table.add(passwordField).padBottom(10).row();
        table.add(loginButton).size(50).padBottom(50).row();
        table.add(backButton).size(50);
    }
}
