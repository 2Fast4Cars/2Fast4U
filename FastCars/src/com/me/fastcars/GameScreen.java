package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	private Box2DDebugRenderer debugRenderer;

	private int screenWidth;
	private int screenHeight;
	private float worldWidth;
	private float worldHeight;
	private static int PIXELS_PER_METER = 20; // how many pixels in a meter

	public static final int STEER_NONE = 0;
	public static final int STEER_RIGHT = 1;
	public static final int STEER_LEFT = 2;

	public static final int ACC_NONE = 0;
	public static final int ACC_ACCELERATE = 1;
	public static final int ACC_BRAKE = 2;

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

	private BitmapFont timerFont;
	private BitmapFont lapFont;
	private long startTime;

	private boolean twoPlayers = true;
	private Music carSound;
	private Music bgMusic;

	public Car car;
	public Car car2;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		
		renderFirstCar();
		renderSecondCar();

		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
		world.clearForces();

		updateCarLaps();
		drawTimerInfo();
		


	}

	private void renderFirstCar() {
		
		CharSequence currentLapCar1 = String.format("Lap: %d/3",
				car.getLap());

		Gdx.gl.glViewport(0, 0, 500, 600);
		Vector2 carPosition1 = car.getPosition();

		camera.update();
		camera.setToOrtho(false, 500, 600);
		camera.update();
		camera.position
				.set(((carPosition1.x * PIXELS_PER_METER) - carSprite
						.getWidth() / 2),
						((carPosition1.y * PIXELS_PER_METER) - carSprite
								.getHeight() / 2), 0);
		camera.update();

		
		batch.setProjectionMatrix(camera.combined);

		updateCar1();

		batch.begin();
		mapSprite.draw(batch);
		carSprite.draw(batch);
		car2Sprite.draw(batch);
		lapFont.setColor(1, 0, 0, 1);
		lapFont.draw(batch, currentLapCar1, camera.position.x-150, camera.position.y+250);
		batch.end();


	}

	private void renderSecondCar() {
		Gdx.gl.glViewport(500, 0, 500, 600);

		CharSequence currentLapCar1 = String.format("Lap: %d/3",car2.getLap());
		
		Vector2 carPosition2 = car2.getPosition();

		camera.update();
		camera.setToOrtho(false, 500, 600);
		camera.update();
		camera.position
				.set(((carPosition2.x * PIXELS_PER_METER) - carSprite
						.getWidth() / 2),
						((carPosition2.y * PIXELS_PER_METER) - carSprite
								.getHeight() / 2), 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		
		updateCar2();

		batch.begin();
		mapSprite.draw(batch);
		carSprite.draw(batch);
		car2Sprite.draw(batch);
		lapFont.setColor(0, 1, 0, 1);
		lapFont.draw(batch, currentLapCar1, camera.position.x-150, camera.position.y+250);

		batch.end();

	}

	private void drawTimerInfo(){

		Gdx.gl.glViewport(0, 0, 1000, 600);

		Vector2 carPosition = car.getPosition();

		camera.update();
		camera.setToOrtho(false, 1000, 600);
		camera.update();
		camera.position
				.set(((carPosition.x * PIXELS_PER_METER) - carSprite
						.getWidth() / 2),
						((carPosition.y * PIXELS_PER_METER) - carSprite
								.getHeight() / 2), 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);


		
		CharSequence timeString = this
				.formatTime((System.currentTimeMillis() - startTime));

		batch.begin();
		timerFont.setColor(1, 0, 0, 1);
		timerFont.draw(batch, timeString,camera.position.x-25, camera.position.y+290);
		batch.end();
	}
	
	private void drawInfoToScreen() {


		//		CharSequence currentLapCar2 = String.format("Player 2: %d/3",
//				car2.getLap());



//		lapFont.draw(batch, currentLapCar2, 460, 540);

	}

	// Updates the keys as well as position/velocity etc for cars.
	private void updateCar1() {

		Vector2 carPosition = car.getPosition();

		// Keys for player one.
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			car.accelerate = GameScreen.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(Input.Keys.S))
			car.accelerate = GameScreen.ACC_BRAKE;
		else
			car.accelerate = GameScreen.ACC_NONE;

		if (Gdx.input.isKeyPressed(Input.Keys.A))
			car.steer = GameScreen.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(Input.Keys.D))
			car.steer = GameScreen.STEER_RIGHT;
		else
			car.steer = GameScreen.STEER_NONE;

		car.update(Gdx.app.getGraphics().getDeltaTime());

		carSprite.setPosition(
				(carPosition.x * PIXELS_PER_METER) - carSprite.getWidth() / 2,
				(carPosition.y * PIXELS_PER_METER) - carSprite.getHeight() / 2);
		carSprite
				.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight() / 2);
		carSprite
				.setRotation(car.getAngle() * MathUtils.radiansToDegrees - 180);
		
		

	}

	private void updateCar2() {

		// Keys and update for 2nd car.
		if (twoPlayers) {
			if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
				car2.accelerate = GameScreen.ACC_ACCELERATE;
				// if (!carSound.isPlaying())
				// carSound.play();
			} else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
				car2.accelerate = GameScreen.ACC_BRAKE;
			} else {
				car2.accelerate = GameScreen.ACC_NONE;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
				car2.steer = GameScreen.STEER_LEFT;
			else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
				car2.steer = GameScreen.STEER_RIGHT;
			else
				car2.steer = GameScreen.STEER_NONE;

			car2.update(Gdx.app.getGraphics().getDeltaTime());

			Vector2 car2Position = car2.getPosition();

			car2Sprite.setPosition(
					(car2Position.x * PIXELS_PER_METER) - carSprite.getWidth()
							/ 2,
					(car2Position.y * PIXELS_PER_METER)
							- car2Sprite.getHeight() / 2);
			car2Sprite.setOrigin(car2Sprite.getWidth() / 2,
					car2Sprite.getHeight() / 2);
			car2Sprite.setRotation(car2.getAngle() * MathUtils.radiansToDegrees
					- 180);

		}

	}

	// Checks and updates the laps for the cars.
	private void updateCarLaps() {

		
		// Check if the first car have crossed the finish line.
		if (finishLine.contains(carSprite.getX(), carSprite.getY())) {
			if (car.getPassedCheckPoints()) {
				car.setLap(car.getLap() + 1);
				car.resetCheckPoints();

			}
		}

		// Check if the first car have crossed the first checkpoint..
		if (checkPoint1.contains(carSprite.getX(), carSprite.getY())) {
			car.setPassedCheckPoint(true, 0);
		}

		// Check if the 2nd car have crossed the finish line.
		if (finishLine.contains(car2Sprite.getX(), car2Sprite.getY())) {
			if (car2.getPassedCheckPoints()) {
				car2.setLap(car2.getLap() + 1);
				car2.resetCheckPoints();

			}
		}

		// Check if the 2nd car have crossed the first checkpoint..
		if (checkPoint1.contains(car2Sprite.getX(), car2Sprite.getY())) {
			car2.setPassedCheckPoint(true, 0);
		}

	}

	private void createSprites() {

		carTexture = new Texture(Gdx.files.internal("data/gfx/CarRed.png"));
		carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		carSprite = new Sprite(carTexture);
		carSprite.setSize(PIXELS_PER_METER * CAR_WIDTH, PIXELS_PER_METER
				* CAR_WIDTH * carSprite.getHeight() / carSprite.getWidth());

		if (twoPlayers) {
			carTexture = new Texture(
					Gdx.files.internal("data/gfx/CarGreen.png"));
			carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			car2Sprite = new Sprite(carTexture);
			car2Sprite.setSize(PIXELS_PER_METER * CAR_WIDTH,
					PIXELS_PER_METER * CAR_WIDTH * car2Sprite.getHeight()
							/ car2Sprite.getWidth());

		}
		mapSprite = track.createSprite();
		mapSprite.setSize(PIXELS_PER_METER * TRACK_WIDTH, PIXELS_PER_METER
				* TRACK_WIDTH * mapSprite.getHeight() / mapSprite.getWidth());
		mapSprite.setPosition(0, 0);

		
		finishLine = new Rectangle(15, 693, 320, 10);
		checkPoint1 = new Rectangle(600, 515, 320, 10);
	}

	@Override
	public void dispose() {
		batch.dispose();

		carTexture.dispose();
		world.dispose();
	}

	private String formatTime(long time) {
		// long seconds = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
		double rest = time % 1000 / 10;
		double seconds = (double) time / 1000;
		double minutes = seconds / 60;

		// long tenthOfSecond = TimeUnit.MICROSECONDS.convert(time - seconds,
		// TimeUnit.NANOSECONDS);

		return String.format("%02d:%02d:%02d", (int) minutes, (int) seconds,
				(int) rest);

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		this.sr = new ShapeRenderer();

		this.trackName = "bana1";
		//
		// carSound = Gdx.audio.newMusic(Gdx.files
		// .internal("data/gfx/CarSound.mp3"));

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/Storm.mp3"));

		bgMusic.play();
		bgMusic.setVolume(0.5f);

		timerFont = new BitmapFont();
		lapFont = new BitmapFont();

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

		this.track = new TrackLoader(trackName, world, TRACK_WIDTH);

		createSprites();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		batch = new SpriteBatch();

		// Creates first car
		this.car = new Car(world, CAR_WIDTH, CAR_LENGTH, new Vector2(8, 35),
				(float) Math.PI, POWER, STEERANGLE, MAXSPEED);

		// Creates a 2nd car if two players are available.
		if (twoPlayers) {
			this.car2 = new Car(world, CAR_WIDTH, CAR_LENGTH, new Vector2(11,
					35), (float) Math.PI, POWER, STEERANGLE, MAXSPEED);
		}

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
