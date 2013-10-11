package com.me.fastcars;

import com.badlogic.gdx.Screen;

public class About extends MainMenu implements Screen {

	
	public About(FastCars fastCar) {
		super(fastCar, false);
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
		

		// Contacts the "subMenuA" in the superClass(BaseMenuClass) and set it to true. 
				super.subMenuA = true;  
				
		// Putting stuffs together		
				tableSUB.add("About").center();
				tableSUB.add().width(tableSUB.getWidth() / 2 );
				tableSUB.add().expandX().width(tableSUB.getWidth() / 2 ).row();
				
				
				tableSUB.add().uniformX().expandY();
				tableSUB.add().uniformX();
				
				
				
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

}
