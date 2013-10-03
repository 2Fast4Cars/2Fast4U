package com.me.fastcars;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FastCars v0.3";
		cfg.useGL20 = true;
		cfg.width = 1000;
		cfg.height = 600;
		
		new LwjglApplication(new FastCars(), cfg);
	}
}

