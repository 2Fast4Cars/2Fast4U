package com.me.fastcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
	protected Texture texture, textureA;
	protected TextureAtlas atlas; 
	protected Sprite sprite, spriteSUB, spriteSUBA;
	protected Stage stage;
	protected Table tableMenu, tableSUB;
	protected Skin skin;
	protected Music music;
	protected FastCars fastCar;
	protected TextButtonStyle textButtonStyle, textButtonStyleB;
	protected TextButton buttonBack;
	protected LabelStyle headingStyle;
	protected Label heading;
	protected boolean subMenu, subMenuA = false;
	protected boolean musicPlay = true;
	
	// The Constructor with a parameter fastCar. 
	public BaseMenuClass(FastCars fastCar){
		this.fastCar = fastCar;
	}
	
	
	//Create and show the backgroundImage // Buttons
	@Override
	public void show() {
		
		// Setting up the stage, input, atlas, skin and a table. 
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);	
		
		atlas = new TextureAtlas("ui/interface.pack");
		
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
	
		
		// Creating the table for the Menu bar. 
		
		tableMenu = new Table(skin);
		tableMenu.setVisible(true);
		tableMenu.setBounds(40, 135, 260, 350);
		
		
		// CREATING the table for SUBHeading
		
		tableSUB = new Table(skin);
		tableSUB.setVisible(true);
		tableSUB.setBounds(352, 135, 536, 350);	
		
		
		// BackgroundImage
				float w = Gdx.graphics.getWidth();
				float h = Gdx.graphics.getHeight();
				
				camera = new OrthographicCamera(w, h);
				camera.setToOrtho(false, 1000, 600);
				batch = new SpriteBatch();
				
				texture = new Texture(Gdx.files.internal("img/MenueBackground1.png"));
				texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
				sprite = new Sprite(texture);
				sprite.setSize(w, h);
				
				texture = new Texture(Gdx.files.internal("img/SUBBackground.png"));
				texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
				textureA = new Texture(Gdx.files.internal("img/SUBBackgroundAbout.png"));
				textureA.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	
				spriteSUB = new Sprite(texture);
				spriteSUB.setBounds(349, 132, 540, 354);
				
				spriteSUBA = new Sprite(textureA);
				spriteSUBA.setBounds(349, 132, 540, 354);
				
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		skin.dispose();
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
		
		if(subMenuA)
			spriteSUBA.draw(batch);	
		
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
//		Table.drawDebug(stage);
		

		
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
		
	
		if(musicPlay){
		music = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/getLOW.mp3"));			
		
		music.play();
		music.setLooping(true);
			}
		}


}
