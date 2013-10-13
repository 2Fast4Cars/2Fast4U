package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class HighScore extends MainMenu implements Screen {

  Preferences highscore = Gdx.app.getPreferences("Highscore");
  String[][] highscoreList = new String[10][2];

  public HighScore(FastCars fastCar) {
    super(fastCar, false);
    readList();
    highscoreList = sortList(highscoreList);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

  }

  @Override
  public void show() {
    super.show();
    super.subMenu = true;
    
    
    // Putting stuffs togehter
    
		tableSUB.add();
		tableSUB.add("HighScore").left().padLeft(15f);
		tableSUB.add().expandX().width(tableSUB.getWidth() / 3 ).row();
	// insert a HighScore list.  	
		
		// Numbers
	for (int i = 0; i < 8; i++) {
		tableSUB.add(String.format("%02d", i + 1)).padLeft(20f);
	    
		// Names 
	    tableSUB.add(highscoreList[i][0]).align(Align.left).padLeft(15f).padRight(200f);
	    
	    // Times
	    tableSUB.add(highscoreList[i][1]).align(Align.left).expandY();
	    tableSUB.row();

    }

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

  private void readList() {
    for (int i = 0; i < 10; i++) {

      highscoreList[i][0] = highscore.getString("Name" + (i + 1));
      highscoreList[i][1] = highscore.getString("Time" + (i + 1));

    }

  }

  private String[][] sortList(String[][] list) {
    for (int i = 0; i < list.length; i++) {
      for (int o = i + 1; o < list.length; o++) {

        float tmp1 = 1000000;
        float tmp2 = 1000000;

        if (!list[i][1].isEmpty())
          tmp1 = Float.parseFloat(list[i][1].replace(":", ""));

        if (!list[o][1].isEmpty())
          tmp2 = Float.parseFloat(list[o][1].replace(":", ""));

        if (tmp1 > tmp2) {
          String tmpName = list[i][0];
          String tmpTime = list[i][1];

          list[i][0] = list[o][0];
          list[i][1] = list[o][1];

          list[o][0] = tmpName;
          list[o][1] = tmpTime;
        }
      }
    }

    return list;

  }

  public void checkIfTimeIsBetterAndSave(String name, String time) {

    String[][] tempList = new String[11][2];
    for (int i = 0; i < 10; i++) {
      tempList[i][0] = highscoreList[i][0];
      tempList[i][1] = highscoreList[i][1];
    }

    tempList[10][0] = name;
    tempList[10][1] = time;

    tempList = sortList(tempList);

    for (int i = 0; i < 10; i++) {
      highscoreList[i][0] = tempList[i][0];
      highscoreList[i][1] = tempList[i][1];
    }

    saveListToFile();
  }

  private void saveListToFile() {
    for (int i = 0; i < 10; i++) {
      highscore.putString("Name" + (i + 1), highscoreList[i][0]);
      highscore.putString("Time" + (i + 1), highscoreList[i][1]);
    }

    highscore.flush();
  }

}
