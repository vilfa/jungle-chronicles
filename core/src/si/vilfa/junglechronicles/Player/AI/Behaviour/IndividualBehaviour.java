package si.vilfa.junglechronicles.Player.AI.Behaviour;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector;

/**
 * @author luka
 * @date 11/12/2021
 * @package si.vilfa.junglechronicles.Player.AI.Behaviour
 **/
public interface IndividualBehaviour<T extends Vector<T>>
{
    SteeringBehavior<T> getBehaviour();

    void setBehaviour(SteeringBehavior<T> steeringBehavior);

    Steerable<T> getTarget();

    void setTarget(Steerable<T> steeringTarget);
}
