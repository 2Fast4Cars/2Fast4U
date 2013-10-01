package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings extends BaseMenuClass implements Screen {
	
	private Table tableBack;
	private TextButton buttonExit, buttonBack;


	public Settings(FastCars fastCar) {
		super(fastCar);
		
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
		
		// CREATING The BackButton Table
		tableBack = new Table(skin);
		tableBack.setVisible(true);
		tableBack.setBounds(0, 550, 200, 50);
		
		
		// CREATING BUTTONS
		
		
		// BACK -------------------------------------------------
		buttonBack = new TextButton("BACK", textButtonStyle);
		buttonBack.addListener(new ClickListener() {
		
			@Override
			public void clicked(InputEvent event, float x, float y) {
						

			}
		});
		buttonBack.pad(15);
		
		
		// EXIT-------------------------------------------------
		buttonExit = new TextButton("EXIT", textButtonStyle);
		buttonExit.addListener(new ClickListener() {
		
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();				

			}
		});
		
		buttonExit.pad(15);
		
		
		
		// putting stuff together
		
		tableBack.add(buttonBack).size(50, 50);
		
		table.add(buttonExit).size(50, 50);
		
		table.debug();
		stage.addActor(table);
		stage.addActor(tableBack);
		
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
