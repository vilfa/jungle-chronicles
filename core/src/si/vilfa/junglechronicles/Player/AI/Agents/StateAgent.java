package si.vilfa.junglechronicles.Player.AI.Agents;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;

/**
 * @author luka
 * @date 11/12/2021
 * @package si.vilfa.junglechronicles.Player.AI.Agents
 **/
public abstract class StateAgent extends AiPlayer
{
    public StateAgent(Body body)
    {
        super(body);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void dispose() { }
}
