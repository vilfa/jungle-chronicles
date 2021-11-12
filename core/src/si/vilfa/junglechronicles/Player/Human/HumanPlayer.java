package si.vilfa.junglechronicles.Player.Human;

import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Graphics.GameTime;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public abstract class HumanPlayer extends GameComponent
{
	@Override
	public abstract void update(GameTime gameTime);
}
