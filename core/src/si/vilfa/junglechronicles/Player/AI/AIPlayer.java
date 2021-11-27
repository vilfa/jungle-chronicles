package si.vilfa.junglechronicles.Player.AI;

import si.vilfa.junglechronicles.Component.GameComponent;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public abstract class AIPlayer extends GameComponent
{
    public AIPlayer()
    {
        super(0, true);
    }

    @Override
    public abstract void update();
}
