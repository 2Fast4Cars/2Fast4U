package com.me.fastcars;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;


public class Settings extends MainMenu implements Screen {


	public Settings(FastCars fastCar, Music music) {
		super(fastCar, false);
		super.music = music;
		
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
		
		
		// Contacts the "subMenu" in the superClass(BaseMenuClass) and set it to true. 
			super.subMenu = true;  

		
		// Putting stuffs together		
			tableSUB.add("Settings").center();
			tableSUB.add().width(tableSUB.getWidth() / 2 );
			tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
			
			
			tableSUB.add().uniformX().expandY();
			tableSUB.add().uniformX();
			
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
