package info.u250.lycying.hitme.scenes.blosics.arts;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.lycying.hitme.Hitme;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlockAnimation extends AbstractAnimation{
	
	public BlockAnimation(TextureRegion region) {
		super(region);
	}

	@Override
	public void animSetup() {
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		int size = Hitme.random.nextInt(8)+1;
		float[] times = new float[size*4+1];
		TextureRegion[] regions = new TextureRegion[size*4+1];
		for(int i=0;i<size;i++){
			times[4*i] = Hitme.random.nextFloat()*3+0.3f;
			times[4*i+1] = Hitme.random.nextFloat()*5+2.3f;
			times[4*i+2] = Hitme.random.nextFloat()*5+0.3f;
			times[4*i+3] = Hitme.random.nextFloat()+0.3f;
			
			regions[4*i] = wd.findRegion("anim1");
			regions[4*i+1] = wd.findRegion("anim2");
			regions[4*i+2] = wd.findRegion("anim3");
			regions[4*i+3] = wd.findRegion("anim4");
		}
		times[size*4] = 0.5f;
		regions[size*4] = wd.findRegion("anim5");
		
		this.anim = new AnimationSprite(times, regions);
		this.anim.setSize(this.getWidth(), this.getHeight());
		this.anim.setOrigin(this.getWidth()/2, this.getHeight()/2);
		this.anim.setAnimationSpriteListener(listener);
	}
}
