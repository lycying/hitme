package info.u250.lycying.hitme.scenes;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.surfaces.SurfaceData;
import info.u250.c2d.graphic.surfaces.serializers.SurfaceWorldReader;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.Cb2TriangleSurfaces;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.lycying.hitme.EngineDriveInstance;
import info.u250.lycying.hitme.Hitme;
import info.u250.lycying.hitme.LevelTools;
import info.u250.lycying.hitme.scenes.blosics.BlosicsContactListener;
import info.u250.lycying.hitme.scenes.blosics.BlosicsHud;
import info.u250.lycying.hitme.scenes.blosics.arts.BlockAnimation;
import info.u250.lycying.hitme.scenes.blosics.arts.PetAnimation;
import info.u250.lycying.hitme.scenes.blosics.arts.XBlockAnimation;
import info.u250.lycying.hitme.scenes.blosics.objs.BlockObject;
import info.u250.lycying.hitme.scenes.blosics.objs.MainObject;
import info.u250.lycying.hitme.scenes.blosics.objs.PetObject;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ObjectMap;
public class SceneBlosics extends InputAdapter implements Scene {
	public Cb2ObjectGroup group ;
	MainObject main;
	public Array<Cb2TriangleSurfaces> surfaces = new Array<Cb2TriangleSurfaces>();
	public SurfaceWorldReader surfaceWorldReader = new SurfaceWorldReader();
	AdvanceSprite control ;
	AdvanceSprite[] arrows;
	boolean worldBuilt = false;
	boolean touchDown = false;
	public int number  = 0;
	public int total;
	boolean win = false;
	ParticleEmitter emitter;
	final Vector2 firstPoint = new Vector2();
	BlosicsContactListener blosicsContactListener;
	ParallaxGroup rbg ;
	CbtWorldReader reader;
	AdvanceSprite mMain ;
	public BlosicsHud hud;
	float delay = 0;
	int level = -1;
	int winleave = 0;
	int fireTime = 0;
	float fireDelta = 0;
	public EngineDriveInstance engineDrive;
	public Array<ParticleEmitter> winds = new Array<ParticleEmitter>();
	AdvanceSprite bgSprite;
	
	public SceneBlosics(EngineDriveInstance engineDrive){
		this.engineDrive = engineDrive;
		this.rbg = Engine.resource("RBG");
		ParticleEffect particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("data/particle/default.p"),Gdx.files.internal("data/particle/"));
		this.emitter = particleEffect.getEmitters().get(0);
		
		
		
//		bg = new SimpleMeshBackground(new Color(1,1,1,1),new Color(17f/255f,196f/255f,1,1));
		hud = new BlosicsHud(this);
		blosicsContactListener = new BlosicsContactListener(this);
		reader = new CbtWorldReader();
		Cb2World.getInstance().installWorld(new World(new Vector2(0,-5f),false));
		Cb2World.getInstance().world().setContactListener(blosicsContactListener);
		
		this.group = new Cb2ObjectGroup();
		this.main = new MainObject(this);
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		control = new AdvanceSprite(wd.findRegion("control"));
		mMain = new AdvanceSprite(wd.findRegion("main"));
		mMain.setSize(16, 16);
		arrows = new AdvanceSprite[5];
		for(int i=0;i<arrows.length;i++){
			arrows[i] = new AdvanceSprite(wd.findRegion("arrow"));
		}
		
