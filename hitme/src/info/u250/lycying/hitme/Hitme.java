package info.u250.lycying.hitme;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.load.startup.StartupLoading;
import info.u250.c2d.graphic.AdControl;

import java.util.Random;

public class Hitme extends Engine {
	public AdControl ad ;
	public Hitme(AdControl ad){
		this.ad = ad;
	}
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineDriveInstance();
	}
	public final static Random random = new Random();
	
	@Override
	protected StartupLoading getStartupLoading() {
		return new LoadDing();
	}
}
