package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.Agents.SimpleAgent;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public class Friend extends SimpleAgent
{
    public Friend(Body body)
    {
        super(body);
        body.setGravityScale(0f);
    }

    @Override
    protected void applySteering()
    {
        body.applyLinearImpulse(steeringOutput.linear, body.getLocalCenter(), true);
        body.applyAngularImpulse(steeringOutput.angular, true);
    }
}
