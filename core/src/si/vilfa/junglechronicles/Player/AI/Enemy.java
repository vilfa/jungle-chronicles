package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.Agents.StateAgent;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public class Enemy extends StateAgent
{
    public Enemy(Body body)
    {
        super(body);
    }
}
