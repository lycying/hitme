package info.u250.lycying.hitme.scenes.blosics;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.lycying.hitme.Hitme;
import info.u250.lycying.hitme.scenes.SceneBlosics;
import info.u250.lycying.hitme.scenes.blosics.objs.AbstractObject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class BlosicsContactListener implements ContactListener {
	SceneBlosics blosics;
	public BlosicsContactListener(SceneBlosics blosics){
		this.blosics = blosics;
	}
	
	float size = 50/Cb2World.RADIO;
	final Vector2 pos = new Vector2();
	
	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture(Fixture fixture) {
			final Vector2 pos2 = fixture.getBody().getPosition().sub(pos);
			final float len = pos2.len();
			final float angle = pos2.angle();
			if(fixture.getBody().getType() == BodyType.DynamicBody){
				fixture.getBody().setAwake(true);
//				fixture.getBody().applyForceToCenter(-1000*len*MathUtils.cosDeg(angle), -1000*len*MathUtils.sinDeg(angle));
				fixture.getBody().setLinearVelocity(100*len*MathUtils.cosDeg(angle), 100*len*MathUtils.sinDeg(angle));
			}
			return true;
		}
	};
	@Override
	public void beginContact(Contact contact) {
		try{
			Cb2Object obj1 = blosics.group.findByBody(contact.getFixtureA().getBody());
			Cb2Object obj2 = blosics.group.findByBody(contact.getFixtureB().getBody());
			if(obj1.data.res.equals("wind")){
				if(obj2.data.isDynamic)
				if(obj2 instanceof AbstractObject){
					AbstractObject theObject = AbstractObject.class.cast(obj2);
					theObject.wind = true;
					theObject.windDeg = obj1.data.angle;
				}
			}else if(obj2.data.res.equals("wind")){
				if(obj1.data.isDynamic)
					if(obj1 instanceof AbstractObject){
						AbstractObject theObject = AbstractObject.class.cast(obj1);
						theObject.wind = true;
						theObject.windDeg = obj2.data.angle;
					}
			}

		}catch(Exception ex){
		}
	}

	@Override
	public void endContact(Contact contact) {
		try{
			Cb2Object obj1 = blosics.group.findByBody(contact.getFixtureA().getBody());
			Cb2Object obj2 = blosics.group.findByBody(contact.getFixtureB().getBody());
			if(obj1.data.res.equals("wind")){
				if(obj2.data.isDynamic)
				if(obj2 instanceof AbstractObject){
					AbstractObject theObject = AbstractObject.class.cast(obj2);
					theObject.wind = false;
				}
			}else if(obj2.data.res.equals("wind")){
				if(obj1.data.isDynamic)
					if(obj1 instanceof AbstractObject){
						AbstractObject theObject = AbstractObject.class.cast(obj1);
						theObject.wind = false;
					}
			}

		}catch(Exception ex){
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		try{
			Cb2Object obj1 = blosics.group.findByBody(contact.getFixtureA().getBody());
			Cb2Object obj2 = blosics.group.findByBody(contact.getFixtureB().getBody());
			if(null!=obj1&&null!=obj2){
				//bomb
				if(obj1.data.res.equals("block-gray") ){
					if(impulse.getNormalImpulses()[0]>1f){
						contact.getFixtureA().getBody().setAwake(true);
						pos.set(contact.getFixtureA().getBody().getPosition());
						Cb2World.getInstance().world().QueryAABB(callback, pos.x - size, pos.y - size, pos.x + size, pos.y + size);
						if(null!=obj1){
							obj1.setVisiable(false);
						}
					}
				}
				//the stone sound
				if(obj2.data.data.equals("main")){					
					if(impulse.getNormalImpulses()[0]>1.5f){
						if(obj1.data.res.equals("stone")){
							Engine.getSoundManager().playSound("Blosics_hit_stone");
						}else if(obj1.data.res.equals("platform")){
							Engine.getSoundManager().playSound("Blosics_hit_platfom");
						}else{
							Engine.getSoundManager().playSound("Blosics_fire"+(Hitme.random.nextInt(5)+1));
						}
					}
					
				}
				
			}
		}catch(Exception ex){
//			ex.printStackTrace();
		}
	}

}
