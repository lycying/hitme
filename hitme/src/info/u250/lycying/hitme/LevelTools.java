package info.u250.lycying.hitme;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Preferences;

public class LevelTools {
	private static Preferences preferences;
	
	public static boolean isMusicEnabled(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		if(null==preferences.getString("__audio__") || "".equals(preferences.getString("__audio__"))){
			return true;
		}
		return preferences.getString("__audio__").equals("lxlol");
	}
	public static void enableMusic(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		preferences.putString("__audio__", "lxlol");
		preferences.flush();
		
		Engine.getMusicManager().setVolume(1);
	}
	public static void disableMusic(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		preferences.putString("__audio__", "ahahahahaha");
		preferences.flush();
		
		Engine.getMusicManager().setVolume(0);
	}
	
	
	public static boolean isSoundEnabled(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		if(null==preferences.getString("__sound__") || "".equals(preferences.getString("__sound__"))){
			return true;
		}
		return preferences.getString("__sound__").equals("lxlol");
	}
	public static void enableSound(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		preferences.putString("__sound__", "lxlol");
		preferences.flush();
		
		Engine.getSoundManager().setVolume(1);
	}
	public static void disableSound(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		preferences.putString("__sound__", "ahahahahaha");
		preferences.flush();
		
		Engine.getSoundManager().setVolume(0);
	}
	
	public static int getPoints(){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		Object o = preferences.getString("__points__");
		if(null==o||"".equals(o)){
			return 40;
		}
		return Integer.parseInt(o+"");
	}
	public static void savePoints(int points){
		if(null == preferences){
			preferences = Engine.getPreferences();
		}
		preferences.putString("__points__", ""+points);
		preferences.flush();
	}
}
