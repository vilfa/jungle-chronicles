package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class GoldCollectible extends GameCollectible
{
	public GoldCollectible(Vector2 position, Vector2 velocity, float mass, BoundingBox boundingBox)
	{
		super(position, velocity, mass, boundingBox);
	}

	@Override
	public void update(GameTime gameTime)
	{

	}

	@Override
	public void dispose()
	{
	}
}
