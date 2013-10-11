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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScene extends Game implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private TextureAtlas atlas; 
	private Sprite sprite;
	private Stage stage;
	private Table table;
	private TextButton buttonExit, buttonPlay, buttonSettings, buttonAbout;
	private Skin skin;
	private Music music;
	
	
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
		table.setBounds(0, 0, 50, 50);
		

		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.Up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.fontColor = Color.BLACK;
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
				
		music = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/getLOW.mp3"));			
			
		
		// creating buttons
			
			
				//PLAY
//				TextButton buttonPlay = new TextButton("PLAY", textButtonStyle);
//				buttonPlay.addListener(new ClickListener() {
//		
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//						 ((Game) Gdx.app).getApplicationListener()).setScreen(new LevelMenu());
//							
//					}
//				});
//				buttonPlay.pad(15);
//		
//				
//				//SETTINGS
//				TextButton buttonSettings = new TextButton("SETTINGS", textButtonStyle);
//				buttonSettings.addListener(new ClickListener() {
//		
//					@Override
//					public void clicked(InputEvent event, float x, float y) {
//					
//								((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
//						
//					}
//				});
//				buttonSettings.pad(15);
		
		
				//ABOUT
//				TextButton buttonAbout = new TextButton("ABOUT", textButtonStyle);
//				buttonAbout.addListener(new ClickListener() {
//		
//					@Override
//					public void clicked(InputEvent event, float x, float y) {
//					
//								((Game) Gdx.app.getApplicationListener()).setScreen(new About());
//						
//					}
//				});
//				buttonAbout.pad(15);

				
				
				//EXIT
				buttonExit = new TextButton("EXIT", textButtonStyle);
				buttonExit.addListener(new ClickListener() {
				
					@Override
					public void clicked(InputEvent event, float x, float y) {
						Gdx.app.exit();
																		
					}
				});
				
				buttonExit.pad(15);
		
				
				// putting stuff together
				table.add(buttonExit).size(150, 50);
				table.debug();
				stage.addActor(table);
				
				music.play();
				music.setLooping(true);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		music.dispose();
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




}
