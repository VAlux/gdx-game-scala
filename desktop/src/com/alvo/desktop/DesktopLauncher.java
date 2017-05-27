package com.alvo.desktop;

import com.alvo.MainstreamHater;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
		config.useGL30 = false;
		config.width = 480;
		config.height = 640;
		new LwjglApplication(new MainstreamHater(config.width, config.height, true), config);
	}
}
