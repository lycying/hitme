package info.u250.lycying.hitme.scenes.blosics.objs;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.lycying.hitme.scenes.SceneBlosics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MainObject extends AbstractObject {

	public MainObject(SceneBlosics holder) {
		this.holder = holder;
		this.wind = false;
	}

	Array<AdvanceSprite> dots = new Array<AdvanceSprite>();
	

	public void spawn(){
		if(null!=this.data && null!=this.data.body){
			Cb2World.getInstance().world().destroyBody(this.data.body);
		}
		dots.clear();
		
		CircleData data = new CircleData();
		data.radius = this.object.getWidth()/2;
		data.density = 1f;
		data.restitution = 0.2f;
		data.data = "main";
		this.data = data;
		
		this.setup();
		
		this.data.body.setBullet(true);
	}
	@Override
	public void render(float delta) {
		if(this.data!=null&&this.data.body!=null){
			super.render(delta);
			if(dots.size<30){
				Vector2 pos = new Vector2(this.object.getX(), this.object.getY());
				if(dots.size>1){
					Vector2 nowPos = new Vector2(dots.peek().getX(),dots.peek().getY());
					if(pos.dst(nowPos)>25){
						newDot(pos);
					}
				}else{
					newDot(pos);
				}
				
			}
//			Engine.getSpriteBatch().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			for(AdvanceSprite dot:dots){
				dot.render(delta);
			}
//			Engine.getSpriteBatch().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
		}
	}
	void newDot(Vector2 pos){
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		final AdvanceSprite dot = new AdvanceSprite(wd.findRegion("path"));
		
		dot.setSize(6, 6);
		dot.setOrigin(3, 3);
		dot.setColor(new Color(1,1,1,(40f-this.dots.size)/40f));
		
		dot.setPosition(pos.x,pos.y);
		this.dots.add(dot);
	}
}
