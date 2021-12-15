package si.vilfa.junglechronicles;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import si.vilfa.junglechronicles.Gameplay.Gameplay;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles
 **/
public class JungleChronicles extends ApplicationAdapter
{
    Gameplay gameplay;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        gameplay = new Gameplay();
        gameplay.create();
    }

    @Override
    public void resize(int width, int height)
    {
        gameplay.resize(width, height);
    }

    @Override
    public void render()
    {
        gameplay.update();
        gameplay.draw();
    }

    @Override
    public void pause()
    {
        gameplay.pause();
    }

    @Override
    public void resume()
    {
        gameplay.resume();
    }

    @Override
    public void dispose()
    {
        gameplay.dispose();
    }
}
