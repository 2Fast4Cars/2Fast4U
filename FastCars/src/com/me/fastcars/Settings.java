package com.me.fastcars;

import com.badlogic.gdx.Screen;

public class Settings extends MainMenu implements Screen {


	public Settings(FastCars fastCar) {
		super(fastCar, false);
		
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}

	@Override
	public void show() {
		super.show();
	
		super.subMenu = true; 
		
	}

	@Override
	public void hide() {
		

	}

	@Override
	public void pause() {
		

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void dispose() {
		super.dispose();

	}

}
