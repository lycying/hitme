package info.u250.lycying.hitme.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.C2dStage;
import info.u250.c2d.utils.UiUtils;
import info.u250.lycying.hitme.EngineDriveInstance;
import info.u250.lycying.hitme.Hitme;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class SceneLevel extends C2dStage implements Scene{
	EngineDriveInstance engineDrive;
	Table levelContainer;
	AdvanceSprite bg ;
	public SceneLevel(final EngineDriveInstance engineDrive){
		this.engineDrive = engineDrive;
//		bg = new SimpleMeshBackground(new Color(0.1f,0.1f,0.1f,1),new Color(17f/255f,196f/255f,1,1));
		bg = Engine.resource("BG");
		levelContainer = new Table();
		levelContainer.pad(20);
		levelContainer.setSize(Engine.getWidth(), Engine.getHeight());
		UiUtils.centerActor(levelContainer);
		addActor(levelContainer);
		
		TextureAtlas wd = Engine.resource("BL");
		
		Table table = new Table();

		ScrollPane scroll = new ScrollPane(table);
		
		levelContainer.add(scroll).expand().fill();

		
		
		NinePatchDrawable bup = new NinePatchDrawable(new NinePatch(wd.findRegion("level")));
		NinePatchDrawable bdown =new NinePatchDrawable( new NinePatch(wd.findRegion("level")));
		NinePatchDrawable bchecked = new NinePatchDrawable(new NinePatch(wd.findRegion("level")));
		for (int i = 0; i < 38; i++) {
			if(i%8==0){
				table.row();
			}

			TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(bdown, bup, bchecked);
			style.font = Engine.resource("GameFont");
			TextButton button = new TextButton((i+1)+"",style );
			table.add(button);
			
			final int this_level = i+1;
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Engine.getSoundManager().playSound("Blosics_btn");
					engineDrive.gotoBlosicsScene(this_level);
					super.clicked(event, x, y);
				}
			});
			
		}
		table.padBottom(70);
		levelContainer.row();
		Image choose_level = new Image(wd.findRegion("choose-level"));
		choose_level.setX(200);
		this.addActor(choose_level);
		
		Image back = new Image(wd.findRegion("back"));
		back.setPosition(20, 20);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				engineDrive.gotoSceneBlosicsMain();
				super.clicked(event, x, y);
			}
		});
		this.addActor(back);
	}
	@Override
	public void update(float delta) {		
	}

	@Override
	public void render(float delta) {
		Engine.getSpriteBatch().begin();
		bg.render(delta);
		Engine.getSpriteBatch().end();
		this.act(delta);
		this.draw();
	}
	
	@Override
	public boolean keyDown(int keycode) {

		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				engineDrive.gotoSceneBlosicsMain();
			}
		} else {
			if (keycode == Keys.DEL) {
				engineDrive.gotoSceneBlosicsMain();
			}
		}

		return super.keyDown(keycode);
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
}
