package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Graphics.Renderer;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class TerrainBlock extends GameBlock
{
	public TerrainBlock(Vector2 position, Vector2 velocity, float density)
	{
		super(position, velocity, density);
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}

		if (position.x < 100 || position.x > 800)
		{
			velocity.x *= -1;
		}
		position.mulAdd(velocity, Renderer.gameTime.getDeltaTime());
	}

	@Override
	public void dispose()
	{
	}
}
