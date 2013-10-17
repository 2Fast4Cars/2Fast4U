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
	}
	
	public Sprite createSprite(){
		
	Texture	mapTexture = new Texture(Gdx.files.internal("tracks/" + map + ".png"));
			mapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	Sprite	mapSprite = new Sprite(mapTexture);
	
	return mapSprite;
	}
}
