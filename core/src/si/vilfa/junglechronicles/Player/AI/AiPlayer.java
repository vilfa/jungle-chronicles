package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.Behaviour.IndividualBehaviour;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public abstract class AiPlayer extends Player
        implements Steerable<Vector2>, IndividualBehaviour<Vector2>
{
    public AiPlayer(Body body)
    {
        super(body);
        isTagged = false;
        boundingRadius = 1f;
        zeroLinearSpeedThreshold = 0.1f;
        maxLinearSpeed = 1f;
        maxLinearAcceleration = 1f;
        maxAngularSpeed = 1f;
        maxAngularAcceleration = 1f;
    }

    @Override
    public SteeringBehavior<Vector2> getBehaviour()
    {
        return steeringBehavior;
    }

    @Override
    public void setBehaviour(SteeringBehavior<Vector2> steeringBehavior)
    {
        this.steeringBehavior = steeringBehavior;
        this.steeringBehavior.setLimiter(this);
    }

    @Override
    public Steerable<Vector2> getTarget()
    {
        if (steeringBehavior instanceof Arrive)
        {
            return (Steerable<Vector2>) ((Arrive<Vector2>) steeringBehavior).getTarget();
        } else if (steeringBehavior instanceof Evade)
        {
            return ((Evade<Vector2>) steeringBehavior).getTarget();
        } else if (steeringBehavior instanceof Pursue)
        {
            return ((Pursue<Vector2>) steeringBehavior).getTarget();
        } else if (steeringBehavior instanceof Wander)
        {
            return (Steerable<Vector2>) ((Wander<Vector2>) steeringBehavior).getTarget();
        } else if (steeringBehavior instanceof Face)
        {
            return (Steerable<Vector2>) ((Face<Vector2>) steeringBehavior).getTarget();
        }

        return null;
    }

    @Override
    public void setTarget(Steerable<Vector2> steeringTarget)
    {
        if (steeringBehavior instanceof Arrive)
        {
            ((Arrive<Vector2>) steeringBehavior).setTarget(steeringTarget);
        } else if (steeringBehavior instanceof Evade)
        {
            ((Evade<Vector2>) steeringBehavior).setTarget(steeringTarget);
        } else if (steeringBehavior instanceof Pursue)
        {
            ((Pursue<Vector2>) steeringBehavior).setTarget(steeringTarget);
        } else if (steeringBehavior instanceof Wander)
        {
            ((Wander<Vector2>) steeringBehavior).setTarget(steeringTarget);
        } else if (steeringBehavior instanceof Face)
        {
            ((Face<Vector2>) steeringBehavior).setTarget(steeringTarget);
        }
    }

    @Override
    public abstract void update();

    @Override
    public abstract void dispose();
}
