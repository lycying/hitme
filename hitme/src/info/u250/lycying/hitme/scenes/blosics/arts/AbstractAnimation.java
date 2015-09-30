package info.u250.lycying.hitme.scenes.blosics.arts;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AnimationSprite.AnimationSpriteListener;
import info.u250.lycying.hitme.Hitme;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractAnimation extends AdvanceSprite{
	static AnimationSpriteListener listener = new AnimationSpriteListener() {
		@Override
		public void onLastFrame() {
			Engine.getSoundManager().playSound("Blosics_normal_"+(Hitme.random.nextInt(40)+1));
		}
	};
	AnimationSprite anim ;
	public AbstractAnimation(TextureRegion region) {
		super(region);
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		final Vector2 pos = new Vector2(this.getX(),this.getY());
		if(null!=this.anim){
			this.anim.setPosition(
					pos.x+(this.getWidth()-this.anim.getWidth())/2, 
					pos.y+(this.getHeight()-this.anim.getHeight())/2);
			this.anim.setRotation(this.getRotation());
			this.anim.render(delta);
		}
		
	}
	public abstract void animSetup();
}
