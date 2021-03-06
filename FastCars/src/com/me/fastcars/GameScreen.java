/**
 * @author Mattias Didriksson & Anders Blomkvist.
 */
package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameScreen implements Screen {

  // Declaration of all constants and variables.

  private static int PIXELS_PER_METER = 20; // how many pixels in a meter

  public static final int STEER_NONE = 0;
  public static final int STEER_RIGHT = 1;
  public static final int STEER_LEFT = 2;

  public static final int ACC_NONE = 0;
  public static final int ACC_ACCELERATE = 1;
  public static final int ACC_BRAKE = 2;

  private static final float CAR_WIDTH = 1;
  private static final float CAR_LENGTH = 2;

  private static final float MAXSPEED = 80;
  private static final float POWER = 20;
  private static float TRACK_WIDTH = 100;
  private static int STEERANGLE = 35;

  private OrthographicCamera camera;
  private SpriteBatch batch;
  private Texture carTexture;
  private Sprite carSprite;
  private Sprite car2Sprite;
  private World world;

  private int screenWidth;
  private int screenHeight;

  private TrackLoader track;
  private String trackName;
  private Rectangle finishLine;
  private Rectangle checkPoint1;
  private Sprite mapSprite;
  private Sprite menuSprite;

  private Music bgMusic;

  private BitmapFont timerFont;
  private float timerFontWidth;
  private BitmapFont lapFontRed;
  private BitmapFont lapFontGreen;
  private long startTime;
  private long stopTime;
  private long timeLapsed = 0;
  private long timePaused = 0;

  private String nameCar1;
  private String nameCar2;

  private boolean twoPlayers = true;
  private boolean finishedCar1;
  private boolean finishedCar2;
  private boolean raceStarted = false;
  private boolean gamePaused = false;

  // 0 if no one won, 1 if player 1 and 2 if player 2 etc.
  private short whoWon = 0;

  private long raceTimeCar1;
  private long raceTimeCar2;

  private FastCars fastCars;
  public Car car;
  public Car car2;

  /**
   * 
   * @param fastCar
   *          - The game object. Required in order to know the settings.
   * @param track
   *          - The track started.
   * @param name1
   *          - The name of the first player.
   * @param name2
   *          - The name of the 2nd player.
   */
  public GameScreen(FastCars fastCar, String track, String name1, String name2) {
    this.fastCars = fastCar;
    this.trackName = track;
    this.nameCar1 = name1;
    this.nameCar2 = name2;
  }

  // The main loop of the program. Thill will handle the rendering and making
  // sure that the location updates.
  // The render method is called over and over again.
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    // Simulates the physical world.
    if(!gamePaused)
      world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
    world.clearForces();

    // Renders the first and second car.
    renderFirstCar();
    renderSecondCar();

    // Updates and renders the car laps as well as the timer.
    updateCarLaps();
    drawTimerInfo();
    if (finishedCar1 && finishedCar2)
      renderRaceCompleteMenu();

    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
      if (raceStarted) {
        if (!gamePaused)
          timePaused = System.currentTimeMillis() - (startTime);
        gamePaused = true;
        bgMusic.pause();
      }
    }

    if (gamePaused)
      renderPauseMenu();

  }

  private void renderPauseMenu() {

    batch.begin();
    menuSprite.setPosition(camera.position.x - (menuSprite.getWidth() / 2),
        camera.position.y - (menuSprite.getHeight() / 10)*4);
    menuSprite.setScale(0.6f, 0.4f);

    timerFont.draw(batch, "Do you really want to quit?",
        (camera.position.x - 120), (camera.position.y + 60));
    
    Color previousColor = timerFont.getColor();
    timerFont.setColor(new Color(Color.RED));
    timerFont.draw(batch, "[Y]",
        (camera.position.x - 55), (camera.position.y + 20));
    timerFont.setColor(new Color(previousColor));
    timerFont.draw(batch, "es!",
        (camera.position.x - 28), (camera.position.y + 20));
    timerFont.setColor(new Color(Color.GREEN));
    timerFont.draw(batch, "[N]",
        (camera.position.x+11), (camera.position.y + 20));
    timerFont.setColor(new Color(previousColor));
    timerFont.draw(batch, "o!",
        (camera.position.x + 39), (camera.position.y + 20));
    timerFont.setColor(previousColor);
    
    menuSprite.draw(batch);
    batch.end();

    if (Gdx.input.isKeyPressed(Keys.Y)) {
      fastCars.setScene(new LevelMenu(fastCars));

    }
    
    else if(Gdx.input.isKeyPressed(Keys.N))
    {
      bgMusic.play();
      gamePaused = false;
      startTime = System.currentTimeMillis() - timePaused;
    }
    

  }

  // When the race is over this method will be contacted and render the menu for
  // the return menu.
  private void renderRaceCompleteMenu() {
    batch.begin();
    menuSprite.setPosition(camera.position.x - (menuSprite.getWidth() / 2),
        camera.position.y - (menuSprite.getHeight() / 2));
    menuSprite.setScale(0.8f, 0.5f);
    timerFont.draw(batch, "Press enter to return.", (camera.position.x - 120),
        (camera.position.y - 40));
    menuSprite.draw(batch);

    {
      switch (whoWon) {
      case 1:
        lapFontRed.draw(batch, nameCar1 + " won!", (camera.position.x - 130),
            camera.position.y + 50);
        break;
      case 2:
        lapFontGreen.draw(batch, nameCar2 + " won!", (camera.position.x - 130),
            camera.position.y + 50);
        break;
      default:
        break;
      }

    }

    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
      bgMusic.stop();
      fastCars.setScene(new LevelMenu(fastCars));
    }

    batch.end();
  }

  // Renders the count down in the beginning.
  private void renderCountDown(float x, float y) {
    CharSequence timeString;
    stopTime = System.currentTimeMillis() - startTime;
    timeString = this.formatTime(stopTime);
    int timeLeft = 3 - Character.getNumericValue(timeString.charAt(4));
    String drawString = Integer.toString(timeLeft);
    if (timeLeft < 1) {
      drawString = "Start!";
      x = x - 35;
    }
    if (timeLeft < 0) {
      raceStarted = true;
      startTime = System.currentTimeMillis();
      stopTime = 0;
    }

    lapFontRed.draw(batch, drawString, x, y);
  }

  // Renders everything centered around the first car.
  // This will set the cameras origo to 0,0.
  private void renderFirstCar() {

    CharSequence currentLapCar1 = String.format("Lap: %d/3", car.getLap());

    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2,
        Gdx.graphics.getHeight());
    Vector2 carPosition1 = car.getPosition();

    camera.update();
    camera.setToOrtho(false, Gdx.graphics.getWidth() / 2,
        Gdx.graphics.getHeight());
    camera.update();
    camera.position.set(
        ((carPosition1.x * PIXELS_PER_METER) - carSprite.getWidth() / 2),
        ((carPosition1.y * PIXELS_PER_METER) - carSprite.getHeight() / 2), 0);
    camera.update();

    batch.setProjectionMatrix(camera.combined);
    
    updateCar1();

    batch.begin();
    mapSprite.draw(batch);
    carSprite.draw(batch);
    car2Sprite.draw(batch);
    if (!raceStarted)
      renderCountDown((camera.position.x), camera.position.y + 100);
    lapFontRed.setColor(1, 0, 0, 1);
    lapFontRed.draw(batch, currentLapCar1, camera.position.x - 230,
        camera.position.y + 270);

    String speed = Integer.toString(Math.round(car.getSpeedKMH())) + " km/h";

    timerFont.draw(batch, speed, camera.position.x - 220,
        camera.position.y - 250);

    if (finishedCar1) {
      lapFontRed.draw(batch, "Race Complete!", (camera.position.x - 100),
          camera.position.y + 100);

      timerFont.draw(batch, formatTime(raceTimeCar1),
          (camera.position.x - 100), (camera.position.y + 60));

    }

    batch.end();

  }

  // Renders everything centered around the 2nd car.
  // This method sets the camera to origo around the graphics.width/2.
  private void renderSecondCar() {
    Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0,
        Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

    CharSequence currentLapCar2 = String.format("Lap: %d/3", car2.getLap());

    Vector2 carPosition2 = car2.getPosition();

    camera.update();
    camera.setToOrtho(false, Gdx.graphics.getWidth() / 2,
        Gdx.graphics.getHeight());
    camera.update();
    camera.position.set(
        ((carPosition2.x * PIXELS_PER_METER) - car2Sprite.getWidth() / 2),
        ((carPosition2.y * PIXELS_PER_METER) - car2Sprite.getHeight() / 2), 0);
    camera.update();

    batch.setProjectionMatrix(camera.combined);

    
    updateCar2();

    batch.begin();
    mapSprite.draw(batch);
    carSprite.draw(batch);
    car2Sprite.draw(batch);

    if (!raceStarted)
      renderCountDown((camera.position.x), camera.position.y + 100);

    lapFontGreen.draw(batch, currentLapCar2, camera.position.x + 110,
        camera.position.y + 270);

    String speed = Integer.toString(Math.round(car2.getSpeedKMH())) + " km/h";
    timerFont.draw(batch, speed, camera.position.x + 150,
        camera.position.y - 250);

    if (finishedCar2) {
      lapFontGreen.draw(batch, "Race Complete!", (camera.position.x - 100),
          camera.position.y + 100);
      timerFont.draw(batch, formatTime(raceTimeCar2),
          (camera.position.x - 100), (camera.position.y + 60));
    }
    batch.end();

  }

  // Draws the timer on the top of the screen.
  private void drawTimerInfo() {

    if (raceStarted) {

      Gdx.gl
          .glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

      camera.update();
      camera.setToOrtho(false, Gdx.graphics.getWidth(),
          Gdx.graphics.getHeight());
      camera.update();

      camera.position.set(0, 0, 0);
      camera.update();

      batch.setProjectionMatrix(camera.combined);

      CharSequence timeString;

      if (gamePaused)
        timeString = this.formatTime(timePaused);
      else if (finishedCar1 && finishedCar2 && stopTime > 0) {
        timeString = this.formatTime(stopTime);
      }

      else if (finishedCar1 && finishedCar2 && stopTime == 0) {
        stopTime = System.currentTimeMillis() - (startTime);
        timeString = this.formatTime(stopTime);
      }

      else
        timeString = this
            .formatTime((System.currentTimeMillis() - (startTime)));

      batch.begin();
      timerFont.setColor(1, 1, 1, 1);
      timerFont.draw(batch, timeString, camera.position.x
          - (timerFontWidth / 2), camera.position.y
          + (Gdx.graphics.getHeight() * 0.45f));

      batch.end();

    }

  }

  // Will handle the inputs for the first car as well as update the sprite
  // to follow the physical body of the car.
  private void updateCar1() {

    Vector2 carPosition = car.getPosition();

    if (!finishedCar1 && raceStarted) {

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

    }

    else {
      car.steer = GameScreen.STEER_NONE;
      car.accelerate = GameScreen.ACC_NONE;

    }

    car.update(Gdx.app.getGraphics().getDeltaTime());

    carSprite.setPosition(
        (carPosition.x * PIXELS_PER_METER) - carSprite.getWidth() / 2,
        (carPosition.y * PIXELS_PER_METER) - carSprite.getHeight() / 2);
    carSprite.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight() / 2);
    carSprite.setRotation(car.getAngle() * MathUtils.radiansToDegrees - 180);

  }

  // Will handle the inputs for the second car as well as update the sprite
  // to follow the physical body of the car.
  private void updateCar2() {

    Vector2 car2Position = car2.getPosition();

    // Keys and update for 2nd car.
    if (twoPlayers) {
      if (!finishedCar2 && raceStarted) {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
          car2.accelerate = GameScreen.ACC_ACCELERATE;
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
      }

      else {
        car2.accelerate = GameScreen.ACC_NONE;
        car2.steer = GameScreen.STEER_NONE;
      }
      car2.update(Gdx.app.getGraphics().getDeltaTime());

      car2Sprite.setPosition(
          (car2Position.x * PIXELS_PER_METER) - carSprite.getWidth() / 2,
          (car2Position.y * PIXELS_PER_METER) - car2Sprite.getHeight() / 2);
      car2Sprite.setOrigin(car2Sprite.getWidth() / 2,
          car2Sprite.getHeight() / 2);
      car2Sprite
          .setRotation(car2.getAngle() * MathUtils.radiansToDegrees - 180);

    }

  }

  // Checks and updates the laps for the cars.
  private void updateCarLaps() {

    // Check if the first car have crossed the finish line.
    if (finishLine.contains(carSprite.getX(), carSprite.getY())) {
      if (car.getPassedCheckPoints()) {
        if ((car.getLap() + 1) == 2) {
          if (whoWon == 0)
            this.whoWon = 1;
          // Car1 will record twice for some reason, this should prevent this.
          if (!finishedCar1) {
            raceTimeCar1 = System.currentTimeMillis()
                - (startTime - timeLapsed);
            new HighScore(this.fastCars).checkIfTimeIsBetterAndSave(nameCar1,
                formatTime(raceTimeCar1), this.trackName);
          }

          finishedCar1 = true;

        } else {
          car.setLap(car.getLap() + 1);
          car.resetCheckPoints();
        }
      }
    }

    // Check if the first car have crossed the first checkpoint..
    if (checkPoint1.contains(carSprite.getX(), carSprite.getY())) {
      car.setPassedCheckPoint(true, 0);
    }

    // Check if the 2nd car have crossed the finish line.
    if (finishLine.contains(car2Sprite.getX(), car2Sprite.getY())) {
      if (car2.getPassedCheckPoints()) {
        if (car2.getLap() + 1 == 2) {
          if (this.whoWon == 0)
            this.whoWon = 2;

          finishedCar2 = true;
          raceTimeCar2 = System.currentTimeMillis() - startTime;
          new HighScore(fastCars).checkIfTimeIsBetterAndSave(nameCar2,
              formatTime(raceTimeCar2), this.trackName);

        }

        else {
          car2.setLap(car2.getLap() + 1);
          car2.resetCheckPoints();
        }

      }
    }

    // Check if the 2nd car have crossed the first checkpoint..
    if (checkPoint1.contains(car2Sprite.getX(), car2Sprite.getY())) {
      car2.setPassedCheckPoint(true, 0);
    }

  }

  // Creates sprites for the two cars as well as the tracks sprite.
  // Also creates the rectangles handling checkpoints and finish.
  private void createSprites() {

    carTexture = new Texture(Gdx.files.internal("data/gfx/CarRed.png"));
    carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    carSprite = new Sprite(carTexture);
    carSprite.setSize(PIXELS_PER_METER * CAR_WIDTH, PIXELS_PER_METER
        * CAR_WIDTH * carSprite.getHeight() / carSprite.getWidth());

    if (twoPlayers) {
      carTexture = new Texture(Gdx.files.internal("data/gfx/CarGreen.png"));
      carTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

      car2Sprite = new Sprite(carTexture);
      car2Sprite.setSize(PIXELS_PER_METER * CAR_WIDTH, PIXELS_PER_METER
          * CAR_WIDTH * car2Sprite.getHeight() / car2Sprite.getWidth());

    }
    mapSprite = track.createSprite();
    mapSprite.setSize(PIXELS_PER_METER * TRACK_WIDTH, PIXELS_PER_METER
        * TRACK_WIDTH * mapSprite.getHeight() / mapSprite.getWidth());
    mapSprite.setPosition(0, 0);

    Texture menuTexture = new Texture(
        Gdx.files.internal("img/SUBBackground.png"));
    menuSprite = new Sprite(menuTexture);

    finishLine = new Rectangle(track.FINISHLINEPOSx, track.FINISHLINEPOSy,
        track.FINISHLINEWIDTH, track.FINISHLINEHEIGHT);
    checkPoint1 = new Rectangle(track.CHECKPOINTPOSx, track.CHECKPOINTPOSy,
        track.CHECKPOINTWIDTH, track.CHECKPOINTHEIGHT);

  }

  @Override
  public void dispose() {
    batch.dispose();

    carTexture.dispose();
    world.dispose();
  }

  // Formats the time string as the string will be formated in milliseconds when
  // passed from Libgdx.
  private String formatTime(long time) {
    double rest = time % 1000 / 10;
    double seconds = (double) time / 1000;
    double minutes = seconds / 60;
    seconds = seconds % 60;

    return String.format("%02d:%02d:%02d", (int) minutes, (int) seconds,
        (int) rest);

  }

  @Override
  public void resize(int width, int height) {

  }

  // This method will be called ONCE when the object is first displayed.
  // Here we will set up most of the inmformation as well as make sure that all
  // the fonts/sprites etc have been loaded.
  @Override
  public void show() {

    bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/gfx/Storm.mp3"));

    if (fastCars.gameMusic) {
      bgMusic.play();
      bgMusic.setVolume(0.5f);

    }

    timerFont = new BitmapFont(
        Gdx.files.internal("ui/fonts/impact25white.fnt"),
        Gdx.files.internal("ui/fonts/impact25white.png"), false);
    lapFontRed = new BitmapFont(Gdx.files.internal("ui/fonts/impact40red.fnt"),
        Gdx.files.internal("ui/fonts/impact40red.png"), false);
    lapFontGreen = new BitmapFont(
        Gdx.files.internal("ui/fonts/impact40green.fnt"),
        Gdx.files.internal("ui/fonts/impact40green.png"), false);

    startTime = System.currentTimeMillis();

    CharSequence timeString = this
        .formatTime((System.currentTimeMillis() - startTime));
    timerFontWidth = timerFont.getBounds(timeString).width;

    screenWidth = Gdx.graphics.getWidth();
    screenHeight = Gdx.graphics.getHeight();

    screenWidth = Gdx.graphics.getWidth();
    screenHeight = Gdx.graphics.getHeight();

    // Creates a new world object for simulation of physics.
    world = new World(new Vector2(0.0f, 0.0f), true);

    this.track = new TrackLoader(trackName, world, TRACK_WIDTH);

    createSprites();

    camera = new OrthographicCamera(screenWidth, screenHeight);
    batch = new SpriteBatch();

    // Creates first car
    this.car = new Car(world, CAR_WIDTH, CAR_LENGTH, new Vector2(
        this.track.POSITIONCAR1x, this.track.POSITIONCAR1y), (float) Math.PI,
        POWER, STEERANGLE, MAXSPEED);

    // Creates a 2nd car if two players are available.
    if (twoPlayers) {
      this.car2 = new Car(world, CAR_WIDTH, CAR_LENGTH, new Vector2(
          this.track.POSITIONCAR2x, this.track.POSITIONCAR2y), (float) Math.PI,
          POWER, STEERANGLE, MAXSPEED);

    }

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