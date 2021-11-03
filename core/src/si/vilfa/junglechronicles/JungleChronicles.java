package si.vilfa.junglechronicles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles
 **/
public class JungleChronicles extends ApplicationAdapter
{
	SpriteBatch batch;
	ArrayList<Texture> world, player;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		world = new ArrayList<>();
		player = new ArrayList<>();
		world.add(new Texture("World@1x/WorldForestBackground@1x.png"));
		world.add(new Texture("World@1x/WorldForestTreetops@1x.png"));
		world.add(new Texture("World@1x/WorldForestDay@1x.png"));
		world.add(new Texture("World@1x/WorldForestSunlightA@1x.png"));
		world.add(new Texture("World@1x/WorldForestSunlightB@1x.png"));
		world.add(new Texture("World@1x/WorldGroundGrass@1x.png"));
		world.add(new Texture("World@1x/WorldGroundBushes@1x.png"));
		player.add(new Texture("128/Player/PlayerR1@128x128.png"));
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		for (Texture t : world)
		{
			batch.draw(t, 0, 0);
		}
		for (Texture t : player)
		{
			batch.draw(t, 0, 0);
		}
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		for (Texture t : world)
		{
			t.dispose();
		}
		for (Texture t : player)
		{
			t.dispose();
		}
	}
}
