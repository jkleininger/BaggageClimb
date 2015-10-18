package net.qrab.watchit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.qrab.watchit.WatchitGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width  = 800;
		config.height = 480;

		new LwjglApplication(new WatchitGame(), config);
	}
}
