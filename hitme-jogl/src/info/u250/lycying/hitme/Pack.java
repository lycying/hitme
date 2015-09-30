package info.u250.lycying.hitme;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Pack {
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.maxHeight = 1024;
		settings.maxHeight = 1024;
		

		TexturePacker2.process(settings, 
				"assets-raw/blosics", 
				"../hitme-android/assets/data/blosics","blosics");
	}
}
