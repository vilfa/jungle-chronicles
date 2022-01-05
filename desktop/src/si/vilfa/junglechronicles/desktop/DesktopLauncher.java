package si.vilfa.junglechronicles.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.vilfa.junglechronicles.Config.Config;
import si.vilfa.junglechronicles.JungleChronicles;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.desktop
 **/
public class DesktopLauncher
{
    private static final Config.Pair<Integer> DEFAULT_RESOLUTION = Config.RESOLUTIONS.first();

    public static void main(String[] arg)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("The Jungle Chronicles");
        config.setWindowedMode(DEFAULT_RESOLUTION.one, DEFAULT_RESOLUTION.two);
        config.setInitialVisible(true);
        config.setResizable(false);
        new Lwjgl3Application(new JungleChronicles(), config);
    }
}
