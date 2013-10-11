package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelMenu extends MainMenu implements Screen {

	private List list;
	private ScrollPane scrollPane;
	private TextButton buttonPlay;
	
	private String track;
	
	public LevelMenu(FastCars fastCar) {
		super(fastCar, false);
	}

	@Override
	public void render(float delta) {
		super.render(delta);


    //Loads the maptexture, booya.
    Texture mapTexture = new Texture(Gdx.files.internal("data/gfx/" + list.getSelection().toLowerCase() + ".png"));
    mapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    Sprite map = new Sprite(mapTexture);
		map.setScale(0.15f, 0.15f);
		map.setPosition(-15, 100);
		
		batch.begin();
		map.draw(batch);
		batch.end();
		
		
		

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
	}

	@Override
	public void show() {
		super.show();
		
		// Contacts the "subMenu" in the superClass(BaseMenuClass) and set it to true. 
		super.subMenu = true; 
		
		
		// Creating a list for the tracks. 
		list = new List(new String[] {"Bana1", "Bana2","The 8-track", "Taggarå", "Mantorp Park"}, skin);
		
		// Setting the track to "bana1" as default.
		track = "bana1";

		
		
		// Creating a scrollPane and implements the list 
		scrollPane = new ScrollPane(list, skin);
		
		
		// Heading 
		heading = new Label("LevelMenu", skin);
				
		
		// PLAY-------------------------------------------------
		buttonPlay = new TextButton("PLAY", skin);
		buttonPlay.addListener(new ClickListener() {

		@Override
		public void clicked(InputEvent event, float x, float y) {
					fastCar.setScene(new GameScreen(fastCar, list.getSelection().toLowerCase()));
					dispose();
					

			}
		});
		buttonPlay.pad(15);
		
		
		// Putting stuffs together
		tableSUB.add("Level Menu").center();
		tableSUB.add().width(tableSUB.getWidth() / 2 );
		tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
		
		
		tableSUB.add(scrollPane).uniformX().size(200, 100).expandY();
		
		tableSUB.add(buttonPlay).uniformX().size(80, 50).bottom().right();
		
		
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
