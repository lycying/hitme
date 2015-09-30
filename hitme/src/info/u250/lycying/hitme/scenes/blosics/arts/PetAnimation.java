package info.u250.lycying.hitme.scenes.blosics.arts;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PetAnimation extends AbstractAnimation{
	public PetAnimation(TextureRegion region) {
		super(region);
	}
	@Override
	public void animSetup() {
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		
		this.anim = new AnimationSprite(0.25f,wd,"npc000");
		float radio = this.anim.getRegionHeight()/this.getHeight();
		this.anim.setSize(this.anim.getRegionWidth()/radio, this.getHeight());
		this.anim.setOrigin(this.anim.getWidth()/2, this.anim.getHeight()/2);
	}
}
