package si.vilfa.junglechronicles.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.vilfa.junglechronicles.JungleChronicles;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.desktop
 **/
public class DesktopLauncher
{
	private static final int DESKTOP_DEFAULT_WINDOW_SIZE_X = 1280;
	private static final int DESKTOP_DEFAULT_WINDOW_SIZE_Y = 720;

	public static void main(String[] arg)
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("The Jungle Chronicles");
		config.setWindowedMode(DesktopLauncher.getDesktopDefaultWindowSizeX(),
		                       DesktopLauncher.getDesktopDefaultWindowSizeY());
		config.setInitialVisible(true);
		config.useVsync(true);
		new Lwjgl3Application(new JungleChronicles(), config);
	}

	public static int getDesktopDefaultWindowSizeX()
	{
		return DESKTOP_DEFAULT_WINDOW_SIZE_X;
	}

	public static int getDesktopDefaultWindowSizeY()
	{
		return DESKTOP_DEFAULT_WINDOW_SIZE_Y;
	}
}
