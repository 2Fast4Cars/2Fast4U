package com.me.fastcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FastCars extends Game {
	
	
	@Override
	public void create() {		
		setScreen(new MenuScene(this));
	}
	
	
	public void setScene(Screen screen){
		
		setScreen(screen);
		
	}
	
}
