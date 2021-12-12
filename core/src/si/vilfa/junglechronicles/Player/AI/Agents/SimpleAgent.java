package si.vilfa.junglechronicles.Player.AI.Agents;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;

/**
 * @author luka
 * @date 11/12/2021
 * @package si.vilfa.junglechronicles.Player.AI.Agents
 **/
public abstract class SimpleAgent extends AiPlayer
{
    protected final SteeringAcceleration<Vector2> steeringOutput;

    public SimpleAgent(Body body)
    {
        super(body);
        steeringOutput = new SteeringAcceleration<>(new Vector2());
    }

    protected abstract void applySteering();

    @Override
    public void update()
    {
        steeringBehavior.calculateSteering(steeringOutput);
        applySteering();
    }

    @Override
    public void dispose() { }
}
