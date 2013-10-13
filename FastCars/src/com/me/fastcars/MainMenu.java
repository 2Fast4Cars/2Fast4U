package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu extends BaseMenuClass implements Screen {

	private TextButton buttonExit, 
					   buttonLevelMenu, 
					   buttonSettings, 
					   buttonAbout, 
					   buttonHighScore;
	
	public MainMenu(FastCars fastCar, boolean playMusic) {
		super(fastCar);
		if(playMusic)
			startMusic();
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
		

		// CREATING BUTTONS-------------------------------------
		
		// PLAY-------------------------------------------------
		buttonLevelMenu = new TextButton("LEVEL MENU", skin);
		buttonLevelMenu.addListener(new ClickListener() {

		@Override
		public void clicked(InputEvent event, float x, float y) {
					fastCar.setScene(new LevelMenu(fastCar, music));
					dispose();
					
			}
		});
		buttonLevelMenu.pad(15);
		
		
		// HIGHSCORE-------------------------------------------------
		buttonHighScore = new TextButton("HIGHSCORE", skin);
		buttonHighScore.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
			
					fastCar.setScreen(new HighScore(fastCar));
					dispose();
			}
		});
		buttonHighScore.pad(15);

		
		// SETTINGS-------------------------------------------------
		buttonSettings = new TextButton("SETTINGS", skin);
		buttonSettings.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
			
					fastCar.setScreen(new Settings(fastCar));
					dispose();
			}
		});
		buttonSettings.pad(15);


		// ABOUT-------------------------------------------------
		buttonAbout = new TextButton("ABOUT", skin);
		buttonAbout.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
					fastCar.setScreen(new About(fastCar));
					dispose();
						
						
			}
		});
		buttonAbout.pad(15);

		
		
		// EXIT-------------------------------------------------
		buttonExit = new TextButton("EXIT", skin);
		buttonExit.addListener(new ClickListener() {
		
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
																	
			}
		});
		
		buttonExit.pad(15);

		
		
		// putting stuff together
		tableMenu.add("Menu");
		tableMenu.row();
		tableMenu.add(buttonLevelMenu).size(200, 40);
		tableMenu.getCell(buttonLevelMenu).spaceBottom(15);
		tableMenu.row();
		tableMenu.add(buttonHighScore).size(200, 40);
		tableMenu.getCell(buttonHighScore).spaceBottom(15);
		tableMenu.row();
		tableMenu.add(buttonSettings).size(200, 40);
		tableMenu.getCell(buttonSettings).spaceBottom(15);
		tableMenu.row();
		tableMenu.add(buttonAbout).size(200, 40);
		tableMenu.getCell(buttonAbout).spaceBottom(15);
		tableMenu.row();
		tableMenu.add(buttonExit).size(200, 40);
		
		
		tableMenu.debug();
		tableSUB.debug();
		stage.addActor(tableMenu);
		stage.addActor(tableSUB);
		
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
