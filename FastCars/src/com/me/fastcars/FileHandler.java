package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public  class FileHandler {

  public static int numberOfTracks(){
    FileHandle tracksFile = Gdx.files.internal("data/tracks.cfg");
    String fileString = tracksFile.readString();
    String[] tracks = fileString.split("\n");
    return tracks.length;
    
  }
  
  public static int[] carPositionForTrack(String trackName){
   
    FileHandle tracksFile = Gdx.files.internal("data/tracks.cfg");
    String fileString = tracksFile.readString();
    String[] temp = fileString.split("\n");
    String[][] tracks = new String[numberOfTracks()][5];

    System.out.print(temp[0]);
    for(int i = 0;i<temp.length;i++){
      temp[i] = temp[i].replace("\n", " ");
      temp[i] = temp[i].trim();
    }
    
    for(int i = 0;i<temp.length;i++){
      String[] tmp2;
      tmp2 = temp[i].split(";");
      tracks[i][0] = tmp2[0];
      tracks[i][1] = tmp2[1];
      tracks[i][2] = tmp2[2];
      tracks[i][3] = tmp2[3];
      tracks[i][4] = tmp2[4];
    }
    
    int[] positions = new int[4];
    
    for(int i=0;i<tracks.length;i++)
    {
      if(tracks[i][0].toLowerCase().equals(trackName.toLowerCase()))
      {
        for(int o=0;o<4;o++)
          positions[o] = Integer.parseInt(tracks[i][o+1]);

        System.out.println(positions[0]);
        break;
      }
      
    }
    
    return positions;
    
  }
  
  public static String[][] readList(String trackName) {
    Preferences highscore = Gdx.app.getPreferences(trackName);
    String[][] highscoreList = new String[10][2];
    
    
    for (int i = 0; i < 10; i++) {

      highscoreList[i][0] = highscore.getString("Name" + (i + 1));
      highscoreList[i][1] = highscore.getString("Time" + (i + 1));

    }
    
    return highscoreList;
    
    
  }
  
  public static void saveListToFile(String trackName, String[][] highscoreList) {
    Preferences highscore = Gdx.app.getPreferences(trackName);

    for (int i = 0; i < 10; i++) {
      highscore.putString("Name" + (i + 1), highscoreList[i][0]);
      highscore.putString("Time" + (i + 1), highscoreList[i][1]);
    }

    highscore.flush();
  }
  
  
}
