package info.u250.lycying.hitme.scenes.blosics.arts;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.lycying.hitme.Hitme;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class XBlockAnimation extends AbstractAnimation{
	
	public XBlockAnimation(TextureRegion region) {
		super(region);
		
	}

	@Override
	public void animSetup() {
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		int size = Hitme.random.nextInt(2)+1;
		float[] times = new float[size*4+1];
		TextureRegion[] regions = new TextureRegion[size*4+1];
		for(int i=0;i<size;i++){
			times[4*i] = Hitme.random.nextFloat()*2+0.3f;
			times[4*i+1] = Hitme.random.nextFloat()*2+2.3f;
			times[4*i+2] = Hitme.random.nextFloat()*2+0.3f;
			times[4*i+3] = Hitme.random.nextFloat()+0.3f;
			
			regions[4*i] = wd.findRegion("xanim1");
			regions[4*i+1] = wd.findRegion("xanim2");
			regions[4*i+2] = wd.findRegion("xanim3");
			regions[4*i+3] = wd.findRegion("xanim4");
		}
		times[size*4] = 0.5f;
		regions[size*4] = wd.findRegion("xanim5");
		
		this.anim = new AnimationSprite(times, regions);
		float radio = this.anim.getRegionHeight()/this.getHeight();
		this.anim.setSize(this.anim.getRegionWidth()/radio, this.getHeight());
		this.anim.setOrigin(this.anim.getWidth()/2, this.anim.getHeight()/2);
		this.anim.setAnimationSpriteListener(listener);
	}
}
