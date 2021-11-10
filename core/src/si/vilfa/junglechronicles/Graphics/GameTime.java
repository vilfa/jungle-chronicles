package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GameTime implements ITimeProvider
{
	private final long startGameTime;

	public GameTime()
	{
		this.startGameTime = TimeUtils.millis();
	}

	@Override
	public long getElapsedGameTimeMillis()
	{
		return TimeUtils.timeSinceMillis(this.startGameTime);
	}

	@Override
	public long getElapsedGameTimeSeconds()
	{
		return TimeUtils.timeSinceMillis(this.startGameTime) / 1000;
	}

	@Override
	public long getStartGameTimeMillis()
	{
		return this.startGameTime;
	}

	@Override
	public long getStartGameTimeSeconds()
	{
		return this.startGameTime / 1000;
	}

	@Override
	public float getDeltaTime()
	{
		return Gdx.graphics.getDeltaTime();
	}
}
