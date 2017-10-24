package com.csharp.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

/**
 * RythemDiscord
 * Created by dane on 24-10-17.
 *
 * This class is the main graphics class
 * in the class all the items and objects are created, generated and disposed.
 * This class is also responsible for noticing inputs and passing it trough.
 */

public class RythemDiscord extends ApplicationAdapter {

	private InputManager inputManager;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Texture> backgroundTextures;

    //This method initilizes all items needed for the game.
    @Override
	public void create () {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		loadBackgroundItems();

		//creation of input handling
		inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
	}


    //This method draws everything to the screen.
    //The other of rendering is crucial. (items stack on top of each other)!!
	@Override
	public void render () {
		//clearing the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//render background alsways needs to be the first thing to render
		renderBackground();

		//rendering of the note section (play area)
		renderNoteSection();

		//rendering of the generated keys
        //TODO

		//rendering of the current keyframe (Key that needs to be pressed)
        renderCurrentKeyFrame();
	}

	//this method clears the items and releases them from memory.
	@Override
	public void dispose () {
        inputManager = null;
		spriteBatch.dispose();
		shapeRenderer.dispose();
		for(Texture t : backgroundTextures) {
			t.dispose();
		}
	}

	private void loadBackgroundItems() {
		//choosing and loading the background
		Random rand =  new Random();
		backgroundTextures = new ArrayList<Texture>();
		switch(rand.nextInt(4)) {
			case 0:
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/far-buildings.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/back-buildings.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/punkcity/foreground.png")));
			break;
			case 1:
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-1.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-2.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-3.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-4.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/jungle/plx-5.png")));
			break;
			case 2:
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/cliffs/sky.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/cliffs/clouds.png")));
			break;
			case 3:
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/far.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/sand.png")));
				backgroundTextures.add(new Texture(Gdx.files.internal("backgrounds/underwater/foreground-merged.png")));
			break;
		}
	}

	private void renderBackground() {
		spriteBatch.begin();
		for(Texture t : backgroundTextures) {
			spriteBatch.draw(t , 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		spriteBatch.end();
	}

	private void renderNoteSection() {
		//render transparent background
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(new Color(1, 1, 1, 0.1f));
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), 200);
		shapeRenderer.end();
		//render border
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(0,0,0,1);
		shapeRenderer.rect(4, 4, Gdx.graphics.getWidth() - 8, 200);
		Gdx.gl20.glLineWidth(10);
		shapeRenderer.end();
	}

	private void renderCurrentKeyFrame() {
        //render current payble note
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(255,0,0,0.7f);
        shapeRenderer.rect(50, 50, 100, 100);
        Gdx.gl20.glLineWidth(15);
        shapeRenderer.end();
    }
}
