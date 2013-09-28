package com.me.fastcars;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture carTexture;
	private Sprite carSprite;
	private Sprite car2Sprite;
	private World world;
	private Box2DDebugRenderer  debugRenderer;

	private int screenWidth;
	private int screenHeight;	
	private float worldWidth;
	private float worldHeight;
	private static int PIXELS_PER_METER=10;      //how many pixels in a meter
	
	public static final int STEER_NONE=0;
	public static final int STEER_RIGHT=1;
	public static final int STEER_LEFT=2;

	public static final int ACC_NONE=0;
	public static final int ACC_ACCELERATE=1;
	public static final int ACC_BRAKE=2;
	
	private static final float CAR_WIDTH = 1;
	private static final float CAR_LENGTH = 2;
	
	private static final float MAXSPEED = 70;
	private static final float POWER = 60;
	private static int STEERANGLE = 35;
	private static float TRACK_WIDTH = 100;
	
	private TrackLoader track; 
	private String trackName;
	private Rectangle finishLine;
	private Rectangle checkPoint1;
	private Sprite mapSprite;

	
	private BitmapFont font;
	private long startTime;
	
	private boolean twoPlayers = true;
	private ShapeRenderer sr = new ShapeRenderer();
	
	public Car car;
	public Car car2;	


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//		camera.combined.scale(PIXELS_PER_METER, PIXELS_PER_METER, PIXELS_PER_METER);
		camera.update();

		CharSequence str = this.formatTime((System.currentTimeMillis() - startTime));
		
//		Keys for player one.
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			car.accelerate = GameScreen.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			car.accelerate = GameScreen.ACC_BRAKE;
		else
			car.accelerate = GameScreen.ACC_NONE;
		
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			car.steer = GameScreen.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			car.steer = GameScreen.STEER_RIGHT;
		else
			car.steer = GameScreen.STEER_NONE;

		car.update(Gdx.app.getGraphics().getDeltaTime());

		
//		Keys and update for 2nd car.
		if(twoPlayers)
		{
			if (Gdx.input.isKeyPressed(Input.Keys.W))
				car2.accelerate = GameScreen.ACC_ACCELERATE;
			else if (Gdx.input.isKeyPressed(Input.Keys.S))
				car2.accelerate = GameScreen.ACC_BRAKE;
			else
				car2.accelerate = GameScreen.ACC_NONE;
			
			if (Gdx.input.isKeyPressed(Input.Keys.A))
				car2.steer = GameScreen.STEER_LEFT;
			else if (Gdx.input.isKeyPressed(Input.Keys.D))
				car2.steer = GameScreen.STEER_RIGHT;
			else
				car2.steer = GameScreen.STEER_NONE;

			car2.update(Gdx.app.getGraphics().getDeltaTime());
			
			Vector2 car2Position = car2.getPosition();

			
			car2Sprite.setPosition((car2Position.x*PIXELS_PER_METER) - carSprite.getWidth()/2, (car2Position.y*PIXELS_PER_METER) - car2Sprite.getHeight()/2);
			car2Sprite.setOrigin(car2Sprite.getWidth()/2, car2Sprite.getHeight()/2);
			car2Sprite.setRotation(car2.getAngle() * MathUtils.radiansToDegrees -180);

			
		}
		Vector2 carPosition = car.getPosition();
		Vector2 carModelorigin = car.getBodyOrigin();
		
		
		
		
		carSprite.setPosition((carPosition.x*PIXELS_PER_METER) - carSprite.getWidth()/2, (carPosition.y*PIXELS_PER_METER) - carSprite.getHeight()/2);
		System.out.println("Tjena! Här kommer angle: " + car.getAngle() * MathUtils.radiansToDegrees + " Plus position: " + carSprite.getX() + " " + carSprite.getY());
		carSprite.setOrigin(carSprite.getWidth()/2, carSprite.getHeight()/2);
		carSprite.setRotation(car.getAngle() * MathUtils.radiansToDegrees -180);
	
		
		batch.begin();
		mapSprite.draw(batch);
		carSprite.draw(batch);
		if(twoPlayers)
			car2Sprite.draw(batch);
		font.setColor(0,0,0,1);
		font.draw(batch,str, 900, 580);
		batch.end();

		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
		
		world.clearForces();
		
		camera.setToOrtho(false, 1000, 600);
		camera.combined.setToOrtho2D(0, 0, screenWidth, screenHeight);
