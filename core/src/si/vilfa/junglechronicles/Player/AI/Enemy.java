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
    private EnemySprite enemySprite;

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
            Vector2 playerPos = game.getPlayer().getPosition();
            Vector2 myPos = getPosition();
            Vector2 attackVec = playerPos.sub(myPos).nor().scl(2f);

            if (withinLeftBound() && withinRightBound())
            {
                setVelocity(new Vector2(attackVec.x, 0f));
            } else
            {
                setVelocity(new Vector2());
            }
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
        Vector2 playerPos = game.getPlayer().getPosition();

        RayCallback callback = new RayCallback();
        game.getPhysics().getWorld().rayCast(callback, myPos, playerPos);

        return callback.getRayContactCount() == 1 && callback.getRayFixtures()
                                                             .first()
                                                             .getUserData() instanceof HumanPlayer;
    }

    public boolean withinLeftBound()
    {
        return getPosition().x > leftBoundHorizontal;
    }

    public boolean withinRightBound()
    {
        return getPosition().x < rightBoundHorizontal;
    }

    public EnemySprite getEnemySprite()
    {
        return enemySprite;
    }

    public void setEnemySprite(Integer enemySprite)
    {
        switch (enemySprite)
        {
            case 1:
                this.enemySprite = EnemySprite.MONSTER_ONE;
                break;
            case 2:
                this.enemySprite = EnemySprite.MONSTER_TWO;
                break;
            case 3:
                this.enemySprite = EnemySprite.MONSTER_THREE;
        }
    }

    public enum EnemySprite
    {
        MONSTER_ONE, MONSTER_TWO, MONSTER_THREE
    }
}
