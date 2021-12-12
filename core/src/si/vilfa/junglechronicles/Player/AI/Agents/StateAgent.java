package si.vilfa.junglechronicles.Player.AI.Agents;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;

/**
 * @author luka
 * @date 11/12/2021
 * @package si.vilfa.junglechronicles.Player.AI.Agents
 **/
public abstract class StateAgent<E, S extends State<E>> extends AiPlayer
{
    protected StateMachine<E, S> stateMachine;

    public StateAgent(Body body)
    {
        super(body);
    }

    public StateMachine<E, S> getStateMachine()
    {
        return stateMachine;
    }

    public void setStateMachine(StateMachine<E, S> stateMachine)
    {
        this.stateMachine = stateMachine;
    }

    @Override
    public abstract void update();

    @Override
    public void dispose() { }
}
