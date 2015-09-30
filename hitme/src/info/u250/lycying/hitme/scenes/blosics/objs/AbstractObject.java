package info.u250.lycying.hitme.scenes.blosics.objs;

import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.lycying.hitme.scenes.SceneBlosics;
import info.u250.lycying.hitme.scenes.blosics.arts.AbstractAnimation;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractObject extends Cb2Object {

	SceneBlosics holder  ;
	public boolean wind = false;
	public float windDeg = 0;
	protected AbstractObject(){
		
	}
	public AbstractObject(BodyData data,AbstractAnimation sprite,SceneBlosics holder){
		super(data,sprite);
		
		this.data.body.setSleepingAllowed(true);
		this.data.body.setAwake(false);
		this.holder = holder;
		
		sprite.animSetup();
		
	}
	
	
	@Override
	public void render(float delta) {
		super.render(delta);
		if(wind){
			this.data.body.applyForceToCenter
			(new Vector2(MathUtils.cosDeg(this.windDeg)*20,MathUtils.sinDeg(this.windDeg)*20));
		}
	}
}
