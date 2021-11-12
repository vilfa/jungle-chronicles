package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Graphics.GameTime;
import si.vilfa.junglechronicles.Scene.SimpleScene;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public class Level extends GameLevel
{
	public Level()
	{
		super(new SimpleScene(), new Vector2(0, 0));
	}

	public Level(SimpleScene scene, Vector2 playerSpawn)
	{
		super(scene, playerSpawn);
	}

	@Override
	public void update(GameTime gameTime)
	{

	}

	@Override
	public void dispose()
	{
		scene.dispose();
	}
}
