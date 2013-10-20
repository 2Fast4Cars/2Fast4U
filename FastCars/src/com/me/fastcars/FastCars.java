package com.me.fastcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FastCars extends Game {
	
	protected boolean gameMusic = true;
	
	@Override
	public void create() {		
	    FileHandler.finishLineAndCheckPointRectangle("MarioCircuit1");
	    setScreen(new MainMenu(this, true));
	}
	
	
	public void setScene(Screen screen){
		
		setScreen(screen);
	}
	
}
