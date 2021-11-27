package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GameTime implements TimeProvider
{
    private final long startGameTime;

    public GameTime()
    {
        this.startGameTime = TimeUtils.millis();
    }

    @Override
    public long getElapsedGameTimeMillis()
    {
        return TimeUtils.timeSinceMillis(startGameTime);
    }

    @Override
    public long getElapsedGameTimeSeconds()
    {
        return TimeUtils.timeSinceMillis(startGameTime) / 1000;
    }

    @Override
    public long getStartGameTimeMillis()
    {
        return startGameTime;
    }

    @Override
    public long getStartGameTimeSeconds()
    {
        return startGameTime / 1000;
    }

    @Override
    public float getDeltaTime()
    {
        return Gdx.graphics.getDeltaTime();
    }
}