		bgSprite = new AdvanceSprite(wd.findRegion("bg"));
		bgSprite.setSize(800, 480);
	}

	public void restart(){
		this.load(level);
	}
	public void nextlevel(){
		this.load(level+1);
	}
	public void load(int level){
		
		
		fireTime=0;
		hud.levelLabel.setText(level+"");
//		this.rbg1.object.setColor(BlockObject.random.nextFloat(), BlockObject.random.nextFloat(), BlockObject.random.nextFloat(), 1);
		touchDown = false;
		worldBuilt = false;
		delay = 0;
		number = 0;
		total = 0;
		win = false;
		
		if(this.level != level){
			this.level = level;
			reader.bodyDatas.clear();
			reader.jointDatas.clear();
			reader.read(Gdx.files.internal("data/blosics/levels/level"+level));
			this.loadConfig();
		}
		control.setScale(0.1f);
		control.setColor(Color.WHITE);
		Engine.getTweenManager().killTarget(control);
		Timeline.createSequence()
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 500f).target(1,1))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 200f).target(0.9f,0.9f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,0.95f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(0.95f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,0.96f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(0.96f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,0.97f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(0.97f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,0.98f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(0.98f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,0.99f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(0.99f,1f))
		.push(Tween.to(this.control, SpriteAccessor.SCALE_XY, 100f).target(1f,1f))
		.push(Tween.to(this.control, SpriteAccessor.OPACITY, 1000f).target(0.5f))
		.start(Engine.getTweenManager());
	}
	@SuppressWarnings("unchecked")
	void loadConfig(){
		ObjectMap<String, Object> map = (ObjectMap<String, Object>)new JsonReader().parse(Gdx.files.internal("data/blosics/levels/level"+level+".config"));
		final float size = Float.parseFloat(map.get("size")+"");
		final float x = Float.parseFloat(map.get("posx")+"");
		final float y = Float.parseFloat(map.get("posy")+"");
		final int win = (int)Float.parseFloat(map.get("win")+"");
		final Object surface = map.get("surface");
		this.control.setSize(size, size);
		this.control.setPosition(x, y);
		this.winleave = win;
		surfaceWorldReader.surfaces.clear();
		if(null!=surface && !"".equals(surface)){
			String[] ss = surface.toString().split("~");
			for(String s:ss){
				surfaceWorldReader.read(Gdx.files.internal("data/blosics/levels/"+s));
			}
		}
	}
	void delayLoad(){
		try{
			Cb2World.getInstance().clear();
		}catch(Exception ex){
			try{
				Cb2World.getInstance().clear();
			}catch(Exception e){
				
			}
		}
		if(null!=this.main.data)this.main.data.body = null;
		this.group.clear();
		this.surfaces.clear();
		for(SurfaceData data:surfaceWorldReader.surfaces){
			this.surfaces.add(new Cb2TriangleSurfaces(data));
		}
		this.reader.rebuild();
		
		total = 0;
		number = 0;
		
		this.winds.clear();
		for(BodyData bd :this.reader.bodyDatas){
			if(bd.res.equals("wind")){
				BoxData windBox = BoxData.class.cast(bd);
				ParticleEmitter wind = new ParticleEmitter(this.emitter);
				if(bd.angle == 90){
					wind.getSpawnWidth().setActive(true);
					wind.getSpawnWidth().setLow(windBox.height,windBox.height);
					wind.getSpawnWidth().setHigh(windBox.height,windBox.height);
					wind.getSpawnHeight().setActive(true);
					wind.getSpawnHeight().setHigh(windBox.width,windBox.width);
					wind.getSpawnHeight().setLow(windBox.width,windBox.width);
					wind.setPosition(windBox.center.x, windBox.center.y-windBox.width/2);
					wind.getGravity().setActive(true);
					wind.getGravity().setHigh(300, 300);
					wind.getWind().setActive(false);
					wind.getWind().setHigh(100, 100);
				}else if(bd.angle == -90){
					wind.getSpawnWidth().setActive(true);					
					wind.getSpawnWidth().setLow(windBox.height,windBox.height);
					wind.getSpawnWidth().setHigh(windBox.height,windBox.height);
					wind.getSpawnHeight().setActive(true);
					wind.getSpawnHeight().setLow(windBox.width,windBox.width);
					wind.getSpawnHeight().setHigh(windBox.width,windBox.width);
					wind.setPosition(windBox.center.x, windBox.center.y-windBox.height/2);
					wind.getGravity().setActive(true);
					wind.getGravity().setHigh(-300, -300);
					wind.getWind().setActive(false);
					wind.getWind().setHigh(100, 100);
				}else if(bd.angle == 0){
					wind.getSpawnWidth().setActive(true);
					wind.getSpawnWidth().setLow(windBox.width,windBox.width);
					wind.getSpawnWidth().setHigh(windBox.width,windBox.width);
					wind.getSpawnHeight().setActive(true);
					wind.getSpawnHeight().setLow(windBox.height,windBox.height);
					wind.getSpawnHeight().setHigh(windBox.height,windBox.height);
					wind.setPosition(windBox.center.x, windBox.center.y);
					wind.getGravity().setActive(false);
					wind.getGravity().setHigh(-500, -500);
					wind.getWind().setActive(true);
					wind.getWind().setHigh(300, 300);
				}else if(bd.angle == 180){
					wind.getSpawnWidth().setActive(true);
					wind.getSpawnWidth().setLow(windBox.width,windBox.width);
					wind.getSpawnWidth().setHigh(windBox.width,windBox.width);
					wind.getSpawnHeight().setActive(true);
					wind.getSpawnHeight().setLow(windBox.height,windBox.height);
					wind.getSpawnHeight().setHigh(windBox.height,windBox.height);
					wind.setPosition(windBox.center.x, windBox.center.y);
					wind.getGravity().setActive(false);
					wind.getGravity().setHigh(-500, -500);
					wind.getWind().setActive(true);
					wind.getWind().setHigh(-300, -300);
				}
				this.winds.add(wind);

			}
			if(bd.res.contains("block")){
				total++;
			}
			
			TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
			if(bd.res.equals("pet")){
				group.add(new PetObject(bd, new PetAnimation(wd.findRegion(bd.res)),this));
			}else if(
					bd.res.equals("block")||
					bd.res.equals("block-red")||
					bd.res.equals("block-blue")||
					bd.res.equals("block-gray")||
					bd.res.equals("block-yellow")){
				group.add(new BlockObject(bd, new BlockAnimation(wd.findRegion(bd.res)),this));
			}else if(
					bd.res.equals("x-block")||
					bd.res.equals("x-block-red")||
					bd.res.equals("x-block-blue")||
					bd.res.equals("x-block-gray")||
					bd.res.equals("x-block-yellow")){
				group.add(new BlockObject(bd, new XBlockAnimation(wd.findRegion(bd.res)),this));
			}else{
				group.add(new Cb2Object(bd, new AdvanceSprite(wd.findRegion(bd.res)),new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {	
					}
					@Override
					public void after(Cb2Object obj) {	
						obj.data.body.setSleepingAllowed(true);
						obj.data.body.setAwake(false);
					}
				}));
			}
			
		}
		
		total -= this.winleave;
		
		this.group.add(this.main);
		worldBuilt = true;
		
		this.hud.needLabel.setText(""+this.total);
		this.hud.currentNeedLabel.setText(""+0);
	}
	@Override
	public void update(float delta) {
		Cb2World.getInstance().update(delta);
		
		if(!worldBuilt && this.level!=-1){
			if(delay>0.5f){
				delayLoad();
			}
			delay += delta;
		}
		if(worldBuilt && !win){
			if(number>=total){
				win = true;
				Engine.getSoundManager().playSound("Blosics_win");
				this.hud.showDialog();
			}
		}
	}

	@Override
	public void render(float delta) {
		Engine.getSpriteBatch().begin();
		bgSprite.render(delta);
		rbg.act(delta);
		rbg.draw(Engine.getSpriteBatch(), 1);
		emitter.draw(Engine.getSpriteBatch(), delta);
		for(ParticleEmitter wind:winds){
			wind.draw(Engine.getSpriteBatch(),delta);
		}
//		Engine.getSpriteBatch().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
//		Engine.getSpriteBatch().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		if(worldBuilt){
			group.render(delta);
			control.render(delta);
			if(touchDown){
				for(AdvanceSprite arrow:arrows){
					arrow.render(delta);
				}
				mMain.render(delta);
				currentPoint = (int)(mMain.getWidth()-4)/2;
				
				if(currentPoint<20){
					if(prePoint!=currentPoint){
						prePoint = currentPoint;
						int p = LevelTools.getPoints()-1;
						if(p<=0){
							p = 0;
						}
						LevelTools.savePoints(p);
//						this.hud.points_label.setText(p+"");
					}else{
						if(fireDelta>0.125f){
							fireDelta = 0;
							mMain.setSize(mMain.getWidth()+1, mMain.getHeight()+1);
							mMain.setOrigin(mMain.getWidth()/2, mMain.getHeight()/2);
							mMain.setPosition(firstPoint.x-mMain.getWidth()/2, firstPoint.y-mMain.getHeight()/2);
						}else{
							fireDelta+=delta;
						}
					}
				}
				
				final BitmapFont font = Engine.getDefaultFont();
				font.setColor(Color.BLUE);
				font.draw(Engine.getSpriteBatch(), ""+currentPoint, mMain.getX()+mMain.getWidth()/2,mMain.getY()+mMain.getHeight()+20 );
			}
		}
		Engine.getSpriteBatch().end();
		for(final Cb2TriangleSurfaces surface:surfaces){
			surface.render(delta);
		}

		hud.act(delta);
		hud.draw();
		
	}

	@Override
	public void show() {
//		this.hud.points_label.setText(LevelTools.getPoints()+"");
//		this.hud.showHelpGame();
		((Hitme)Engine.get()).ad.show();
	}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		InputMultiplexer mul = new InputMultiplexer();
		mul.addProcessor(hud);
		mul.addProcessor(this);
		return mul;
	}
	
	boolean pointInControl(Vector2 point){
		return point.sub(this.control.getX()+this.control.getWidth()/2,this.control.getY()+this.control.getHeight()/2).len()<this.control.getWidth()/2;
	}
	
	int currentPoint = 0;
	int prePoint = -1;
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(touchDown){
			Engine.getSoundManager().stopSound("Blosics_touchdown");
			final Vector2 v = Engine.screenToWorld(x, y).sub(firstPoint);
			final float angle = v.angle();
			final float len = v.len()/arrows.length;
			final float lenX = len*MathUtils.cosDeg(angle);
			final float lenY = len*MathUtils.sinDeg(angle);
			Engine.getSoundManager().playSound("Blosics_Scroll");
//			this.main.object = mMain;
			this.main.object = new AdvanceSprite(mMain);
			this.main.spawn();
			this.main.setPosition(firstPoint);
			this.main.data.body.setLinearVelocity(-lenX, -lenY);
			this.fireTime ++;
			this.hud.fireTime.setText(fireTime+"");
			
			int p = LevelTools.getPoints();
			if(p<=0){
				p = 0;
//				hud.showGetMore();
			}
			
		}
		touchDown = false;
		return super.touchUp(x, y, pointer, button);
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		final Vector2 v = Engine.screenToWorld(x, y).sub(firstPoint);
		float angle = v.angle();
		float len = v.len();
		if(len>150)len=150;
		float lenX = len/arrows.length*MathUtils.cosDeg(angle);
		float lenY = len/arrows.length*MathUtils.sinDeg(angle);
		for(int i=0;i<arrows.length;i++){
			AdvanceSprite arrow = arrows[i];
			arrow.setPosition(firstPoint.x-arrow.getWidth()/2-lenX*i, firstPoint.y-arrow.getHeight()/2-lenY*i);
			arrow.setRotation(angle);
		}
		return super.touchDragged(x, y, pointer);
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
//		this.hud.hideHelpGame();
		if(pointInControl(Engine.screenToWorld(x, y))){
//			if(LevelTools.getPoints()<=0){
//				hud.showGetMore();
				Engine.getSoundManager().playSound("Blosics_nopoints");
//			}else{
				Engine.getSoundManager().playSound("Blosics_touchdown");
				firstPoint.set(Engine.screenToWorld(x, y));
				mMain.setSize(8, 8);
				mMain.setOrigin(4, 4);
				mMain.setPosition(firstPoint.x-mMain.getWidth()/2, firstPoint.y-mMain.getHeight()/2);
				for(int i=0;i<arrows.length;i++){
					AdvanceSprite arrow = arrows[i];
					arrow.setPosition(firstPoint.x-arrow.getWidth()/2, firstPoint.y-arrow.getHeight()/2);
					arrow.setRotation(180);
				}
				touchDown = true;
				fireDelta = 0;
				currentPoint = 0;
				prePoint = -1;
//			}
		}
		return super.touchDown(x, y, pointer, button);
	}
	@Override
	public boolean keyDown(int keycode) {

		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				engineDrive.gotoLevelScene();
			}
		} else {
			if (keycode == Keys.DEL) {
				engineDrive.gotoLevelScene();
			}
		}

		return super.keyDown(keycode);
	}
}
