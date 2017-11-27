package com.csharp.game.screens.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.csharp.game.RythemDiscord;

import java.util.HashMap;

public class CreateLobbyScreen extends MenuScreen implements IMenuScreen {

    //textures and renderers

    public CreateLobbyScreen(RythemDiscord game) {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures = new HashMap<>();
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
    }

    @Override
    public void createUiComponents() {

        Label.LabelStyle defaultLabelStyle = new Label.LabelStyle();
        defaultLabelStyle.font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MODES.TTF")).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());
        defaultLabelStyle.fontColor = Color.WHITE;

        final Label testLabel = new Label("TEST", defaultLabelStyle);
        final Label testLabel2 = new Label("TEST2", defaultLabelStyle);

        final Image titleImage = new Image(textures.get("gameTitle"));

        table.debug();

        VerticalGroup leftMenu = new VerticalGroup();
        leftMenu.addActor(testLabel);
        leftMenu.addActor(testLabel2);


        table.top().add(titleImage).padTop(20).row();

    }
}
