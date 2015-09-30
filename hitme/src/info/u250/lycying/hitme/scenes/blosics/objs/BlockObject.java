package info.u250.lycying.hitme.scenes.blosics.objs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.lycying.hitme.scenes.SceneBlosics;
import info.u250.lycying.hitme.scenes.blosics.arts.AbstractAnimation;

public  class BlockObject extends AbstractBlockObject {	
	public BlockObject(BodyData data,AbstractAnimation sprite,SceneBlosics holder){
		super(data,sprite,holder);
	}
	

	@Override
	protected void die() {
		holder.number ++ ;
		Engine.getSoundManager().playSound("Blosics_dispose");
	}
}
