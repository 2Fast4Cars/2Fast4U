package com.me.fastcars;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;


public class Settings extends MainMenu implements Screen {
	
	
	protected CheckBox musicCheckBoxMenu, musicCheckBoxGame ;
	protected Music music;
	
	
	public Settings(FastCars fastCar, Music music) {
		super(fastCar, false);
		this.music = music;
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
			
		// Creating Music CheckBoxes	
			
			// Menu Music
			musicCheckBoxMenu = new CheckBox("Music In Menu", skin);
			musicCheckBoxMenu.getCells().get(0).size(30, 30);
			musicCheckBoxMenu.setChecked(true);
			
			musicCheckBoxMenu.addListener( new InputListener() {
				  public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					  
				  }
				  public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				    if(musicCheckBoxMenu.isChecked()){
				    	music.pause();	
				    }
				    else 
				    	music.play();
				    
				    
				    return true;
				  };
				});
			
			
			
			// Game Music	
			musicCheckBoxGame = new CheckBox("Music In Game", skin);
			musicCheckBoxGame.getCells().get(0).size(30, 30);
			musicCheckBoxGame.setChecked(true);
			
			musicCheckBoxGame.addListener( new InputListener() {
				  public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					  
				  }
				  public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				    if(musicCheckBoxGame.isChecked()){
				    	fastCar.gameMusic = false;	
				    }
				    else 
				    	fastCar.gameMusic = true;
				    
				    
				    return true;
				  };
				});
			
			
			
		// Putting stuffs together		
			tableSUB.add("Settings").center();
			tableSUB.add().width(tableSUB.getWidth() / 2 );
			tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
			
			
			tableSUB.add(musicCheckBoxMenu).uniformX().expandY();
			tableSUB.add(musicCheckBoxGame).uniformX().expandY();
			
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
