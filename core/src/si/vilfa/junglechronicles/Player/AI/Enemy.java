package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Physics.RayCallback;
import si.vilfa.junglechronicles.Player.AI.Agents.StateAgent;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public class Enemy extends StateAgent<Enemy, EnemyState>
{
    private float leftBoundHorizontal;
    private float rightBoundHorizontal;
    private float topBoundVertical;
    private float bottomBoundVertical;

    public Enemy(Body body)
    {
        super(body);
        stateMachine = new DefaultStateMachine<>(this, EnemyState.IDLE);
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        stateMachine.update();

        if (stateMachine.getPreviousState() == stateMachine.getCurrentState()) return;

        if (stateMachine.getCurrentState() == EnemyState.IDLE)
        {
            setVelocity(new Vector2());
        } else if (stateMachine.getCurrentState() == EnemyState.MOVE_LEFT)
        {
            setVelocity(new Vector2(-0.5f, 0f));
        } else if (stateMachine.getCurrentState() == EnemyState.MOVE_RIGHT)
        {
            setVelocity(new Vector2(0.5f, 0f));
        } else if (stateMachine.getCurrentState() == EnemyState.ATTACK)
        {
            Vector2 playerPos = gameState.getPlayer().getPosition();
            Vector2 myPos = getPosition();
            Vector2 attackVec = playerPos.sub(myPos).nor();

            setVelocity(new Vector2(attackVec.x, 0f));
        }
    }

    public void setLeftBound(Vector2 initialPosition, float left)
    {
        leftBoundHorizontal = initialPosition.x - left;
    }

    public void setRightBound(Vector2 initialPosition, float right)
    {
        rightBoundHorizontal = initialPosition.x + right;
    }

    public void setTopBound(Vector2 initialPosition, float top)
    {
        topBoundVertical = initialPosition.y + top;
    }

    public void setBottomBound(Vector2 initialPosition, float bottom)
    {
        bottomBoundVertical = initialPosition.y - bottom;
    }

    public boolean seesPlayer()
    {
        Vector2 myPos = getPosition();
        Vector2 playerPos = gameState.getPlayer().getPosition();

        RayCallback callback = new RayCallback();
        gameState.getPhysics().getWorld().rayCast(callback, myPos, playerPos);

        return callback.getRayContactCount() == 1 && callback.getRayFixtures()
                                                             .get(0)
                                                             .getUserData() instanceof HumanPlayer;
    }

    public boolean canAttackPlayer()
    {
        Vector2 playerPos = gameState.getPlayer().getPosition();
        Vector2 myPos = getPosition();
        return playerPos.x > leftBoundHorizontal && playerPos.x < rightBoundHorizontal
               && playerPos.y > bottomBoundVertical && playerPos.y < topBoundVertical;
    }

    public boolean withinLeftBound()
    {
        return getPosition().x > leftBoundHorizontal;
    }

    public boolean withinRightBound()
    {
        return getPosition().x < rightBoundHorizontal;
    }
}
