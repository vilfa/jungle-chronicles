package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import si.vilfa.junglechronicles.Component.Loggable;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public enum EnemyState implements State<Enemy>, Loggable
{
    MOVE_LEFT()
            {
                @Override
                public void update(Enemy entity)
                {
                    if (entity.seesPlayer() && entity.canAttackPlayer())
                    {
                        entity.getStateMachine().changeState(EnemyState.ATTACK);
                    } else if (!entity.withinLeftBound())
                    {
                        entity.getStateMachine().changeState(EnemyState.MOVE_RIGHT);
                    }
                }
            },

    MOVE_RIGHT()
            {
                @Override
                public void update(Enemy entity)
                {
                    if (entity.seesPlayer() && entity.canAttackPlayer())
                    {
                        entity.getStateMachine().changeState(EnemyState.ATTACK);
                    } else if (!entity.withinRightBound())
                    {
                        entity.getStateMachine().changeState(EnemyState.MOVE_LEFT);
                    }
                }
            },

    ATTACK()
            {
                @Override
                public void update(Enemy entity)
                {
                    if (!entity.seesPlayer())
                    {
                        entity.getStateMachine().changeState(EnemyState.IDLE);
                    }
                }
            },

    IDLE()
            {
                @Override
                public void update(Enemy entity)
                {
                    if (entity.seesPlayer())
                    {
                        entity.getStateMachine().changeState(EnemyState.ATTACK);
                    } else if (entity.withinLeftBound())
                    {
                        entity.getStateMachine().changeState(EnemyState.MOVE_LEFT);
                    } else if (entity.withinRightBound())
                    {
                        entity.getStateMachine().changeState(EnemyState.MOVE_RIGHT);
                    }
                }
            };

    @Override
    public void enter(Enemy entity)
    {
        log("Entered state:" + entity.getStateMachine().getCurrentState());
    }

    @Override
    public void exit(Enemy entity)
    {
        log("Exited state:" + entity.getStateMachine().getCurrentState());
    }

    @Override
    public boolean onMessage(Enemy entity, Telegram telegram)
    {
        return false;
    }

    @Override
    public String getId()
    {
        return getDeclaringClass().getSimpleName() + "#" + hashCode();
    }

    @Override
    public void log(String message)
    {
        Gdx.app.debug(getId(), message);
    }
}
