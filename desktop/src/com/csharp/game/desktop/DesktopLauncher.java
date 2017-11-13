package com.csharp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csharp.game.RythemDiscord;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        //TODO Resizable maken
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 900;
        config.width = 1600;
        config.resizable = false;
        new LwjglApplication(new RythemDiscord(), config);
    }
}
