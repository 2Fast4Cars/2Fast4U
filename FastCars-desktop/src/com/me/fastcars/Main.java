package com.me.fastcars;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FastCars";
		cfg.useGL20 = false;
		cfg.width = 1000;
		cfg.height = 600;
		
		new LwjglApplication(new FastCars(), cfg);
	}
}
