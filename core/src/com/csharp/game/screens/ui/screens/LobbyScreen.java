package com.csharp.game.screens.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csharp.game.RythemDiscord;
import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 11-12-17
 * RythemDiscord created by Dane Naebers
 */
public class LobbyScreen extends MenuScreen implements IMenuScreen {

    private String gamekey;

    private Label gamekeyLabel;
    private Image titleImage;
    private Label.LabelStyle defaultLabelStyle;
    private Label playerNameLabel;
    private ImageButton backButton;

    private Table playerTable;


    public LobbyScreen(RythemDiscord game, String gamekey) {
        super(game);
        game.getLogic().setCallback(this);
        this.gamekey = gamekey;
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
    public void render(float delta) {
        //Updating the camera
        camera.update();
        game.spriteBatch.setProjectionMatrix(camera.combined);

        //Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        Gdx.gl.glClearColor(67 / 255f, 71 / 255f, 81 / 255f, 1);

        //rendering the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        drawTable(null);
        stage.draw();
    }

    @Override
    public void createUiComponents() {
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_default")));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));
        backButtonStyle.over = new TextureRegionDrawable(new TextureRegion(textures.get("menuItemBack_pressed")));

        defaultLabelStyle = new Label.LabelStyle();
        defaultLabelStyle.font = skin.getFont("modes");

        gamekeyLabel = new Label(gamekey, defaultLabelStyle);
        titleImage = new Image(textures.get("gameTitle"));
        backButton = new ImageButton(backButtonStyle);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        playerTable = new Table();

        table.top().add(backButton).size(80, 50).expandX().align(Align.right).padTop(20).padRight(20).row();
        table.top().add(titleImage).padTop(20).padBottom(10).row();
        table.add(gamekeyLabel).padTop(100).padBottom(30).row();
    }

    private void drawTable(List<Player> players) {
        if (players == null)
        {
            players = game.getLogic().getPlayers();
        }

        table.removeActor(playerTable);
        playerTable.clear();
        for (Player p : players) {
            playerNameLabel = new Label(p.getName(), defaultLabelStyle);
            playerTable.add(playerNameLabel).row();
        }
        table.add(playerTable).row();
    }

    @Override
    public void callback(DTOClientUpdate callbackUpdate)
    {
        System.out.println("callback called");
        drawTable(callbackUpdate.getNewPlayerList());
    }
}
