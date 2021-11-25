package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public class MidgroundBlock extends GameBlock
{
	public MidgroundBlock(Vector2 position, Vector2 velocity, float density)
	{
		super(position, velocity, density);
	}

	@Override
	public void update()
	{
		if (!isUpdatable) {return;}
	}

	@Override
	public void dispose()
	{
	}
}
