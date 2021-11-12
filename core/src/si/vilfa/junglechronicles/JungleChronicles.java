package si.vilfa.junglechronicles;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import si.vilfa.junglechronicles.Gameplay.Gameplay;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles
 **/
public class JungleChronicles extends ApplicationAdapter
{
	Gameplay gameplay;
	SpriteBatch batch;

	@Override
	public void create()
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		this.gameplay = new Gameplay();
		this.batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
	}

	@Override
	public void dispose()
	{
	}
}
