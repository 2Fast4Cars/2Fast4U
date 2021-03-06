package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class HighScore extends MainMenu implements Screen {

  String[][] highscoreList = new String[10][2];
  List trackList;
  String highScoreListString;
  Label highScoreNameText;
  Label highScoreTimeText;

  public HighScore(FastCars fastCar, Music music) {
    super(fastCar, false);
    super.music = music;
  }

  public HighScore(FastCars fastCar) {
    super(fastCar, false);
  }
  
    
  @Override
  public void render(float delta) {
    super.render(delta);
    
    String track = trackList.getSelection().substring(0, trackList.getSelection().length());
    highscoreList = FileHandler.readList(track);
    sortList(highscoreList);
    
    
    String highscoreNames = "";
    String highscoreTimes = "";
    // Numbers
    for (int i = 0; i < 10; i++) {
      String name = highscoreList[i][0];
      String time = highscoreList[i][1];
      if(name == null)
        name = "";
      if(time == null)
        time = "";
      

      highscoreNames = highscoreNames + String.format("%02d  ", i + 1) + name +"\n";
      highscoreTimes = highscoreTimes + time + "\n";
    }

    batch.begin();

    highScoreNameText.setText(highscoreNames);
    highScoreNameText.draw(batch, 1);
    highScoreTimeText.setText(highscoreTimes);
    highScoreTimeText.draw(batch, 1);
    
    batch.end();    

  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

  }

  @Override
  public void show() {
    super.show();
    super.subMenu = true;
  
    highScoreNameText = new Label(highScoreListString, skin);
    highScoreTimeText = new Label(highScoreListString, skin);

    // Creates the tracklist. 
    trackList = new List(FileHandler.readTracks(), skin);
    
    // Creates the scroll.
    ScrollPane scrollPane = new ScrollPane(trackList, skin);
    
    // Putting stuffs together
	tableSUB.add("HighScore       Nr").padLeft(65);
	tableSUB.add("Name                  Time").width(tableSUB.getWidth() / 2 );
	tableSUB.add().width(tableSUB.getWidth() / 2 );
	tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
    
	tableSUB.add(scrollPane).uniformX().expandY().size(150, 140);  
    
	tableSUB.add().uniformX();
	
	
    // insert a HighScore list. 
	String track = trackList.getSelection().substring(0, trackList.getSelection().length()-1);
    
    highscoreList = FileHandler.readList(track);
    highScoreNameText.setPosition((Gdx.graphics.getWidth()/2)+80, Gdx.graphics.getHeight()/2);
    highScoreTimeText.setPosition((Gdx.graphics.getWidth()/2)+290, Gdx.graphics.getHeight()/2);
    


    
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


  private String[][] sortList(String[][] list) {
    for (int i = 0; i < list.length; i++) {
      for (int o = i + 1; o < list.length; o++) {

        float tmp1 = 1000000;
        float tmp2 = 1000000;

        if (list != null && !list[i][1].isEmpty())
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

  public void checkIfTimeIsBetterAndSave(String name, String time, String trackName) {

    highscoreList = FileHandler.readList(trackName);
    sortList(highscoreList);
    
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

    FileHandler.saveListToFile(trackName, highscoreList);
    
  }

}
