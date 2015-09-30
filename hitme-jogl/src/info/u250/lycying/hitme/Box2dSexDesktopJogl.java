package info.u250.lycying.hitme;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AdControl;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Box2dSexDesktopJogl {
	public static void main(String[] args){
		Hitme instance = new Hitme(new AdControl() {
			
			@Override
			public void show() {
				
			}
			
			@Override
			public void hide() {
				
			}
		});
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = Engine.useGL20();
		config.width  =(int) Engine.getWidth();
		config.height =(int) Engine.getHeight();
		new LwjglApplication(instance, config);
	}
}
