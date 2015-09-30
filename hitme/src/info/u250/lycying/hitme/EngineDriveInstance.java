package info.u250.lycying.hitme;

import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.lycying.hitme.scenes.SceneBlosics;
import info.u250.lycying.hitme.scenes.SceneBlosicsMain;
import info.u250.lycying.hitme.scenes.SceneLevel;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class EngineDriveInstance implements EngineDrive {

	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{
				"data/blosics/blosics.atlas",
				"data/blosics/music/",
				"data/blosics/sound/",
				"data/blosics/textures/",
				"data/fonts"
				}, 800, 480);
		opt.useGL20 = false;
		opt.autoResume = true;
		opt.debug = false;
		opt.configFile = "info.u250.hitme.conf";
		opt.catchBackKey = true;
		return opt;
	}

	SceneBlosicsMain sceneBlosicsMain; 
	SceneBlosics sceneBlosics;
	SceneLevel sceneLevel;
	@Override
	public void onLoadedResourcesCompleted() {
		sceneLevel = new SceneLevel(this);
		sceneBlosics = new SceneBlosics(this);
		sceneBlosicsMain = new SceneBlosicsMain(this);
		Engine.setMainScene(sceneBlosicsMain);
		Engine.getMusicManager().playMusic("Blosics_bg_music", true);
	}
	public void gotoSceneBlosicsMain(){
		Engine.setMainScene(sceneBlosicsMain,TransitionType.Fade,300);
	}
	public void gotoLevelScene(){
		Engine.setMainScene(sceneLevel,TransitionType.Fade,300);
	}

	public void gotoBlosicsScene(int level){
		sceneBlosics.load(level);
		Engine.setMainScene(sceneBlosics,TransitionType.Fade,300);
	}
	public void gotoBlosicsScene(){
		Engine.setMainScene(sceneBlosics,TransitionType.Fade,300);
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.font("GameFont", "data/fonts/purisa.fnt");
		
		reg.texture("DirtGrass.png", "data/blosics/textures/DirtGrass.png");
		reg.texture("RockLayered.jpg", "data/blosics/textures/RockLayered.jpg");
		reg.texture("DSRT.png", "data/blosics/textures/DSRT.png");
		reg.texture("S.png", "data/blosics/textures/S.png");
		reg.texture("LoamWalls.jpg", "data/blosics/textures/LoamWalls.jpg");
		
		reg.textureAtlas("BL", "data/blosics/blosics.atlas");
		
		reg.music("Blosics_bg_music", "data/blosics/music/bg.ogg");
		
		reg.sound("Blosics_puzzle_swap", "data/blosics/sound/puzzle-swap.wav");
		reg.sound("Blosics_puzzle_coin", "data/blosics/sound/puzzle-coin.wav");
		reg.sound("Blosics_puzzle_hit", "data/blosics/sound/puzzle-hit.wav");
		reg.sound("Blosics_puzzle_clear1", "data/blosics/sound/coinpickup1.ogg");
		reg.sound("Blosics_puzzle_clear2", "data/blosics/sound/coinpickup2.ogg");
		reg.sound("Blosics_puzzle_clear3", "data/blosics/sound/coinpickup3.ogg");
		reg.sound("Blosics_puzzle_clear4", "data/blosics/sound/coinpickup4.ogg");
		reg.sound("Blosics_puzzle_clear5", "data/blosics/sound/coinpickup5.ogg");
		reg.sound("Blosics_puzzle_clear6", "data/blosics/sound/coinpickup6.ogg");
		reg.sound("Blosics_puzzle_clear7", "data/blosics/sound/coinpickup7.ogg");
		reg.sound("Blosics_puzzle_clear8", "data/blosics/sound/coinpickup8.ogg");
		reg.sound("Blosics_puzzle_clear9", "data/blosics/sound/coinpickup9.ogg");
		
		reg.sound("Blosics_win", "data/blosics/sound/win.wav");
		reg.sound("Blosics_nopoints", "data/blosics/sound/nopoints.wav");
		reg.sound("Blosics_fire1", "data/blosics/sound/shoot1.wav");
		reg.sound("Blosics_fire2", "data/blosics/sound/shoot2.wav");
		reg.sound("Blosics_fire3", "data/blosics/sound/shoot3.wav");
		reg.sound("Blosics_fire4", "data/blosics/sound/shoot4.wav");
		reg.sound("Blosics_fire5", "data/blosics/sound/shoot5.wav");
		reg.sound("Blosics_btn", "data/blosics/sound/btn.wav");
		reg.sound("Blosics_hit_stone", "data/blosics/sound/hit-stone.wav");
		reg.sound("Blosics_hit_platfom", "data/blosics/sound/hit-platform.wav");
		reg.sound("Blosics_dispose", "data/blosics/sound/dispose.wav");
		reg.sound("Blosics_dispose_pet", "data/blosics/sound/dispose-pet.wav");
		reg.sound("Blosics_touchdown", "data/blosics/sound/holder.ogg");
		reg.sound("Blosics_Scroll", "data/blosics/sound/scroll.wav");

		reg.sound("Blosics_normal_1", "data/blosics/sound/klocki_normal_01.wav");
		reg.sound("Blosics_normal_2", "data/blosics/sound/klocki_normal_02.wav");
		reg.sound("Blosics_normal_3", "data/blosics/sound/klocki_normal_03.wav");
		reg.sound("Blosics_normal_4", "data/blosics/sound/klocki_normal_04.wav");
		reg.sound("Blosics_normal_5", "data/blosics/sound/klocki_normal_05.wav");
		reg.sound("Blosics_normal_6", "data/blosics/sound/klocki_normal_06.wav");
		reg.sound("Blosics_normal_7", "data/blosics/sound/klocki_normal_07.wav");
		reg.sound("Blosics_normal_8", "data/blosics/sound/klocki_normal_08.wav");
		reg.sound("Blosics_normal_9", "data/blosics/sound/klocki_normal_09.wav");
		reg.sound("Blosics_normal_10", "data/blosics/sound/klocki_normal_10.wav");
		reg.sound("Blosics_normal_11", "data/blosics/sound/kulki_normal_01.wav");
		reg.sound("Blosics_normal_12", "data/blosics/sound/kulki_normal_02.wav");
		reg.sound("Blosics_normal_13", "data/blosics/sound/kulki_normal_03.wav");
		reg.sound("Blosics_normal_14", "data/blosics/sound/kulki_normal_04.wav");
		reg.sound("Blosics_normal_15", "data/blosics/sound/kulki_normal_05.wav");
		reg.sound("Blosics_normal_16", "data/blosics/sound/kulki_multi_01.wav");
		reg.sound("Blosics_normal_17", "data/blosics/sound/kulki_multi_02.wav");
		reg.sound("Blosics_normal_18", "data/blosics/sound/kulki_multi_03.wav");
		reg.sound("Blosics_normal_19", "data/blosics/sound/kulki_multi_04.wav");
		reg.sound("Blosics_normal_20", "data/blosics/sound/kulki_multi_05.wav");
		reg.sound("Blosics_normal_21", "data/blosics/sound/kulki_push_01.wav");
		reg.sound("Blosics_normal_22", "data/blosics/sound/kulki_push_02.wav");
		reg.sound("Blosics_normal_23", "data/blosics/sound/kulki_push_03.wav");
		reg.sound("Blosics_normal_24", "data/blosics/sound/kulki_push_04.wav");
		reg.sound("Blosics_normal_25", "data/blosics/sound/kulki_push_05.wav");
		reg.sound("Blosics_normal_26", "data/blosics/sound/klocki_normalStinkyAlert_01.wav");
		reg.sound("Blosics_normal_27", "data/blosics/sound/klocki_normalStinkyAlert_02.wav");
		reg.sound("Blosics_normal_28", "data/blosics/sound/klocki_normalStinkyAlert_03.wav");
		reg.sound("Blosics_normal_29", "data/blosics/sound/klocki_normalStinkyAlert_04.wav");
		reg.sound("Blosics_normal_30", "data/blosics/sound/klocki_normalStinkyAlert_05.wav");
		reg.sound("Blosics_normal_31", "data/blosics/sound/kulka_wybuchowaRemote_01.wav");
		reg.sound("Blosics_normal_32", "data/blosics/sound/kulka_wybuchowaRemote_02.wav");
		reg.sound("Blosics_normal_33", "data/blosics/sound/kulka_wybuchowaRemote_03.wav");
		reg.sound("Blosics_normal_34", "data/blosics/sound/kulka_wybuchowaRemote_04.wav");
		reg.sound("Blosics_normal_35", "data/blosics/sound/kulka_wybuchowaRemote_05.wav");
		reg.sound("Blosics_normal_36", "data/blosics/sound/kulki_vortex_01.wav");
		reg.sound("Blosics_normal_37", "data/blosics/sound/kulki_vortex_02.wav");
		reg.sound("Blosics_normal_38", "data/blosics/sound/kulki_vortex_03.wav");
		reg.sound("Blosics_normal_39", "data/blosics/sound/kulki_vortex_04.wav");
		reg.sound("Blosics_normal_40", "data/blosics/sound/kulki_vortex_05.wav");
		
		final TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		final ParallaxGroup rbg = new ParallaxGroup(800, 480, new Vector2(80,0));
		rbg.addActor(new ParallaxLayer(rbg, new Image(wd.createSprite("rbg3")), new Vector2(0.2f,0), new Vector2(0,500),new Vector2(0,0)));
		rbg.addActor(new ParallaxLayer(rbg, new Image(wd.createSprite("rbg2")), new Vector2(1,0), new Vector2(0,500),new Vector2()));
//		rbg.add(new ParallaxLayer("bg4", new SpriteParallaxLayerDrawable(wd.createSprite("rbg4")), new Vector2(1.0f,0), new Vector2(500,500), -1, -1,new Vector2(0,80)));
		reg.object("RBG", rbg);
		
//		SimpleMeshBackground bg  = new SimpleMeshBackground(new Color(0.1f,0.1f,0.1f,1),new Color(17f/255f,196f/255f,1,1));
		AdvanceSprite bg = new AdvanceSprite(wd.findRegion("bg"));
		reg.object("BG", bg);
	}
}