//		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,PIXELS_PER_METER,PIXELS_PER_METER));

		sr.begin(ShapeType.FilledRectangle);
		sr.setColor(Color.RED);
		sr.filledRect(315, 248, 116, 2);
		sr.end();
		
		System.out.println(car.getLap());
		
		if(finishLine.contains(carSprite.getX(), carSprite.getY()))
		{
			if(car.getPassedCheckPoints()){
				car.setLap(car.getLap()+1);	
				car.resetCheckPoints();
		}
		}
			
		if(checkPoint1.contains(carSprite.getX(), carSprite.getY()))
		{
			car.setPassedCheckPoint(true, 0);
		}
			

		}
		
		


	private void createSprites() {
		
		carTexture = new Texture(Gdx.files.internal("data/gfx/CarRed.png"));
		carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		carSprite = new Sprite(carTexture);
		carSprite.setSize(PIXELS_PER_METER*CAR_WIDTH, PIXELS_PER_METER*CAR_WIDTH  * carSprite.getHeight()/ carSprite.getWidth());
		
		if(twoPlayers)
		{
			carTexture = new Texture(Gdx.files.internal("data/gfx/CarGreen.png"));
			carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			car2Sprite = new Sprite(carTexture);
			car2Sprite.setSize(PIXELS_PER_METER*CAR_WIDTH, PIXELS_PER_METER*CAR_WIDTH  * car2Sprite.getHeight()/ car2Sprite.getWidth());
			
		}		
		mapSprite = track.createSprite();
		mapSprite.setSize(PIXELS_PER_METER*TRACK_WIDTH, PIXELS_PER_METER*TRACK_WIDTH  * mapSprite.getHeight()/ mapSprite.getWidth());
		mapSprite.setPosition(0, 0);
		
		finishLine = new Rectangle(35, 348, 80, 5);
		checkPoint1 = new Rectangle(315, 248, 116, 2);
	}


	@Override
	public void dispose() {
		batch.dispose();

		carTexture.dispose();
		world.dispose();
	}

	private String formatTime(long time){
//		long seconds = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
		double rest = time%1000;
		double seconds = (double)time/1000;
		double minutes = seconds/60;
		

//		long tenthOfSecond = TimeUnit.MICROSECONDS.convert(time - seconds, TimeUnit.NANOSECONDS);
		
		return String.format("%02d:%02d:%d",(int)minutes, (int)seconds, (int)rest);

	}
	
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {


		
		this.trackName = "bana1";
		
		font = new BitmapFont();
		
		startTime = System.currentTimeMillis();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;

		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;
		
		world = new World(new Vector2(0.0f, 0.0f), true);	

		
		this.track = new TrackLoader(trackName , world, TRACK_WIDTH);
		
		createSprites();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		batch = new SpriteBatch();
		
		
		//Creates first car
	    this.car = new Car(world, CAR_WIDTH, CAR_LENGTH,
	    		new Vector2(8, 35), (float) Math.PI, POWER, STEERANGLE, MAXSPEED);
	    
	    //Creates a 2nd car if two players are available.
	    if(twoPlayers)
	    {this.car2 = new Car(world, CAR_WIDTH, CAR_LENGTH,
	    		new Vector2(11, 35), (float) Math.PI, POWER, STEERANGLE, MAXSPEED);}
	 
	    debugRenderer = new Box2DDebugRenderer();		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
