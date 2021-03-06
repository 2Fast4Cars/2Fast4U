package com.me.fastcars;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class TrackLoader {
	
	Body trackBody;
	String map;
	public final int POSITIONCAR1x;
  public final int POSITIONCAR1y;
  public final int POSITIONCAR2x;
  public final int POSITIONCAR2y;
  public final int FINISHLINEPOSx;
  public final int FINISHLINEPOSy;
  public final int FINISHLINEWIDTH;
  public final int FINISHLINEHEIGHT;

  public final int CHECKPOINTPOSx;
  public final int CHECKPOINTPOSy;
  public final int CHECKPOINTWIDTH;
  public final int CHECKPOINTHEIGHT;
  
	public TrackLoader(String map, World world, float TRACK_WIDTH) {
		this.map = map;
		
	    // 0. Create a loader for the file saved from the editor.
	    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/MapLoader.json"));
	 
	    // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    bd.type = BodyType.StaticBody;
	 
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 1;
	    fd.friction = 0.5f;
	    fd.restitution = 0.3f;
	    
	    // 3. Create a Body, as usual.
	    trackBody = world.createBody(bd);
	 
	    // 4. Create the body fixture automatically by using the loader.
	    loader.attachFixture(trackBody, map , fd, TRACK_WIDTH);
	    
	    int[] positions = new int[4];

	    positions = FileHandler.carPositionForTrack(this.map);
	    POSITIONCAR1x = positions[0];
	    POSITIONCAR1y = positions[1];
      POSITIONCAR2x = positions[2];
      POSITIONCAR2y = positions[3];
      
      int[][] finishLineAndCheckPoint = FileHandler.finishLineAndCheckPointRectangle(map);
      FINISHLINEPOSx = finishLineAndCheckPoint[0][0];
      FINISHLINEPOSy = finishLineAndCheckPoint[0][1];
      FINISHLINEWIDTH = finishLineAndCheckPoint[0][2];
      FINISHLINEHEIGHT = finishLineAndCheckPoint[0][3];
      
      CHECKPOINTPOSx = finishLineAndCheckPoint[1][0];
      CHECKPOINTPOSy = finishLineAndCheckPoint[1][1];
      CHECKPOINTWIDTH = finishLineAndCheckPoint[1][2];
      CHECKPOINTHEIGHT = finishLineAndCheckPoint[1][3];
      
	}
	
	public Sprite createSprite(){
		
	Texture	mapTexture = new Texture(Gdx.files.internal("tracks/" + map + ".png"));
			mapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	Sprite	mapSprite = new Sprite(mapTexture);
	
	return mapSprite;
	}
}
