package com.csharp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureKeyContainer
{
    private Texture[] textures;
    private char[] keyChars;

    public TextureKeyContainer fromKeyCharArray(char[] keyChars)
    {
        this.keyChars = keyChars;
        this.textures = new Texture[keyChars.length];

        for(int i = 0; i < this.keyChars.length; i++)
        {
            Texture keyTexture = new Texture(Gdx.files.internal("keys/" + Character.toString(this.keyChars[i]) + ".png"));
            this.textures[i] = keyTexture;
        }

        return this;
    }

    public Texture get(char keyChar)
    {
        for(int i = 0; i < this.keyChars.length; i++)
        {
            if(this.keyChars[i] == keyChar)
            {
                return this.textures[i];
            }
        }

        return null;
    }
}
