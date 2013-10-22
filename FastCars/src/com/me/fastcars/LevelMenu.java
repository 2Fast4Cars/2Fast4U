package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelMenu extends MainMenu implements Screen {

	private TextField nameField1, nameField2;
	private List list;
	private ScrollPane scrollPane;
	private TextButton buttonPlay;
	
	
	public LevelMenu(FastCars fastCar, Music music) {
		super(fastCar, false);
		super.music = music;
	}
	

  public LevelMenu(FastCars fastCar) {
    super(fastCar, true);
  }

	@Override
	public void render(float delta) {
		
		
		super.render(delta);


    //  Loads the map texture.
	    Texture mapTexture = new Texture(Gdx.files.internal("tracks/" + list.getSelection().toLowerCase() + ".png"));
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
			list = new List(new String[] {"MarioCircuit1", "MarioCircuit2", "MarioCircuit3", "TheGreenMess"}, skin);

		// Creating a scrollPane and implements the list 
			scrollPane = new ScrollPane(list, skin);
		

		
		
		// Creating input fields for the playernames 
			
			// PLayer One
			nameField1 = new TextField("Player One", skin);
			nameField1.setBounds(650, 350, 200, 40);
			nameField1.setVisible(true);
			
			stage.addActor(nameField1);
		
			// PLayer Two
			nameField2 = new TextField("Player Two", skin);
			nameField2.setBounds(650, 280, 200, 40);
			nameField2.setVisible(true);
			
			stage.addActor(nameField2);
			
		// PLAY-------------------------------------------------
			buttonPlay = new TextButton("PLAY", skin);
			buttonPlay.addListener(new ClickListener() {
	
			@Override
			public void clicked(InputEvent event, float x, float y) {
					music.stop();
						fastCar.setScene(new GameScreen(fastCar, list.getSelection().toLowerCase(), 
								nameField1.getText(), nameField2.getText()));
						dispose();
										}
			});
			buttonPlay.pad(15);
		
		
		// Putting stuffs together
			tableSUB.add("Level Menu").center();
			tableSUB.add().width(tableSUB.getWidth() / 2 );
			tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
			
			
			tableSUB.add(scrollPane).uniformX().size(220, 160).expandY().bottom().padBottom(20);
			
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
