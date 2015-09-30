package info.u250.lycying.hitme.scenes;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.C2dStage;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.utils.UiUtils;
import info.u250.lycying.hitme.EngineDriveInstance;
import info.u250.lycying.hitme.Hitme;
import info.u250.lycying.hitme.LevelTools;
import info.u250.lycying.hitme.scenes.blosics.arts.BlockAnimation;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SceneBlosicsMain extends C2dStage implements Scene {
	EngineDriveInstance engineDrive;
	ParallaxGroup rbg ;
	AdvanceSprite bg ;
	BlockAnimation bigFace;
	BlockAnimation midFace;
	public SceneBlosicsMain(final EngineDriveInstance engineDrive){
		this.engineDrive = engineDrive;
		bg = Engine.resource("BG");
		this.rbg = Engine.resource("RBG");
		
		final TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);

		Image title = new Image(wd.findRegion("title"));
		UiUtils.centerActor(title);
		title.setY(350);
		title.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f,1f),Actions.fadeIn(1))));
		Image play = new Image(wd.findRegion("play"));
		UiUtils.centerActor(play);
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				engineDrive.gotoLevelScene();
				super.clicked(event, x, y);
			}
		});
		this.addActor(play);
		this.addActor(title);
		
		bigFace = new BlockAnimation(wd.findRegion("block-yellow"));
		bigFace.setRotation(45);
		bigFace.setPosition(690, -20);
		bigFace.animSetup();
		Timeline.createSequence()
		.push(Tween.to(bigFace, SpriteAccessor.RGB, 2000f).target(0.2f,0.5f,0.3f))
		.repeatYoyo(-1, 0)
		.start(Engine.getTweenManager());
		
		midFace = new BlockAnimation(wd.findRegion("block"));
		midFace.setSize(64, 64);
		midFace.setOrigin(32, 32);
		midFace.setPosition(200, 380);
		midFace.animSetup();
		
		Timeline.createSequence()
		.push(Tween.to(midFace, SpriteAccessor.ROTATION, 1000).target(360))
		.repeat(-1, 0)
		.start(Engine.getTweenManager());
		
		Image moreComing = new Image(wd.findRegion("morelevels"));
		this.addActor(moreComing);
		
		
		
		final TextureRegionDrawable sound_on_region = new TextureRegionDrawable( wd.findRegion("menu-sound-on"));
		final TextureRegionDrawable sound_off_region = new TextureRegionDrawable(wd.findRegion("menu-sound-off"));
		final Image menu_sound = new Image(sound_on_region);
		menu_sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(menu_sound.getDrawable()==sound_on_region){
					menu_sound.setDrawable(sound_off_region);
					LevelTools.disableSound();
				}else{
					menu_sound.setDrawable(sound_on_region);
					LevelTools.enableSound();
				}
				super.clicked(event, x, y);
			}
		});
		
		final TextureRegionDrawable music_on_region =new TextureRegionDrawable( wd.findRegion("menu-music-on"));
		final TextureRegionDrawable music_off_region = new TextureRegionDrawable(wd.findRegion("menu-music-off"));
		final Image menu_music = new Image(music_on_region);
		menu_music.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(menu_music.getDrawable()==music_on_region){
					menu_music.setDrawable(music_off_region);
					LevelTools.disableMusic();
				}else{
					menu_music.setDrawable(music_on_region);
					LevelTools.enableMusic();
				}
				super.clicked(event, x, y);
			}
		});
		if(!LevelTools.isMusicEnabled()){
			menu_music.setDrawable(music_off_region);
			LevelTools.disableMusic();
		}
		if(!LevelTools.isSoundEnabled()){
			menu_sound.setDrawable(sound_off_region);
			LevelTools.disableSound();
		}
		menu_sound.setPosition(30, 300);
		menu_music.setPosition(30, 250);
		this.addActor(menu_music);
		this.addActor(menu_sound);
	}
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(float delta) {
		Engine.getSpriteBatch().begin();
		bg.render(delta);
		rbg.act(delta);
		rbg.draw(Engine.getSpriteBatch(), 1);
		bigFace.render(delta);
		midFace.render(delta);
		Engine.getSpriteBatch().end();
		this.act(delta);
		this.draw();
	}

	@Override
	public void show() {
		((Hitme)Engine.get()).ad.hide();
	}

	@Override
	public void hide() {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}
	@Override
	public boolean keyDown(int keycode) {

		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				System.exit(0);
			}
		} else {
			if (keycode == Keys.DEL) {
				//do nothing
			}
		}

		return super.keyDown(keycode);
	}
}
