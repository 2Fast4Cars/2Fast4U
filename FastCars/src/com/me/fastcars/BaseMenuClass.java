package com.me.fastcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class BaseMenuClass extends Game implements Screen {
	
	
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected Texture texture;
	protected TextureAtlas atlas, atlasB; 
	protected Sprite sprite, spriteSUB;
	protected Stage stage;
	protected Table tableMenu, tableSUB;
	protected Skin skin;
	protected Music music;
	protected FastCars fastCar;
	protected TextButtonStyle textButtonStyle, textButtonStyleB;
	protected TextButton buttonBack;
	protected LabelStyle headingStyle;
	protected Label heading;
	protected boolean subMenu = false;
	
	// The Constructor with a parameter fastCar. 
	public BaseMenuClass(FastCars fastCar){
		this.fastCar = fastCar;
	}
	
	public BaseMenuClass(){}
	
	//Create and show the backgroundImage // Buttons
	@Override
	public void show() {
		
		// Setting up the stage, input, atlas, skin and a table. 
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);	
		
		atlas = new TextureAtlas("ui/interface.pack");
				
		skin = new Skin(atlas);
		skin.add("default", new BitmapFont());
		
		
		// Creating the table for the Menu bar. 
		
		tableMenu = new Table(skin);
		tableMenu.setVisible(true);
		tableMenu.setBounds(40, 135, 260, 350);
		
		
		// CREATING the table for SUBHeading
		
		tableSUB = new Table(skin);
		tableSUB.setVisible(true);
		tableSUB.setBounds(349, 135, 540, 350);	
		
			
		headingStyle = new LabelStyle(skin.getFont("default"), Color.WHITE);	

		// Setting up for the textButtonStyle
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.Up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
	
		
		// BackgroundImage
				float w = Gdx.graphics.getWidth();
				float h = Gdx.graphics.getHeight();
				
				camera = new OrthographicCamera(w, h);
				camera.setToOrtho(false, 1000, 600);
				batch = new SpriteBatch();
				
				texture = new Texture(Gdx.files.internal("data/gfx/MenueBackground1.png"));
				texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
				sprite = new Sprite(texture);
				sprite.setSize(w, h);
				
				texture = new Texture(Gdx.files.internal("data/gfx/SUBBackground.png"));
				texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	
				spriteSUB = new Sprite(texture);
				spriteSUB.setBounds(349, 132, 540, 354);
				
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		
	}
	
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		
		sprite.draw(batch);
		
		if(subMenu)
			spriteSUB.draw(batch);
		
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		

		
	}
	
	@Override
	public void resize(int width, int height) {
//		stage.setViewport(width, height, false);
//		table.invalidateHierarchy();
//		table.setSize(width, height);
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
	public void create() {
	
		
	}
	
	
	// CREATING and implements the background music.
	
	public void startMusic(){
			
		music = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/getLOW.mp3"));			
		
		music.play();
		music.setLooping(true);
		
		}


}
