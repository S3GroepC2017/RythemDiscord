package com.csharp.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csharp.game.RythemDiscord;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height  = 900;
		config.width = 1600;
		config.addIcon("icon/32x.png", Files.FileType.Internal);
		config.addIcon("icon/64x.png", Files.FileType.Internal);
		config.addIcon("icon/128x.png", Files.FileType.Internal);
		config.resizable = true;
		new LwjglApplication(new RythemDiscord(), config);
	}
}