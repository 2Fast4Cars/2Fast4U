package com.me.fastcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

public class HighScore extends MainMenu implements Screen {
	
	Preferences highscore = Gdx.app.getPreferences("Highscore");
	
	public HighScore(FastCars fastCar) {
		super(fastCar, false);
	}
	
	public HighScore(){
		super();
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
	
	public float[] getHighScoreTimes(){
		float[] tempList = new float[10];
		for(int i=0;i<10;i++)
		{
			tempList[i] = getPlayerHighScore(i);
		}
		
		return tempList;
	}
	
	public void checkIfTimeIsBetter(String name, String time){
		
		int index = 10;
		boolean madeHighscore = false;
		boolean isBetter = true;
		System.out.println(time);
		while(index > 1 && isBetter)
		{
			String tmp = highscore.getString(("Time" + index));
			for(int i = 0;i<tmp.length() && tmp.length() == 6;i++)
			{	
				if(time.charAt(i)<tmp.charAt(i))
				{
					isBetter = true;
					madeHighscore = true;
					System.out.println("It's better!");
					break;
				}
				else if(time.charAt(i) > tmp.charAt(i))
				{
					isBetter = false;
					break;
				}
			}
			
			if(tmp.length() < 6)
				madeHighscore = true;
			
			index--;
			
		}
		
		System.out.println(madeHighscore);
		
		if(madeHighscore)
		{
			saveHighScore(name, time, index);
			System.out.println(name +"  " + time + "  " +"  " + index);
		
		}
	}
	
	public float getPlayerHighScore(int index){
		return Float.parseFloat(highscore.getString(("Time"+index)));
	}
	/***
	 * Bumps down all the players below in the highscore.
	 * @param name - Well, the name.
	 * @param time - The time represented as a string.
	 * @param place - index/place.
	 */
	public void saveHighScore(String name, String time, int place){
		
		int index = 10;
		if(place > 0 && place < 10)
		{
			if(place == 10)
			{
				
				String namePlace = "Name" + place;
				String timePlace = "Time" + place;
	
				highscore.putString(namePlace, name);
				highscore.putString(timePlace, time);
				
			}

			else
			{
				while(index>=place)
				{
					String tmpName = highscore.getString("Name" + (index-1));
					String tmpTime = highscore.getString("Time" + (index-1));			
					
					highscore.putString("Name"+index, tmpName);
					highscore.putString("Time" + index, tmpTime);
					
					index--;
				
				}
			}

		String namePlace = "Name" + place;
		String timePlace = "Time" + place;

		highscore.putString(namePlace, name);
		highscore.putString(timePlace, time);
		
		highscore.flush();		

		}
		
	}


}
