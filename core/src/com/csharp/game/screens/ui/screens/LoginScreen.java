package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
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
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemLogin_default", new Texture(Gdx.files.internal("menu/menuItemLogin_default.png")));
        textures.put("menuItemLogin_pressed", new Texture(Gdx.files.internal("menu/menuItemLogin_pressed.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));
    }

    @Override
    public void createUiComponents()
    {
        //Style aanmaken
        TextField.TextFieldStyle defaultTextFieldStyle = new TextField.TextFieldStyle();
        defaultTextFieldStyle.font = skin.getFont("modes");
        defaultTextFieldStyle.fontColor = Color.WHITE;
        defaultTextFieldStyle.focusedFontColor = new Color(0.49f, 0.63f, 0.92f, 1f);
        defaultTextFieldStyle.background = skin.getDrawable("textfieldBackground");
        defaultTextFieldStyle.cursor = skin.getDrawable("white");

        ImageButton.ImageButtonStyle loginButtonStyle = new ImageButton.ImageButtonStyle();
        loginButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_default")));
        loginButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_pressed")));
        loginButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemLogin_pressed")));

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        Label.LabelStyle defaultLabelStyle = new Label.LabelStyle();
        defaultLabelStyle.font = skin.getFont("modes");

        //Nieuwe fields maken
        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final Label loginLabel = new Label("Multiplayer - Login", defaultLabelStyle);
        final TextField nameField = new TextField("", defaultTextFieldStyle);
        final TextField passwordField = new TextField("", defaultTextFieldStyle);
        final ImageButton loginButton = new ImageButton(loginButtonStyle);
        final ImageButton backButton = new ImageButton(backButtonStyle);

        nameField.setAlignment(Align.center);
        nameField.setMessageText("Name:");
        passwordField.setAlignment(Align.center);
        passwordField.setMessageText("Password:");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        loginButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                dispose();
                game.setScreen(new SecondMenuScreen(game));
                //TODO ff inloggen fixen met database
                /*
                System.out.println(String.format("Login aangeroepen, Name: %s, Password: %s", nameField.getText(), passwordField.getText()));

                boolean success = game.getLogic().logIn(nameField.getText(), passwordField.getText());

                if(success)
                {
                    dispose();
                    game.setScreen(new CreateLobbyScreen(game));
                }

                else
                {
                    nameField.setText("ERROR");
                    passwordField.setText("");
                    passwordField.setPasswordMode(true);
                }
                */
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
        table.top().add(backButton).size(80, 50).expandX().align(Align.right).padTop(20).padRight(20).row();
        table.top().add(titleImage).padTop(20).padBottom(10).row();
        table.add(loginLabel).padTop(20).row();
        table.add(nameField).align(Align.center).padBottom(10).padTop(100).size(200, 20).row();
        table.add(passwordField).align(Align.center).padBottom(10).size(200, 20).row();
        table.add(loginButton).padTop(20).size(50);
    }
}
