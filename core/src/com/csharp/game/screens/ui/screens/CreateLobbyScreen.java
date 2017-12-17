package com.csharp.game.screens.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csharp.game.RythemDiscord;

public class CreateLobbyScreen extends MenuScreen implements IMenuScreen {

    //textures and renderers

    public CreateLobbyScreen(RythemDiscord game) {
        super(game);
        loadTextures();
        createUiComponents();
    }

    @Override
    public void loadTextures() {
        textures.put("gameTitle", new Texture(Gdx.files.internal("menu/GameTitle.png")));
        textures.put("menuItemBack_default", new Texture(Gdx.files.internal("keys/EscKey_default.png")));
        textures.put("menuItemBack_pressed", new Texture(Gdx.files.internal("keys/EscKey_pressed.png")));

    }

    @Override
    public void createUiComponents() {
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        final com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new Image(textures.get("gameTitle"));
        final ImageButton backButton = new ImageButton(backButtonStyle);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.top().add(backButton).size(80, 50).expandX().align(Align.right).padTop(20).padRight(20).row();
        table.top().add(titleImage).padTop(20).padBottom(10).row();
    }
}
