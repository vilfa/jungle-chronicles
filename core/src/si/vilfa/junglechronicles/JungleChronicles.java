package si.vilfa.junglechronicles;

import com.badlogic.gdx.ApplicationAdapter;
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
		this.gameplay = new Gameplay();
		this.batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}
}
