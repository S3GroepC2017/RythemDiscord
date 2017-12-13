package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csharp.game.RythemDiscord;

import java.util.HashMap;

/**
 * 11-12-17
 * RythemDiscord created by Dane Naebers
 */
public class SecondMenuScreen extends MenuScreen implements IMenuScreen {

    public SecondMenuScreen(RythemDiscord game) {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));
        textures.put("joinGameButton_default", new Texture(Gdx.files.internal("menu/joinGameButton_default.png")));
        textures.put("joinGameButton_pressed", new Texture(Gdx.files.internal("menu/joinGameButton_pressed.png")));
        textures.put("hostGameButton_default", new Texture(Gdx.files.internal("menu/hostGameButton_default.png")));
        textures.put("hostGameButton_pressed",  new Texture(Gdx.files.internal("menu/hostGameButton_pressed.png")));
    }

    @Override
    public void createUiComponents() {
        Label.LabelStyle defaultLabelStyle = new Label.LabelStyle();
        defaultLabelStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());

        ImageButton.ImageButtonStyle joinGameButtonStyle = new ImageButton.ImageButtonStyle();
        joinGameButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("joinGameButton_default")));
        joinGameButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("joinGameButton_pressed")));
        joinGameButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("joinGameButton_pressed")));

        ImageButton.ImageButtonStyle hostGameButtonStyle = new ImageButton.ImageButtonStyle();
        hostGameButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("hostGameButton_default")));
        hostGameButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("hostGameButton_pressed")));
        hostGameButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("hostGameButton_pressed")));

        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final ImageButton joinGameButton = new ImageButton(joinGameButtonStyle);
        final ImageButton hostGameButton = new ImageButton(hostGameButtonStyle);
        final ImageButton backButton = new ImageButton(backButtonStyle);
        final Label text = new Label("Choose an option", defaultLabelStyle);

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new JoinGameScreen(game));
            }
        });

        hostGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HostGameScreen(game));
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
        table.add(text).padTop(120).row();
        table.add(joinGameButton).size(200, 65).padTop(50).row();
        table.add(hostGameButton).size(200, 65).padTop(20);
    }
}
