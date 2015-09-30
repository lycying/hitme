package info.u250.lycying.hitme.scenes.blosics.objs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.lycying.hitme.scenes.SceneBlosics;
import info.u250.lycying.hitme.scenes.blosics.arts.AbstractAnimation;

public abstract class AbstractBlockObject extends AbstractObject {
	public AbstractBlockObject(BodyData data,AbstractAnimation sprite,SceneBlosics holder){
		super(data, sprite, holder);
	}
	protected abstract void die();

	@Override
	public void render(float delta) {
		super.render(delta);
		if (this.data.isDynamic)
			if (!this.isVisiable() || this.object.getX() < -50
					|| this.object.getY() < -50
					|| this.object.getX() > Engine.getWidth()
					|| this.object.getY() > Engine.getHeight() * 2) {

				Cb2World.getInstance().world().destroyBody(this.data.body);
				holder.group.removeValue(this, false);
				this.die();
				holder.hud.currentNeedLabel.setText("" + holder.number);
			}
	}
}
