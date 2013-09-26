package com.me.fastcars;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class FastCars implements ApplicationListener {
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
	private static int PIXELS_PER_METER=16;      //how many pixels in a meter
	
	public static final int STEER_NONE=0;
	public static final int STEER_RIGHT=1;
	public static final int STEER_LEFT=2;

	public static final int ACC_NONE=0;
	public static final int ACC_ACCELERATE=1;
	public static final int ACC_BRAKE=2;
	
	private static final float CAR_WIDTH = 2;
	private static final float CAR_LENGTH = 4;
	
	private static final float MAXSPEED = 70;
	private static final float POWER = 60;
	private static int STEERANGLE = 35;


	public Car car;
	
	
	@Override
	public void create() {		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;

		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		worldWidth = screenWidth / PIXELS_PER_METER;
		worldHeight = screenHeight / PIXELS_PER_METER;
		
		createSprites();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		batch = new SpriteBatch();
		
		world = new World(new Vector2(0.0f, 0.0f), true);	
		

	    this.car = new Car(world, CAR_WIDTH, CAR_LENGTH,
	    		new Vector2(0, 0), (float) Math.PI, POWER, STEERANGLE, MAXSPEED);
	    
		
	    debugRenderer = new Box2DDebugRenderer();
	    
	}


	private void createSprites() {
		
		carTexture = new Texture(Gdx.files.internal("data/gfx/CarRed.png"));
		carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		carSprite = new Sprite(carTexture);
		carSprite.setSize(PIXELS_PER_METER*CAR_WIDTH, PIXELS_PER_METER*CAR_WIDTH  * carSprite.getHeight()/ carSprite.getWidth());

		car2Sprite = new Sprite(carTexture);
		car2Sprite.setSize(200, 400);

		
	}


	@Override
	public void dispose() {
		batch.dispose();

		carTexture.dispose();
		world.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//		camera.combined.scale(PIXELS_PER_METER, PIXELS_PER_METER, PIXELS_PER_METER);
		camera.update();

		
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			car.accelerate = FastCars.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			car.accelerate = FastCars.ACC_BRAKE;
		else
			car.accelerate = FastCars.ACC_NONE;
		
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			car.steer = FastCars.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			car.steer = FastCars.STEER_RIGHT;
		else
			car.steer = FastCars.STEER_NONE;
		
		car.update(Gdx.app.getGraphics().getDeltaTime());
	
		Vector2 carPosition = car.getPosition();
		Vector2 carModelorigin = car.getBodyOrigin();
		
		
		carSprite.setPosition((carPosition.x*PIXELS_PER_METER + screenWidth/2) - carSprite.getWidth()/2, (carPosition.y*PIXELS_PER_METER + screenHeight/2) - carSprite.getHeight()/2);
		System.out.println("Tjena! Här kommer angle: " + car.getAngle() * MathUtils.radiansToDegrees + " Plus position: " + carSprite.getX() + " " + carSprite.getY());
		carSprite.setOrigin(carSprite.getWidth()/2, carSprite.getHeight()/2);
		carSprite.setRotation(car.getAngle() * MathUtils.radiansToDegrees -180);
	
		batch.begin();
		carSprite.draw(batch);
		batch.end();

		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
		
		world.clearForces();
		
		camera.setToOrtho(false, 1000, 600);
		camera.combined.setToOrtho2D(0, 0, screenWidth, screenHeight);
//		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,PIXELS_PER_METER,PIXELS_PER_METER));

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
