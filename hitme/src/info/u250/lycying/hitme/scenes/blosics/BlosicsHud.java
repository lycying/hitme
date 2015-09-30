package info.u250.lycying.hitme.scenes.blosics;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.C2dStage;
import info.u250.c2d.utils.UiUtils;
import info.u250.lycying.hitme.scenes.SceneBlosics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BlosicsHud extends C2dStage {
	SceneBlosics blosics;	
	Group menuGroup;
	Group dialogGroup;
	Image black ;
	final public Label levelLabel ;
	final public Label currentNeedLabel ;
	final public Label needLabel ;
	final public Label fireTime ;
//	final public Label points_label;
//	final public Image getmore ;
//	final public Image help_game;
	public BlosicsHud(SceneBlosics blosicsx){
		this.blosics = blosicsx;
		TextureAtlas wd = Engine.resource("BL",TextureAtlas.class);
		Image pause = new Image(wd.findRegion("pause"));
		pause.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				showMenu();
			};
		});
		
		final BitmapFont font = Engine.resource("GameFont");
		levelLabel = new Label("1", new LabelStyle(font,Color.YELLOW));
		currentNeedLabel = new Label("23", new LabelStyle(font,Color.YELLOW));
		needLabel = new Label("23", new LabelStyle(font,Color.YELLOW));
		fireTime = new Label("0", new LabelStyle(font,Color.MAGENTA));
		
		
		black = new Image(wd.findRegion("black"));
		black.setSize(this.getWidth(), this.getHeight());
		menuGroup = new Group();
		menuGroup.setPosition(30, 30);
		menuGroup.addActor(new Image(wd.findRegion("menu-bg")));
		Image menu_restart = new Image(wd.findRegion("menu-restart"));
		menu_restart.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				blosics.restart();
				hideMenu();
			};
		});
		Image menu_menu = new Image(wd.findRegion("menu-menu"));
		menu_menu.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				blosics.engineDrive.gotoLevelScene();
				hideMenu();
			};
		});
		Image menu_start = new Image(wd.findRegion("menu-start"));
		menu_start.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				hideMenu();
			};
		});
		menu_start.setPosition(45, 100);
		menu_restart.setPosition(45, 150);
		menu_menu.setPosition(45, 50);

		menuGroup.setSize(267, 237);
		menuGroup.addActor(menu_restart);
		menuGroup.addActor(menu_menu);
		menuGroup.addActor(menu_start);

		
		
		Image dialog_restart = new Image(wd.findRegion("dialog-restart"));
		dialog_restart.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				blosics.restart();
				hideDialog();
			};
		});
		Image dialog_menu = new Image(wd.findRegion("dialog-menu"));
		dialog_menu.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				blosics.engineDrive.gotoLevelScene();
				hideDialog();
			};
		});
		Image dialog_next = new Image(wd.findRegion("dialog-next"));
		dialog_next.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				blosics.nextlevel();
				hideDialog();
			};
		});
		dialog_restart.setPosition(30, -50);
		dialog_menu.setPosition((535f-dialog_menu.getWidth())/2, 0);
		dialog_next.setPosition((535f-dialog_next.getWidth()-30),-50);
		dialogGroup = new Group();
		dialogGroup.setSize(535, 455);
		dialogGroup.addActor(new Image(wd.findRegion("dialog-bg")));
		dialogGroup.addActor(dialog_restart);
		dialogGroup.addActor(dialog_menu);
		dialogGroup.addActor(dialog_next);
		dialogGroup.setOrigin(535f/2, 0);
		UiUtils.centerActor(dialogGroup);
		dialogGroup.setY(0);
		
		
		
		Table labelTable = new Table();
		labelTable.add(pause);
//		labelTable.add(menu_music);
//		labelTable.add(menu_sound);
		labelTable.add(new Label("   Level:", new LabelStyle(font,Color.WHITE)));
		labelTable.add(levelLabel);
		labelTable.add(new Label("   Point:", new LabelStyle(font,Color.WHITE)));
		labelTable.add(currentNeedLabel);
		labelTable.add(new Label("/", new LabelStyle(font,Color.RED)));
		labelTable.add(needLabel);
		labelTable.add(new Label("   Fire:", new LabelStyle(font,Color.WHITE)));
		labelTable.add(fireTime);
		labelTable.setBackground(new NinePatchDrawable(new  NinePatch(wd.findRegion("black"))));
		labelTable.add(new Label("                  ", new LabelStyle(font,Color.WHITE)));
		labelTable.pack();
		this.addActor(labelTable);

	}
	public void showDialog(){
		this.addActor(this.black);
		this.addActor(dialogGroup);
		dialogGroup.setScale(0);
		dialogGroup.addAction(Actions.scaleTo(1, 1,0.3f,Interpolation.swingOut));
	}
	public void hideDialog(){
		dialogGroup.addAction(Actions.sequence(Actions.scaleTo(0, 0,0.15f),Actions.run(new Runnable() {
			
			@Override
			public void run() {
				dialogGroup.remove();
				black.remove();
			}
		})));
		
	}
	void hideMenu(){
		this.menuGroup.clearActions();
		menuGroup.addAction(Actions.sequence(Actions.scaleTo(0, 0,0.15f),Actions.run(new Runnable() {
			
			@Override
			public void run() {
				menuGroup.remove();
				black.remove();
			}
		})));	
	}
	void showMenu(){
		this.addActor(menuGroup);
		menuGroup.clearActions();
		menuGroup.setScale(0);
		menuGroup.addAction(Actions.scaleTo(1, 1,0.3f,Interpolation.swingOut));
	}
}
