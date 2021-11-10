package si.vilfa.junglechronicles.Player.AI;

import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public abstract class AIPlayer extends DrawableGameComponent
{
	@Override
	public abstract void draw(GameTime gameTime);

	@Override
	public abstract void update(GameTime gameTime);
}
