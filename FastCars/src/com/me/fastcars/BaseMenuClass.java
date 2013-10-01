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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class BaseMenuClass extends Game implements Screen {
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected Texture texture;
	protected TextureAtlas atlas; 
	protected Sprite sprite;
	protected Stage stage;
	protected Table table;
	protected Skin skin;
	protected Music music;
	protected FastCars fastCar;
	protected TextButtonStyle textButtonStyle;

	public BaseMenuClass(FastCars fastCar){
		this.fastCar = fastCar;
	}
	
	//Create and show the backgroundImage // Buttons
	@Override
	public void show() {
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);	
		
		atlas = new TextureAtlas("ui/button.pack");
				
		skin = new Skin(atlas);
		skin.add("default", new BitmapFont());
		
		
		table = new Table(skin);
		table.setVisible(true);
		table.setBounds(40, 135, 260, 350);
		
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.Up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
	
		//BackgroundImage
				float w = Gdx.graphics.getWidth();
				float h = Gdx.graphics.getHeight();
				
				camera = new OrthographicCamera(w, h);
				camera.setToOrtho(false, 1000, 600);
				batch = new SpriteBatch();
				
				texture = new Texture(Gdx.files.internal("ui/background.png"));
				texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
				sprite = new Sprite(texture);
				sprite.setSize(w, h);
				
			
		// Set Background Music.
				

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
		// TODO Auto-generated method stub
		
	}

	
	public void startMusic(){
			
		music = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/getLOW.mp3"));			
		
		music.play();
		music.setLooping(true);
		
		}
		




}
