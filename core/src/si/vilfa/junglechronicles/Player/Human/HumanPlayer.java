package si.vilfa.junglechronicles.Player.Human;

import si.vilfa.junglechronicles.Component.GameComponent;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public abstract class HumanPlayer extends GameComponent
{
	public HumanPlayer()
	{
		this.initialize(0, true);
	}

	@Override
	public abstract void update();

	@Override
	public abstract void dispose();
}
