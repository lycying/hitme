package info.u250.lycying.hitme;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.load.startup.StartupLoading;
import info.u250.c2d.graphic.AdvanceSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LoadDing extends StartupLoading {
	AdvanceSprite sprite;
	ShapeRenderer renderer ;
	final static float OFFSET = 100;
	public LoadDing(){
		renderer = new ShapeRenderer();
		sprite=new AdvanceSprite( new Texture(Gdx.files.internal("logo.png")));
		sprite.setSize(300, 300);
		sprite.setPosition(
				100,
				(Engine.getHeight()-sprite.getHeight())/2);
		sprite.setOrigin(150,150);
	}
	@Override
	protected void finishLoadingCleanup() {
		sprite.getTexture().dispose();
		renderer.dispose();
	}

	@Override
	protected void inLoadingRender(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		renderer.setProjectionMatrix(Engine.getDefaultCamera().combined);
		
		renderer.setColor(Color.GRAY);
		renderer.begin(ShapeType.Line);
		renderer.line(OFFSET, Engine.getHeight()/2, Engine.getWidth()-OFFSET, Engine.getHeight()/2);
		renderer.end();
		renderer.setColor(Color.YELLOW);
		renderer.begin(ShapeType.Line);
		renderer.line(OFFSET, Engine.getHeight()/2,OFFSET+ (Engine.getWidth()-2*OFFSET)*this.percent(),  Engine.getHeight()/2);
		renderer.end();
		Engine.getSpriteBatch().begin();
		sprite.setScale((this.percent()*100)%34f/34f);
		sprite.draw(Engine.getSpriteBatch());
		Engine.getDefaultFont().setColor(Color.WHITE);
		Engine.getDefaultFont().draw(Engine.getSpriteBatch(), "Loading "+(int)(100*this.percent())+"%", 400, 200);
		Engine.getSpriteBatch().end();
	}

}
