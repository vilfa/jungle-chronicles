package si.vilfa.junglechronicles.Graphics;

import si.vilfa.junglechronicles.Config.Config;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public interface WindowAdapter
{
    void resize(int width, int height);

    float getScreenAspectRatio();

    int getScreenRefreshRate();

    Config.Pair<Integer> getWindowResolution();

    void setWindowResolution(Config.Pair<Integer> windowResolution);
}
