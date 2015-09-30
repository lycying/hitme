package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
/**
 * @author lycying@gmail.com
 */
public class WeldJointData extends JointData {
	// / The local anchor point relative to body1's origin.
	public final Vector2 localAnchorA = new Vector2();

	
	public void build(){
		WeldJointDef def = new WeldJointDef();
		def.initialize(bodyA.body, bodyB.body, 
				bodyA.body.getWorldPoint(
						getLocalPointWithAngle(this.localAnchorA,bodyA)
						.mul(1/Cb2World.RADIO))
				);
		def.collideConnected = this.collideConnected;
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		render.setColor(DebugColor.COLOR_WeldJoint);
		render.begin(ShapeType.Line);
		if(null==this.joint){
			Vector2 p =  bodyA.center.cpy().add(localAnchorA);
			render.line(bodyA.center.x, bodyA.center.y, p.x, p.y);
			render.line(bodyB.center.x, bodyB.center.y, p.x, p.y);
		}else {
			Vector2 p1 = bodyA.body.getPosition().mul(Cb2World.RADIO);
			Vector2 p2 = bodyB.body.getPosition().mul(Cb2World.RADIO);
			Vector2 p  = joint.getAnchorA().mul(Cb2World.RADIO);
			render.line(p1.x, p1.y, p.x, p.y);
			render.line(p2.x, p2.y, p.x, p.y);
		}
		render.end();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 ;
	}
}
