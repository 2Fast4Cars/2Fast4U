package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class About extends BaseMenuClass implements Screen {

	private TextButton buttonExit;

	public About(FastCars fastCar) {
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
		
		
		// CREATING BUTTONS
		
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
				
		table.add(buttonExit).size(200, 50);
		
		table.debug();
		stage.addActor(table);
		
		
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
