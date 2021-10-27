package si.vilfa.junglechronicles.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.vilfa.junglechronicles.JungleChronicles;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("The Jungle Chronicles");
		new Lwjgl3Application(new JungleChronicles(), config);
	}
}
